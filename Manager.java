public class Manager {
    private ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();
    private boolean gameStart;
    private GlobalTime globalTime;

    public Manager(){
        threadList = new ArrayList<ServerThread>();
        gameStart = false;
    }
    public synchronized void add(ServerThread serverThread){
        threadList.add(serverThread);
        broadcast(MessagesIds.PLAYER_COUNT, threadSize());
        broadcast(MessagesIds.PLAYER_READY_COUNT, getTotalPlayerReady());
    }
    public synchronized boolean checkDuplicateUsername(String username){
        int counter = 0;
        for (int i = 0; i < threadList.size(); i++){
            if (username.equals(threadList.get(i).getUsername())){
                if (counter > 0){
                    return false;
                }
                counter++;
            }
        }
        return true;
    }
    public synchronized void remove(ServerThread serverThread, boolean userReady){
        threadList.remove(serverThread);
        broadcast(MessagesIds.PLAYER_COUNT, threadSize());
        if (userReady){
            broadcast(MessagesIds.PLAYER_READY_DECREASE, null);
        }
    }
    public synchronized void broadcast(MessagesIds id, Object obj){
        for (int i = 0; i < threadList.size(); i++){
            threadList.get(i).sendMessage(id, obj);
        }
    }
    public synchronized int getTotalPlayerReady(){
        int total = 0;
        for (int i = 0; i < threadList.size(); i++){
            if (threadList.get(i).getUserReady()){
                total++;
            }
        }
        return total;
    }
    public synchronized void checkStartGame(){
        if (getTotalPlayerReady() >= 2 && getTotalPlayerReady() == threadSize()){
            broadcast(MessagesIds.GAME_START, gameStart);
            gameStart = true;
            // globalTime = new GlobalTime(this);
            // Thread globalTimeThread = new Thread(globalTime);
            // globalTimeThread.start();
        }
    }
    public synchronized void resetGameStatus(){
        gameStart = false;
    }
    public synchronized int threadSize(){
        return threadList.size();
    }
}