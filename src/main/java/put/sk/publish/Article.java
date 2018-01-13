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
     * Topic name
     */
    private String topicName;
    /**
     * Loaded details status
     */
    private boolean loaded;

    /**
     * New article detail
     * @param fileName Article fileName
     * @param title Article title
     */
    public Article(String fileName, String title, String topicName) {
        this.fileName = fileName;
        this.title = title;
        this.topicName = topicName;
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
        return this.topicName;
    }

    /**
     * Check loaded details in article
     * @return Status of load
     */
    public boolean isLoaded() {
        return loaded;
    }
}
