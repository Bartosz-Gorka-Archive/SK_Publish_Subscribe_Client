package put.sk.publish;

import javafx.application.Application;
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
        openMainView(); // TODO Only now, delete and uncomment code below
        loadTopics();
//        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        stage.setTitle("LoginView");
//        stage.setScene(new Scene(root, 260, 280));
//        stage.setResizable(false);
//        stage.show();
    }

    /**
     * Login to server API
     * @param IP Server IP or domain
     * @param port Port
     * @param userName Username
     * @return Action status, true if correct login
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
        server = new ConnectionService("127.0.0.1", 1445, "bartek"); // TODO Delete
        try {
            Parent root = FXMLLoader.load(Client.class.getResource("main.fxml"));
            stage.setTitle("Publish Subscribe");
            stage.setScene(new Scene(root, 800, 600));
            stage.setResizable(false);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load topics from API and pass to View
     * @return ArrayList with Topics
     */
    public static ArrayList<Topic> loadTopics() {
        return server.fetchAllTopics(0);
    }

    /**
     * Load articles assigned in selected topic
     * @param selectedTopic Topic to check
     * @return ArrayList with articles
     */
    public static ArrayList<Article> loadArticles(Topic selectedTopic) {
        return server.fetchTopicArticles(selectedTopic, 0);
    }

    /**
     * Getter - check last action success?
     * @return Boolean action status
     */
    public static boolean isActionSuccess() {
        return server.isActionSuccess();
    }
}
