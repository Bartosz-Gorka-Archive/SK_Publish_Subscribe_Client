package put.sk.publish;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
     * Base connection constructor
     */
    public Connection() {}

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
     * Find server IP from domain name
     * @param domainName String with domain name
     * @return Server IP if can find
     * @throws UnknownHostException When IP not found
     */
    public String findServerIP(String domainName) throws UnknownHostException {
        return InetAddress.getByName(domainName).getHostAddress();
    }

    /**
     * Get server IP
     * @return IP
     */
    public String getHostIP() {
        return hostIP;
    }

    /**
     * Set server IP
     * @param hostIP Server IP
     */
    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    /**
     * Get server Port
     * @return Port
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Set server Port
     * @param portNumber Port
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

}
