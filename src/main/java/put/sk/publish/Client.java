package put.sk.publish;

public class Client {

    public static void main(String args[]) {
        Connection connection = new Connection("localhost", 80);
        System.out.println(connection.toString());
    }
}
