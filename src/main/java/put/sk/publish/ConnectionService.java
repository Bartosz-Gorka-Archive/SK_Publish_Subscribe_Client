package put.sk.publish;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Connection service to enable sending and receive message
 */
public class ConnectionService {
    /**
     * Server IP
     */
    private static String IP_ADDRESS;
    /**
     * Server PORT
     */
    private static int IP_PORT;
    /**
     * Username
     */
    private static String USERNAME;
    /**
     * Last action status
     */
    private boolean actionSuccess;
    /**
     * Last action message from server
     */
    private String actionMessage;

    /**
     * Connection service constructor
     * @param IP Server IP
     * @param port Server port
     * @param userName UserName
     */
    public ConnectionService(String IP, int port, String userName) {
        IP_ADDRESS = IP;
        IP_PORT = port;
        USERNAME = userName;
    }

    /**
     * Login to server API
     * @return Login action status
     */
    public boolean login() {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("LOGIN_C");
        requestData.add(USERNAME);

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // Check response from server
            if(data.get(0).equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();

            // Return invalid login
            return false;
        }
    }

    /**
     * Fetch all topics from API
     * @param topicList Observable list with topics
     * @param skip Number of topics to skip
     * @return Updated input topics list
     */
    public ObservableList<Topic> fetchAllTopics(ObservableList<Topic> topicList, int skip) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("GET_QUEUES");
        requestData.add(USERNAME);
        requestData.add(Integer.toString(skip));

        // Set action as fail
        this.actionSuccess = false;

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // Correct action
            this.actionSuccess = true;

            // Create new topic, set title
            for (String title : data) {
                // No more records - break
                if(title.equals("NOMORE")) {
                    break;
                } else {
                    Topic topic = new Topic(title);
                    topicList.add(topic);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return result
        return topicList;
    }

    /**
     * Load articles from API - only for selected topic
     * @param selectedTopic Selected topic to check
     * @param skip Articles to skip in load
     * @return ArrayList with articles
     */
    public ArrayList<Article> fetchTopicArticles(Topic selectedTopic, int skip) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("GET_ALL_ARTICLES");
        requestData.add(USERNAME);
        requestData.add(selectedTopic.getName());
        requestData.add(Integer.toString(skip));

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            if(data.get(0).equals("ERROR")) {
                this.actionSuccess = false;
                if(data.size() > 1) {
                    this.actionMessage = data.get(1);
                }
            } else {
                // Prepare result
                int resultSize = data.size();

                // Prepare articles list
                for(int i = 0; i < resultSize; i += 2) {
                    // If NO MORE - break download
                    if(data.get(i).equals("NOMORE")) {
                        break;
                    } else {
                        // Read articles
                        Article article = new Article(data.get(i), data.get(i + 1), selectedTopic);
                        selectedTopic.addArticle(article);
                    }
                }

                // Set action as success
                this.actionSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return result
        return selectedTopic.getArticles();
    }

    /**
     * Load article details from API
     * @param selectedArticle Article to check and load details
     * @return Article with loaded details
     */
    public Article loadArticleDetails(Article selectedArticle) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("GET_ARTICLE");
        requestData.add(USERNAME);
        requestData.add(selectedArticle.getTopicName());
        requestData.add(selectedArticle.getFileName());

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Action status
        this.actionSuccess = false;

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // We don't get error - check
            if(!data.get(0).equals("ERROR")) {
                // Update article details
                try {
                    selectedArticle.setCreateDate(data.get(1));
                    selectedArticle.setTitle(data.get(2));
                    selectedArticle.setContent(data.get(3));
                } catch(ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

                // Set article as loaded
                selectedArticle.setLoaded(true);

                // Set action as success
                this.actionSuccess = true;
            } else {
                if(data.size() > 1) {
                    this.actionMessage = data.get(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return article
        return selectedArticle;
    }

    /**
     * Getter - check action success?
     * @return Boolean action status
     */
    public boolean isActionSuccess() {
        return this.actionSuccess;
    }

    /**
     * Getter - last action message
     * @return Last action message (from API)
     */
    public String getActionMessage() {
        return this.actionMessage;
    }

    /**
     * API - Add topic
     * @param topicName Topic to add
     * @return Action status, true on success
     */
    public boolean addTopic(String topicName) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("ADD_QUEUE");
        requestData.add(USERNAME);
        requestData.add(topicName);

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Action status
        this.actionSuccess = false;

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // We don't get error - check
            if(data.get(0).equals("OK")) {
                // Set action as success
                this.actionSuccess = true;

                // Return success
                return true;
            } else {
                if(data.size() > 1) {
                    this.actionMessage = data.get(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return error
        return false;
    }

    /**
     * Add subscribe - topic
     * @param selectedTopic Topic to subscribe
     * @return Action status
     */
    public boolean addSubscribe(Topic selectedTopic) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("ADD_SUBSCRIBER");
        requestData.add(USERNAME);
        requestData.add(selectedTopic.getName());

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Action status
        this.actionSuccess = false;

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // We don't get error - check
            if(data.get(0).equals("OK")) {
                // Set action as success
                this.actionSuccess = true;

                // Return success
                return true;
            } else {
                if(data.size() > 1) {
                    this.actionMessage = data.get(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return error
        return false;
    }

    /**
     * Add new article - API
     * @param selectedTopic Topic
     * @param title Article title
     * @param content Article content
     * @return Action status, true if success
     */
    public boolean addArticle(Topic selectedTopic, String title, String content) {
        // Prepare request parameters
        ArrayList<String> requestData = new ArrayList<>();
        requestData.add("ADD_ARTICLE");
        requestData.add(USERNAME);
        requestData.add(selectedTopic.getName());
        requestData.add(title);
        requestData.add(content);

        // Create API Parser
        APIParser apiParser = new APIParser();
        String request = apiParser.buildRequest(requestData);

        // Action status
        this.actionSuccess = false;

        // Connection
        Connection connection = new Connection(IP_ADDRESS, IP_PORT);
        try {
            // Transfer data to API, receive and parse response
            String response = connection.transfer(request);
            ArrayList<String> data = apiParser.parseResponse(response);

            // We don't get error - check
            if(data.get(0).equals("OK")) {
                // Set action as success
                this.actionSuccess = true;

                // Return success
                return true;
            } else {
                if(data.size() > 1) {
                    this.actionMessage = data.get(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return error
        return false;
    }
}
