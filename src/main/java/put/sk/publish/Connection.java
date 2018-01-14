package put.sk.publish;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Connection to the server
 */
public class Connection {
    /**
     * Package length
     */
    private static final int PACKAGE_LENGTH = 12000;
    /**
     * Server host IP
     */
    private String hostIP;
    /**
     * Server port
     */
    private int portNumber;

    /**
     * Prepare connection to server
     * @param hostIP Server host IP
     * @param portNumber Server port
     */
    public Connection(String hostIP, int portNumber) {
        this.hostIP = hostIP;
        this.portNumber = portNumber;
    }

    /**
     * Connection details
     * @return Server details
     */
    @Override
    public String toString() {
        return this.hostIP + ":" + this.portNumber;
    }

    /**
     * Transfer data to server, receive response
     * @param message Message to server
     * @return Server response
     * @throws IOException Invalid socket
     */
    public String transfer(String message) throws IOException {
        Socket socket = new Socket(hostIP, portNumber);
        if(socket.isConnected()) {
            // Transform message to bytes list
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

            // Count data to send
            int transferSize = bytes.length;

            // Prepare output
            OutputStream outputStream = socket.getOutputStream();

            // Transfer
            for(int i = 0; i < transferSize; i++) {
                outputStream.write(bytes[i]);
                outputStream.flush();
            }

            // Receive response
            byte[] responseBytes = new byte[PACKAGE_LENGTH];
            InputStream inputStream = socket.getInputStream();

            for(int i = 0; i < PACKAGE_LENGTH; i++) {
                int singleChar = inputStream.read();
                if (singleChar >= 0) {
                    responseBytes[i] = (byte) singleChar;
                } else {
                    // If invalid transfer from server - raise error
                    throw new IOException();
                }
            }

            // Close socket
            socket.close();

            // Return prepared response as String UTF-8
            return new String(responseBytes, StandardCharsets.UTF_8);
        }

        // Can not connect - raise error
        throw new IOException();
    }

}
