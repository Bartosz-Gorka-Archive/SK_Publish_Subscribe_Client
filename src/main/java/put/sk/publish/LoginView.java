package put.sk.publish;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Login view
 */
public class LoginView {
    /**
     * Connection to server - button
     */
    public Button buttonConnect;
    /**
     * Reset inserted values - button
     */
    public Button buttonReset;
    /**
     * Input with username
     */
    public TextField inputUsername;
    /**
     * Input with server IP or domain name
     */
    public TextField inputIP;
    /**
     * Input with server PORT
     */
    public TextField inputPort;

    /**
     * Reset values inserted in text fields
     * @param actionEvent Mouse click event
     */
    public void onReset(ActionEvent actionEvent) {
        this.inputIP.clear();
        this.inputPort.clear();
        this.inputUsername.clear();
    }
}
