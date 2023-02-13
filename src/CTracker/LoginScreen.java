package CTracker;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import CTracker.util.Connector;
import CTracker.HomePage.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import static javax.swing.JOptionPane.*;

public class LoginScreen extends JPanel implements ActionListener {

    public JFrame parentFrame = null;
    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JButton loginButton;

    LoginScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void createLogin() {
        this.setBackground(Color.MAGENTA);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        parentFrame.add(this);

        // left panel
        JPanel lleftPanel = new JPanel();
        lleftPanel.setBackground(Color.WHITE);
        this.add(lleftPanel);
        leftPanel(lleftPanel);

        //login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
       
        this.add(loginPanel);
        rightPanel(loginPanel);

        parentFrame.setVisible(true);

    }

    public static void leftPanel(JPanel panel) {
        // panel.setLayout(null);
        // panel.setPreferredSize(new Dimension(IMAGE_PANEL_WIDTH, FRAME_HEIGHT));
        // panel.setBackground(Color.BLUE);

                // get the screen resolution, and size the window proportionately
               // Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
                // int jFrameWidth = (int)(screenDimension.width * 0.7);
                //int jFrameHeight = (int)(screenDimension.height * 0.7);
    try {
            BufferedImage img = ImageIO.read(new File("img_emblem.png"));
            int imgWidth = img.getWidth(null) ;
            int imgHeight = img.getHeight(null);
            Dimension size = new Dimension(imgWidth+80, imgHeight+10);
            //  Dimension size = new Dimension(jFrameWidth-625, jFrameHeight);
            panel.setPreferredSize(size);
            panel.setMinimumSize(size);
            panel.setMaximumSize(size);
            // panel.setSize(size);

            //
            JLabel pic = new JLabel(new ImageIcon(img));
            panel.add(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void rightPanel(JPanel panel) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        // int jFrameWidth = (int)(screenDimension.width * 0.7);
        int jFrameHeight = (int)(screenDimension.height * 0.7);
        // content sized for 325x200 
        final int LEFT_MARGIN = 500;
        final int TOP_MARGIN = 500;
        panel.setLayout(null);
        JLabel login = new JLabel("Login");
        login.setBounds( LEFT_MARGIN, TOP_MARGIN-30,40,40);
       // login.setFont(getFont());
        panel.add(login);
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(LEFT_MARGIN, TOP_MARGIN, 80, 25);
        panel.add(userLabel);
      // login.setFont(getFont());
        userText = new JTextField(20);
        // userText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        // userText.setOpaque(false);
        userText.setBounds(LEFT_MARGIN + 100, TOP_MARGIN, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(LEFT_MARGIN, TOP_MARGIN + 40, 140, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        // passwordText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        passwordText.setBounds(LEFT_MARGIN + 100, TOP_MARGIN + 40, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(164, 219, 232)); // light blue
        loginButton.setBounds(LEFT_MARGIN + 140, TOP_MARGIN + 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);
    }

    public static String encode(String Text)  {
        try {
            // convert the text to UTF-8
            byte[] textBytes = Text.getBytes(StandardCharsets.UTF_8);

            // hash the password input and convert to base64 code
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            return new String(
                    Base64.getEncoder().encode(hash.digest(textBytes)));
       } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
      //  if (e.getSource() == loginButton) { if??
            Connector Con = new Connector();
            try {

                ResultSet rs = Con.execute("SELECT PASS FROM Users WHERE NAME='" + userText.getText() + "'");
                if (rs.next() == false) {
                    System.out.println("New User");
                    showMessageDialog(null, "User not registered!", "Error", ERROR_MESSAGE);
                    return;
                }
                // hash the password
                String pasw = encode(new String(passwordText.getPassword()));
                // System.out.println(pasw);
                if (rs.getString("PASS").equals(pasw)) {
                    System.out.println("Logged In");
                    parentFrame.getContentPane().setVisible(false);
                    parentFrame.setContentPane(new HomePage(parentFrame, userText.getText()));

                    return;
                } else {
                    System.out.println("Wrong PassWord");
                    showMessageDialog(null, "Wrong password!", "Error", ERROR_MESSAGE);
                    return;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    //}
}
