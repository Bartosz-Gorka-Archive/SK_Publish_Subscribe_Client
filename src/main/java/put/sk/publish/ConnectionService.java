package put.sk.publish;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Connection service to enable sending and receive message
 */
public class ConnectionService {
    private static String IP_ADDRESS;
    private static int IP_PORT;
    private static String USERNAME;

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
        ArrayList<String> requestData = new ArrayList();
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

}
