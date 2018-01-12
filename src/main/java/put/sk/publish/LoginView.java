package put.sk.publish;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.UnknownHostException;

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

        if(checkCorrectData(IP) && checkCorrectData(userName) && checkCorrectData(portText)) {
            try {
                int port = Integer.parseInt(portText);
                if(port <= 0) {
                    showAlert("Invalid value", "Port should be greater than zero.");
                } else {
                    // Block fields
                    blockButtons(true);

                    // Connection with server
                    Client.loginToServer(IP, port, userName);

                    // Open second view
                    // TODO view
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid format", "Port should be integer value.");
            } catch (UnknownHostException e) {
                blockButtons(false);
                showAlert("Server address", "We can not find server.\nPlease insert correct IP / domain name.");
            }
        }
    }

    /**
     * Show alert in login view
     * @param header Header in alert
     * @param message Message to show
     */
    private void showAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeaderText(header);
        alert.show();
    }

    /**
     * Check correct text. Text should be not empty (after reduce spaces).
     * @param text Test to check
     * @return Status of date
     */
    private boolean checkCorrectData(String text) {
        return !text.trim().isEmpty();
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
