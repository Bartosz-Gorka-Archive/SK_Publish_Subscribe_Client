package put.sk.publish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    public void loadTopicList() {
        ArrayList<Topic> topics = Client.loadTopics();
        ObservableList<Topic> topicList = FXCollections.observableList(topics);
        this.listQueue.setItems(topicList);
    }

    /**
     * Load articles for selected topic
     */
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
    public void onReadArticle() {
        // Get selected article from list
        Article selectedArticle = (Article) this.listArticle.getSelectionModel().getSelectedItem();
        if(selectedArticle != null) {
            // Load text only when not null (exists selection)
            Article t = Client.loadArticleDetails(selectedArticle);

            // TODO Show details

            System.out.println(t.getFileName());
            System.out.println(t.getTopicName());
            System.out.println(t.toString());
        }
    }

    /**
     * Write new article - action
     */
    public void onWriteNewArticle() {
        Topic selectedTopic = (Topic) this.listQueue.getSelectionModel().getSelectedItem();
        if(selectedTopic != null) {
            this.prepareSendEnv(true);
        }
    }

    /**
     * Cancel prepare new article
     */
    public void onCancelSend() {
        this.prepareSendEnv(false);
    }

    /**
     * Prepare sending environment - disable / enable buttons, list ...
     * @param status Boolean sending ready (true)
     */
    private void prepareSendEnv(boolean status) {
        // Clear inputs
        this.clearInputs();

        // Buttons
        this.buttonLoadArticles.setDisable(status);
        this.buttonRead.setDisable(status);
        this.buttonWrite.setDisable(status);

        // Lists
        this.listArticle.setDisable(status);
        this.listQueue.setDisable(status);

        // Sending button
        this.buttonSend.setDisable(!status);

        // Cancel button
        this.buttonCancel.setVisible(status);
    }

}
