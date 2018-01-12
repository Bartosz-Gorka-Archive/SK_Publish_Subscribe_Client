package put.sk.publish;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.UnknownHostException;

/**
 * Main module in Publish Subscribe application
 */
public class Client extends Application {
    /**
     * Current stage
     */
    private static Stage stage;

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
        primaryStage.setTitle("LoginView");
        primaryStage.setScene(new Scene(root, 260, 280));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Login to server API
     * @param IP Server IP or domain
     * @param port Port
     * @param userName Username
     */
    public static void loginToServer(String IP, int port, String userName) throws UnknownHostException {
        // Create new connection
        Connection connection = new Connection();

        // Check domain or IP
        connection.findServerIP(IP);

    }
}
