package put.sk.publish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * API Parser
 */
public class APIParser {
    /**
     * Package length (char)
     */
    private static final int PACKAGE_LENGTH = 12000;
    /**
     * Special char to split options
     */
    private static final String SPLIT_CHAR = "\r";
    /**
     * Special char to insert on empty places
     */
    private static final String EMPTY_CHAR = "\n";

    /**
     * Build request to API
     * @param parameters List with Strings
     * @return Request in string form
     */
    public String buildRequest(List parameters) {
        // Request builder
        StringBuilder request = new StringBuilder(PACKAGE_LENGTH);

        // Append parameters to request
        for (Object param : parameters) {
            request.append(param);
            request.append(SPLIT_CHAR);
        }

        // Append new line char to limit
        int toInsert = PACKAGE_LENGTH - request.length();
        for(int i = 0; i < toInsert; i++) {
            request.append(EMPTY_CHAR);
        }

        // Return prepared request
        return request.toString();
    }

    /**
     * Parse response from API
     * @param response String with text from Server
     * @return ArrayList with data, only valid (no empty) data.
     */
    public ArrayList<String> parseResponse(String response) {
        // Parse response from API
        String[] split = response.split("\\r");

        // Make List with Strings
        List<String> splitList = Arrays.asList(split);

        // Prepare ArrayList, add all elements from List and drop last (only new lines)
        ArrayList<String> responseData = new ArrayList();
        responseData.addAll(splitList);
        responseData.remove(responseData.size() - 1);

        // Return prepared data
        return responseData;
    }
}
