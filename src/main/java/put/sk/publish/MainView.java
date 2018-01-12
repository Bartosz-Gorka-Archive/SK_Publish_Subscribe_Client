package put.sk.publish;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

}
