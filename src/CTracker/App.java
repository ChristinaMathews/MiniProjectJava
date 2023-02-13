package CTracker;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import CTracker.*;

public class App {

    public static void main(String[] args) {
        JFrame ctracker = new JFrame("C Tracker");
        // doesnt work
        JFrame.setDefaultLookAndFeelDecorated(true);

        // get the screen resolution, and size the window proportionately
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        double jFrameWidth = screenDimension.width * 1;
        double jFrameHeight = screenDimension.height * 1;
        ctracker.setSize((int) jFrameWidth, (int) jFrameHeight);

        ctracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginScreen sc = new LoginScreen(ctracker);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sc.createLogin();
            }
        });

        // ctracker.setContentPane(sc);
        // ctracker.setVisible(true);
    }
}

