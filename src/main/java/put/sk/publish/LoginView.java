package put.sk.publish;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javax.swing.*;

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
     * Loader image
     */
    public ImageView loader;

    /**
     * Reset values inserted in text fields
     * @param actionEvent Action event (mouse click)
     */
    public void onReset(ActionEvent actionEvent) {
        this.inputIP.clear();
        this.inputPort.clear();
        this.inputUsername.clear();
    }

    /**
     * Connection to server - action to bind API
     * @param actionEvent Action event (mouse click)
     */
    public void onConnect(ActionEvent actionEvent) {
        String IP = this.inputIP.getText();
        String portText = this.inputPort.getText();
        String userName = this.inputUsername.getText();

        // TODO Validation

        int port = Integer.parseInt(portText);
        blockButtons(true);

        // TODO Call connection to server
    }

    /**
     * Block buttons and fields on view, show loader
     * @param status Boolean status of action
     */
    private void blockButtons(Boolean status) {
        // Fields
        this.inputIP.setDisable(status);
        this.inputPort.setDisable(status);
        this.inputUsername.setDisable(status);

        // Buttons
        this.buttonConnect.setDisable(status);
        this.buttonReset.setDisable(status);

        // Loader
        this.loader.setVisible(status);
    }
}
