package view;

import controller.ControllerLogin;
import sec.SHA256Hasher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;


public class ViewLogin{

    private ControllerLogin controller;

    public ViewLogin(ControllerLogin controllerInput){
        controller = controllerInput;
    }

    public void janelaLogin(){
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        int width = 400;
        int height = 300;
        Dimension dimension = new Dimension(width, height);

        // gera o painel principal de login
        JPanel panel = loginJPanel(width, height, frame);

        panel.setPreferredSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);

        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        frame.add(box);

        frame.setVisible(true);
    }

    private JPanel loginJPanel(int width, int height, JFrame framePai) {
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
            String passwordInput = new String(pswrdText.getPassword());

            try {
                if(controller.validLogin(loginInput, passwordInput)){
                    JOptionPane.showMessageDialog(framePai, "Sucesso! Entrando na plataforma.");
                    controller.retornaLogado();
                    framePai.dispose();
                }
                else{
                    messageLabel.setText("Senha incorreta ou usuário não existe.");
                }
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = panel.getInputMap(condition);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });

        return panel;
    }
}
