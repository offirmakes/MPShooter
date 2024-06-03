public class GlobalTime implements Runnable {
    private int counter;
    private Manager manager;

    public GlobalTime(Manager manager) {
        counter = 0;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            counter++; // counter = 50 is 1 second
            // manager.broadcast("10");
            try {
                Thread.sleep(20); // Adjust sleep time for desired frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}