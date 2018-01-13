package put.sk.publish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Main view
 */
public class MainView {
    /**
     * List view with queues
     */
    public ListView listQueue;
    /**
     * List with articles in selected queue
     */
    public ListView listArticle;
    /**
     * Selected article text area
     */
    public TextArea areaArticleText;
    /**
     * Group with selected article
     */
    public AnchorPane groupSelectedArticle;
    /**
     * Article title - used in create new article
     */
    public TextField inputArticleTitle;
    /**
     * Button read article
     */
    public Button buttonRead;
    /**
     * Button write new article
     */
    public Button buttonWrite;
    /**
     * Button clear text
     */
    public Button buttonClear;
    /**
     * Button send new article
     */
    public Button buttonSend;
    /**
     * Loader group
     */
    public AnchorPane groupLoader;
    /**
     * Button load articles
     */
    public Button buttonLoadArticles;
    /**
     * Button cancel send new article
     */
    public Button buttonCancel;
    /**
     * Button create new topic
     */
    public Button buttonCreateTopic;
    /**
     * Input with new topic title
     */
    public TextField inputTopicName;
    /**
     * Input server response
     */
    public TextField inputServerResponse;
    /**
     * Button add subscribe to topic
     */
    public Button buttonAddSubscribe;

    /**
     * Clear inputs to enable prepare new article
     * @param actionEvent Event - clear action
     */
    public void onClearText(ActionEvent actionEvent) {
        clearInputs();
    }

    /**
     * Initialize view
     */
    @FXML
    public void initialize() {
        // Topic list update
        this.loadTopicList();
    }

    /**
     * Clear inputs
     */
    private void clearInputs() {
        this.inputArticleTitle.clear();
        this.areaArticleText.clear();
    }

    /**
     * Loader group show / hide
     * @param status Show / hide as boolean status. Show when true.
     */
    private void loader(boolean status) {
        this.groupLoader.setVisible(status);
    }

    /**
     * Load topic lists from Client and set in listView
     */
    @FXML
    public void loadTopicList() {
        ArrayList<Topic> topics = Client.loadTopics();
        ObservableList<Topic> topicList = FXCollections.observableList(topics);
        this.listQueue.setItems(topicList);
    }

    /**
     * Load articles for selected topic
     */
    @FXML
    public void onLoadArticles() {
        Topic selectedTopic = (Topic) this.listQueue.getSelectionModel().getSelectedItem();
        if(selectedTopic != null) {
            ArrayList<Article> articles = Client.loadArticles(selectedTopic);
            ObservableList<Article> articlesList = FXCollections.observableList(articles);
            this.listArticle.setItems(articlesList);
            this.buttonRead.setDisable(!Client.isActionSuccess());
        }

        // TODO Enable add subscription to topic
    }

    /**
     * Read selected article - download text
     */
    @FXML
    public void onReadArticle() {
        // Block button
        this.buttonRead.setDisable(true);

        // Show loader
        this.loader(true);

        // Get selected article from list
        Article selectedArticle = (Article) this.listArticle.getSelectionModel().getSelectedItem();
        if(selectedArticle != null) {
            // Load text only when not null (exists selection)
            Client.loadArticleDetails(selectedArticle);

            // Insert details
            this.areaArticleText.setText(selectedArticle.getContent());
            this.inputArticleTitle.setText(selectedArticle.articleDescription());
        }

        // Enable button
        this.buttonRead.setDisable(false);

        // Hide loader
        this.loader(false);
    }

    /**
     * Write new article - action
     */
    @FXML
    public void onWriteNewArticle() {
        Topic selectedTopic = (Topic) this.listQueue.getSelectionModel().getSelectedItem();
        if(selectedTopic != null) {
            this.prepareSendEnv(true);
        }
    }

    /**
     * Cancel prepare new article
     */
    @FXML
    public void onCancelSend() {
        this.prepareSendEnv(false);
    }

    /**
     * Prepare sending environment - disable / enable buttons, list ...
     * @param status Boolean sending ready (true)
     */
    @FXML
    private void prepareSendEnv(boolean status) {
        // Clear inputs
        this.clearInputs();

        // Buttons
        this.buttonLoadArticles.setDisable(status);
        this.buttonRead.setDisable(status);
        this.buttonWrite.setDisable(status);
        this.buttonAddSubscribe.setDisable(status);

        // Lists
        this.listArticle.setDisable(status);
        this.listQueue.setDisable(status);

        // Sending button
        this.buttonSend.setDisable(!status);

        // Cancel button
        this.buttonCancel.setVisible(status);
    }

    /**
     * Create new topic
     */
    @FXML
    public void onCreateTopic() {
        // Clear response from server and block button
        this.inputServerResponse.clear();
        this.buttonCreateTopic.setDisable(true);

        // Get text from input
        String topicName = this.inputTopicName.getText().trim();

        // Try add new topic
        if(Client.addNewTopic(topicName)) {
            // Success - clear input, load topic list
            this.inputTopicName.clear();
            this.loadTopicList();
        } else {
            // Show error
            this.inputServerResponse.setText("Can not add new topic.");
        }

        // Enable button
        this.buttonCreateTopic.setDisable(false);
    }

    /**
     * Change text in topic title input - action
     */
    @FXML
    public void onChangeTopicTitle(KeyEvent keyEvent) {
//        this.inputTopicName.addEventFilter(KeyEvent.KEY_TYPED, );
//        System.out.println(keyEvent.getSource());
//        System.out.println(keyEvent.getText());
//        System.out.println(keyEvent.getCharacter());
//        String c = keyEvent.getCharacter();
//        if("1234567890".contains(c)) {}
//        else {
//            keyEvent.consume();
//        }
//        String text = this.inputTopicName.getText();
    }

    /**
     * Add subscribe to topic
     */
    @FXML
    public void onAddSubscribe() {
        // Clear response from server
        this.inputServerResponse.clear();

        // Block button
        this.buttonAddSubscribe.setDisable(true);

        // Get selected topic
        Topic selectedTopic = (Topic) this.listQueue.getSelectionModel().getSelectedItem();
        if(selectedTopic != null) {
            // Send action to server
            Client.addSubscribe(selectedTopic);
            this.inputServerResponse.setText("Subscription applied.");
        } else {
            // Set error message
            this.inputServerResponse.setText("You mast select topic.");
        }

        // Enable button
        this.buttonAddSubscribe.setDisable(false);
    }

    /**
     * Send article to API
     */
    @FXML
    public void onSendArticle() {
        // Get text from inputs
        String title = this.inputArticleTitle.getText().trim();
        String content = this.areaArticleText.getText().trim();

        // Block button
        this.buttonSend.setDisable(true);

        Topic selectedTopic = (Topic) this.listQueue.getSelectionModel().getSelectedItem();
        if(selectedTopic != null) {
            // Check size
            if(checkSize(title, 1, 50) && checkSize(content, 1, 11000)) {
                Client.addNewArticle(selectedTopic, title, content);
                this.prepareSendEnv(false);
            }
        } else {
            // Set error message
            this.inputServerResponse.setText("You mast select topic.");
        }

        // Enable button
        this.buttonSend.setDisable(false);
    }

    private boolean checkSize(String text, int min, int max) {
        int length = text.length();
        return (length >= min && length <= max);
    }

}
