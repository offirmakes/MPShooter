import javax.swing.JFrame;
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Predator Strike");

        ClientScreen sc = new ClientScreen();
        frame.add(sc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sc.close();
            }
        });

        frame.pack();
        frame.setVisible(true);

        sc.connect();
    }
}