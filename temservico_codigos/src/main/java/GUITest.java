import db.PostgresDB;
import sec.SHA256Hasher;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

        JLabel title = new JLabel("TemServiço?", SwingConstants.CENTER);
        title.setBounds(width/2 - 80, 10, 160, fieldHeight + 10);
        title.setFont(title.getFont().deriveFont(20.0f));
        panel.add(title);

        JLabel usrLabel = new JLabel("CPF/Nome/Email:");
        usrLabel.setBounds(width/2 - 125, 55, 110, fieldHeight);
        panel.add(usrLabel);
        JTextField usrText = new JTextField(20);
        usrText.setBounds(width/2 - 125 + 110, 55, 140, fieldHeight);
        panel.add(usrText);

        JLabel pswrdLabel = new JLabel("Senha:");
        pswrdLabel.setBounds(width/2 - 125, 55 + fieldHeight, 110, fieldHeight);
        panel.add(pswrdLabel);

        JPasswordField pswrdText = new JPasswordField(20);
        pswrdText.setBounds(width/2 - 125 + 110, 55 + fieldHeight, 140, fieldHeight);
        panel.add(pswrdText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(width/2 - 50, 75 + fieldHeight * 2, 100, fieldHeight);
        panel.add(loginButton);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(width/2 - 150, 80 + fieldHeight * 3, 300, fieldHeight);
        panel.add(messageLabel);

        loginButton.addActionListener(e -> {
            String loginInput = usrText.getText();
            PostgresDB.UsersCols loginType = checkLoginInput(loginInput);

            String inputPasswordHash = null;
            try {
                inputPasswordHash = SHA256Hasher.hashString(new String(pswrdText.getPassword()));
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            try {
                if(PostgresDB.getPasswordHash(loginInput, loginType).equals(inputPasswordHash)){
                    messageLabel.setText("Sucesso! Entrando na plataforma.");
                }
                else{
                    messageLabel.setText("Senha incorreta ou usuário não existe.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        return panel;
    }

    private static PostgresDB.UsersCols checkLoginInput(String loginInput) {
        if(myUtils.isNumeric(loginInput) && loginInput.length() == 11){
            return PostgresDB.UsersCols.CPF;
        }
        if(loginInput.contains("@")){
            return PostgresDB.UsersCols.EMAIL;
        }
        return PostgresDB.UsersCols.NAME;
    }

}
