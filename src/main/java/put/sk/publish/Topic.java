package put.sk.publish;

import java.util.ArrayList;

/**
 * Single topic in API
 */
public class Topic {
    /**
     * Topic name
     */
    private String name;
    /**
     * Array list with articles in topic
     */
    private ArrayList<Article> articles;

    /**
     * Topic constructor - without articles
     * @param name Topic name
     */
    public Topic(String name) {
        this.articles = new ArrayList<>();
        this.name = name;
    }

    /**
     * Custom toString()
     * @return Topic name
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Getter - Topic name
     * @return Topic name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Add article to Topic
     * @param article Article to add
     */
    public void addArticle(Article article) {
        this.articles.add(article);
    }

    /**
     * Getter - Articles list
     * @return List with articles
     */
    public ArrayList<Article> getArticles() {
        return this.articles;
    }
}
