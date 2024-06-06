import java.net.*;
import java.io.*;

public class ServerThread implements Runnable {
    private Socket clientSocket;
    private Manager manager;
    private boolean userReady;
    private String username;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj; 
    

    public ServerThread(Socket clientSocket, Manager manager) {
        this.clientSocket = clientSocket;
        this.manager = manager;
        userReady = false;
        username = "";
        try {
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + ": connection opened.");
        
        Boolean keepRunning = true;

        while (keepRunning){
            Pair<MessagesIds, Object> input = null;
            try {
                input = (Pair<MessagesIds, Object>) inObj.readObject();
            } catch (IOException ex) {
                System.out.println("Error listening for a connection");
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (ClassCastException e){
                System.out.println("ClassCastException");
                System.out.println(e.getMessage());
            }

            if (input == null) continue;

            switch (input.getKey()) {
                case EXIT:
                    if (input.getValue() != null){
                        String playerUsername = (String) input.getValue();
                        manager.broadcast(MessagesIds.REMOVE_PLAYER, playerUsername);
                    }
                    manager.remove(this, userReady);
                    sendMessage(MessagesIds.EXIT, null);
                    keepRunning = false;
                    break;
                case PLAYER_COUNT:
                    sendMessage(MessagesIds.PLAYER_COUNT, manager.threadSize());
                    break; 
                case PLAYER_READY:
                    userReady = true; 
                    manager.broadcast(MessagesIds.PLAYER_READY_COUNT, manager.getTotalPlayerReady());
                    manager.checkStartGame();
                    break;
                case PLAYER_USERNAME:
                    username = (String) input.getValue(); 
                    if (username.length() < 3){
                        sendMessage(MessagesIds.USERNAME_TOO_SHORT, null);
                    } else {
                        sendMessage(MessagesIds.USERNAME_DUPLICATE, manager.checkDuplicateUsername(username));
                    }
                    break;
                case PLAYER_OBJECT:
                    Player player = (Player) input.getValue();
                    manager.broadcast(MessagesIds.PLAYER_OBJECT, player);
                    break;
                case PLAYER_UNREADY:
                    userReady = false;
                    manager.broadcast(MessagesIds.PLAYER_READY_COUNT, manager.getTotalPlayerReady());
                    break;
                case REMOVE_BULLET:
                    Pair<String, Integer> bullet = (Pair<String, Integer>) input.getValue();
                    manager.broadcast(MessagesIds.REMOVE_BULLET, bullet);
                    break;
                case REMOVE_DAGGER:
                    Pair<String, Integer> dagger = (Pair<String, Integer>) input.getValue();
                    manager.broadcast(MessagesIds.REMOVE_DAGGER, dagger);
                    break;
                case PLAYER_FOUND:
                    String playerFoundUsername = (String) input.getValue();
                    manager.broadcast(MessagesIds.PLAYER_FOUND, playerFoundUsername);
                    break;
                case PLAYER_DEAD:
                    Pair<String, String> playerDead = (Pair<String, String>) input.getValue();
                    manager.broadcast(MessagesIds.PLAYER_DEAD, playerDead);
                    break;
                case GAME_RESET:
                    manager.resetGameStatus();
                    break;
            }
        }
        
        try {
            // Clears and close the output stream.
            outObj.flush();
            outObj.close();
        } catch (IOException ex) {
            System.out.println("Error listening for a connection");
            System.out.println(ex.getMessage());
        }
        
        System.out.println(Thread.currentThread().getName() + ": connection closed.");
    }
    public String getUsername(){
        return username;
    }
    public boolean getUserReady(){
        return userReady;
    }
    public synchronized void sendMessage(MessagesIds id, Object obj) {
        try {
            outObj.writeObject(new Pair<MessagesIds, Object>(id, obj));
            outObj.flush();
            outObj.reset();
        } catch (IOException ex) {
            System.out.println("Error sending message");
            System.out.println(ex.getMessage());
        }
    }
}