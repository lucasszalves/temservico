package view;

import controller.ControllerLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.util.List;


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
        frame.setLayout(new BorderLayout());

        // gera o painel principal de login
        JPanel panel = loginJPanel(frame);

        frame.add(panel);

        frame.setVisible(true);
    }

    private JPanel loginJPanel(JFrame framePai) {
        JPanel painelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título "TemServiço?"
        JLabel lblTitulo = new JLabel("TemServiço?");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Mantém o título no tamanho normal
        gbc.insets = new Insets(10, 10, 30, 10);
        painelLogin.add(lblTitulo, gbc);

// Resetando as configurações de grid para o formulário
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

// 2. Campo "CPF/Nome/Email"
        JLabel lblIdentificacao = new JLabel("CPF/Nome/Email:");
        lblIdentificacao.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelLogin.add(lblIdentificacao, gbc);

        JTextField txtIdentificacao = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelLogin.add(txtIdentificacao, gbc);

// 3. Campo "Senha"
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelLogin.add(lblSenha, gbc);

        JPasswordField txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelLogin.add(txtSenha, gbc);

// 4. Botão "Login"
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setPreferredSize(new Dimension(150, 35));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 10, 15, 10);
        painelLogin.add(btnLogin, gbc);

// 5. Texto "Ainda não possui uma conta?"
        JLabel lblPergunta = new JLabel("Ainda não possui uma conta?");
        lblPergunta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        painelLogin.add(lblPergunta, gbc);

// 6. Botão "Cadastre-se!"
        JButton btnCadastrar = new JButton("Cadastre-se!");
        btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCadastrar.setPreferredSize(new Dimension(180, 35));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        painelLogin.add(btnCadastrar, gbc);

        btnLogin.addActionListener(e -> {
            String loginInput = txtIdentificacao.getText();
            String passwordInput = new String(txtSenha.getPassword());

            try {
                if(controller.validLogin(loginInput, passwordInput)){
                    JOptionPane.showMessageDialog(framePai, "Sucesso! Entrando na plataforma.");
                    controller.loginSuccess();
                    framePai.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(framePai, "Senha incorreta ou usuário não existe.");
                }
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = painelLogin.getInputMap(condition);
        ActionMap actionMap = painelLogin.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });

        btnCadastrar.addActionListener(e -> {
            controller.telaCadastro();
            framePai.dispose();
        });

        return painelLogin;
    }

}
