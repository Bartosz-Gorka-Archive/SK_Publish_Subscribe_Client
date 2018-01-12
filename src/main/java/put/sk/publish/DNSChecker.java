package put.sk.publish;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * DNS Checker
 */
public class DNSChecker {
    /**
     * Find server IP from domain name
     * @param domainName String with domain name
     * @return Server IP if can find
     * @throws UnknownHostException When IP not found
     */
    public String findServerIP(String domainName) throws UnknownHostException {
        return InetAddress.getByName(domainName).getHostAddress();
    }
}
