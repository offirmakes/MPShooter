public class Animate implements Runnable {
    private ClientScreen s;
    private int refreshTime;

    public Animate(ClientScreen s) {
        this.s = s;
        refreshTime = 10;
    }

    @Override
    public void run() {
        while (true) {
            s.repaint();
            try {
                Thread.sleep(refreshTime); // Adjust sleep time for desired frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public double getRefreshTime(){
        return ((double)refreshTime)/1000;
    }
}