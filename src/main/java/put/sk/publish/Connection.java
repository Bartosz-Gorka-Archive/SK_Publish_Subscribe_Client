package put.sk.publish;

/**
 * Connection to the server
 */
public class Connection {
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

}
