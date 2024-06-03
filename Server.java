import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 1024;
        ServerSocket serverSocket = new ServerSocket(portNumber);

        Manager manager = new Manager();

        while (true) {
            System.out.println("Waiting for a connection");

            Socket clientSocket = serverSocket.accept();

            ServerThread serverThread = new ServerThread(clientSocket, manager); // Pass clientScreen
            Thread thread = new Thread(serverThread);
            manager.add(serverThread);
            thread.start();
        }
    }
}