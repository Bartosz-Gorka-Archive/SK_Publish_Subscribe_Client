package put.sk.publish;

/**
 * Article details
 */
public class Article {
    /**
     * Filename stored in API
     */
    private String fileName;
    /**
     * Article title
     */
    private String title;
    /**
     * Topic - parent
     */
    private Topic topic;
    /**
     * Loaded details status
     */
    private boolean loaded;
    /**
     * Article create date
     */
    private String createDate;
    /**
     * Article content
     */
    private String content;

    /**
     * New article detail
     * @param fileName Article fileName
     * @param title Article title
     * @param topic Topic - parent
     */
    public Article(String fileName, String title, Topic topic) {
        this.fileName = fileName;
        this.title = title;
        this.topic = topic;
        this.createDate = "I forgot date :(";
        this.loaded = false;
    }

    /**
     * Custom toString()
     * @return Article title
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * Article description with title and create date
     * @return Article description
     */
    public String articleDescription() {
        return this.title + " @ " + this.createDate;
    }

    /**
     * Getter fileName
     * @return Article fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Getter topicName
     * @return Article's topic name
     */
    public String getTopicName() {
        return this.topic.getName();
    }

    /**
     * Check loaded details in article
     * @return Status of load
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Mark article as loader or not
     * @param loaded Loaded status
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Set article title
     * @param title Title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set article create date
     * @param createDate Date to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Get article content
     * @return Article content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set article content
     * @param content Content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
