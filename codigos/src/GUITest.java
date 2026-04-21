import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GUITest {

    public static void main(String[] args) {
        // 1. Create the main window (JFrame)
        JFrame frame = new JFrame("My First Java GUI");

        // Ensure the program exits when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the window (width, height in pixels)
        frame.setSize(500, 500);

        // 2. Create a container (JPanel) to hold our stuff
        int width = 400;
        int height = 300;
        Dimension dimension = new Dimension(width, height);
        JPanel panel = loginJPanel(width, height);

        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);

//        panel.setBackground(Color.RED); // for debug only

        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        // 6. Add the panel to the frame
        frame.add(box);

        // 7. Finally, make the window visible
        frame.setVisible(true);
    }

    private static JPanel loginJPanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        int fieldHeight = 25;

        JLabel usrLabel = new JLabel("Username:");
        usrLabel.setBounds(width/2 - 90, 20, 80, fieldHeight);
        panel.add(usrLabel);
        JTextField usrText = new JTextField(20);
        usrText.setBounds(width/2 - 90 + 80, 20, 100, fieldHeight);
        panel.add(usrText);

        JLabel pswrdLabel = new JLabel("Senha:");
        pswrdLabel.setBounds(width/2 - 90, 20 + fieldHeight, 80, fieldHeight);
        panel.add(pswrdLabel);

        JPasswordField pswrdText = new JPasswordField(20);
        pswrdText.setBounds(width/2 - 90 + 80, 20 + fieldHeight, 100, fieldHeight);
        panel.add(pswrdText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(width/2 - 50, 40 + fieldHeight * 2, 100, fieldHeight);
        panel.add(loginButton);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(width/2 - 150, 40 + fieldHeight * 3, 300, fieldHeight);
        panel.add(messageLabel);

        loginButton.addActionListener(e -> {
            String username = usrText.getText();
            String password = new String(pswrdText.getPassword());

            try {
                if(username.equals("admin") && hashString(password).equals(hashString("1234"))){
                    messageLabel.setText("Sucesso");
                }
                else{
                    messageLabel.setText("Falha");
                }
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });

        return panel;
    }

    public static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] rawHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for(byte b : rawHash){
            hash.append(Integer.toHexString(b & 0xff));
        }
        return hash.toString();
    }
}
