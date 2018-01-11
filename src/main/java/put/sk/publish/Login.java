package put.sk.publish;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {
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
    public void clickReset(ActionEvent actionEvent) {
        actionEvent.getSource();
        System.out.println(actionEvent);
    }
}
