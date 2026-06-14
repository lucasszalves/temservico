package view;

import com.sun.tools.javac.Main;
import controller.MainController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewUnloggedMenu extends JFrame {
    private MainController controller;

    public ViewUnloggedMenu(MainController c){
        controller = c;
    }

    public void janelaMenuEntrar(){
        setTitle("Entrar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// Configuração padrão de centralização
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

// 1. Título "TemServiço?"
        JLabel lblTitulo = new JLabel("TemServiço?");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        painelEntrada.add(lblTitulo, gbc);

// 2. Subtítulo "Entrar"
        JLabel lblEntrar = new JLabel("Entrar");
        lblEntrar.setFont(new Font("SansSerif", Font.PLAIN, 20));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 30, 10);
        painelEntrada.add(lblEntrar, gbc);

// 3. Botão "Login"
        JButton btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 40, 10);
        painelEntrada.add(btnLogin, gbc);

// 4. Texto "Ainda não possui uma conta?"
        JLabel lblPergunta = new JLabel("Ainda não possui uma conta?");
        lblPergunta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        painelEntrada.add(lblPergunta, gbc);

// 5. Botão "Cadastre-se!"
        JButton btnCadastrar = new JButton("Cadastre-se!");
        btnCadastrar.setPreferredSize(new Dimension(180, 40));
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 10);
        painelEntrada.add(btnCadastrar, gbc);

        add(painelEntrada);

        btnLogin.addActionListener(e -> {
            controller.uc02_login();
            dispose();
        });

        btnCadastrar.addActionListener(e -> {
            controller.uc01_cadastrarUsuario();
            dispose();
        });


        setVisible(true);
    }
}
