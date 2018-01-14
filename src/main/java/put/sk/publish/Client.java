package put.sk.publish;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Main module in Publish Subscribe application
 */
public class Client extends Application {
    /**
     * Current stage
     */
    private static Stage stage;
    /**
     * Connection service to allow operations with server
     */
    private static ConnectionService server;
    /**
     * All topics in API
     */
    private static volatile ObservableList<Topic> topicList;
    /**
     * Topic list guardian
     */
    private final static Object guardTopicList = new Object();
    /**
     * Application run status
     */
    private static boolean APPLICATION_RUN = true;

    /**
     * Main method, launch application
     * @param args Application parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start application, load scene
     * @param primaryStage Default primary scene
     * @throws Exception When don't exists login.fxml in resources
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("LoginView");
        stage.setScene(new Scene(root, 260, 280));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Custom stop method
     */
    @Override
    public void stop() {
        // Disable run application
        APPLICATION_RUN = false;

        // Say bye
        System.out.println("Bye application killer!");
    }

    /**
     * Login to server API
     * @param IP Server IP or domain
     * @param port Port
     * @param userName Username
     * @return Action status, true if correct login
     * @throws UnknownHostException Unknown host error
     */
    public static boolean loginToServer(String IP, int port, String userName) throws UnknownHostException {
        // Check IP from Domain name
        DNSChecker dnsChecker = new DNSChecker();
        String serverIP = dnsChecker.findServerIP(IP);

        // Connection
        ConnectionService connectionService = new ConnectionService(serverIP, port, userName);
        server = connectionService;
        return connectionService.login();
    }

    /**
     * Change active window to MainView
     */
    public static void openMainView() {
        try {
            // Topic list
            topicList = FXCollections.observableList(new ArrayList<>());

            // View
            Parent root = FXMLLoader.load(Client.class.getResource("main.fxml"));
            stage.setTitle("Publish Subscribe");
            stage.setScene(new Scene(root, 660, 630));
            stage.setX(stage.getX() / 2);
            stage.setY(stage.getY() / 2);
            stage.setResizable(false);
            stage.show();


            // Thread - refresh list with topics
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    while(APPLICATION_RUN) {
                        // Load topics
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Refresh topics");
                                Client.loadTopics();
                            }
                        });

                        // Sleep 1 min
                        try {

                            for(int i = 0; i < 60; i++) {
                                sleep(1000);

                                if(!APPLICATION_RUN) {
                                    break;
                                }
                            }
                        } catch (InterruptedException e) {}
                    }
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter - Observable list with topics
     * @return Topics list
     */
    public static ObservableList<Topic> getTopicList() {
        return topicList;
    }

    /**
     * Load topics from API and pass to View
     * @return ObservableList with Topics
     */
    public static ObservableList<Topic> loadTopics() {
        // To reduce bugs with threads we must prepare guardian
        synchronized (guardTopicList) {
            // Count topics on start
            int lastDownload = -1;
            int count = topicList.size();

            // Load topics
            while (lastDownload != count) {
                lastDownload = count;
                server.fetchAllTopics(topicList, count);
                count = topicList.size();
            }

            return topicList;
        }
    }

    /**
     * Load articles assigned in selected topic
     * @param selectedTopic Topic to check
     * @return ArrayList with articles
     */
    public static ArrayList<Article> loadArticles(Topic selectedTopic) {
        // Count articles on start
        int lastDownload = -1;
        int count = selectedTopic.getArticles().size();

        // Load articles
        while(lastDownload != count) {
            lastDownload = count;
            server.fetchTopicArticles(selectedTopic, count);
            count = selectedTopic.getArticles().size();
        }

        return selectedTopic.getArticles();
    }

    /**
     * Getter - check last action success?
     * @return Boolean action status
     */
    public static boolean isActionSuccess() {
        return server.isActionSuccess();
    }

    /**
     * Getter - Check API response
     * @return String with action message
     */
    public static String getAPIResponse() {
        return server.getActionMessage();
    }

    /**
     * Load article details
     * @param selectedArticle Article to load
     * @return Article details
     */
    public static Article loadArticleDetails(Article selectedArticle) {
        if(selectedArticle.isLoaded()) {
            // Data already in article object
            return selectedArticle;
        } else {
            // We must load data from API
            return server.loadArticleDetails(selectedArticle);
        }
    }

    /**
     * Insert new topic on server
     * @param topicName Topic name to insert
     * @return Action status
     */
    public static boolean addNewTopic(String topicName) {
        return server.addTopic(topicName);
    }

    /**
     * Add subscribe to topic
     * @param selectedTopic Topic to subscribe
     * @return Action status
     */
    public static boolean addSubscribe(Topic selectedTopic) {
        return server.addSubscribe(selectedTopic);
    }

    /**
     * Add new article
     * @param selectedTopic Topic
     * @param title Article title
     * @param content Article content
     * @return Action status
     */
    public static boolean addNewArticle(Topic selectedTopic, String title, String content) {
        return server.addArticle(selectedTopic, title, content);
    }
}
