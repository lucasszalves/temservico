package view;

import command.CadastroUsuarioCommand;
import command.Command;
import controller.ControllerCadastroUsuario;
import controller.MainController;
import controller.RetornoValidaMsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ViewCadastroUsuario extends JFrame implements View{
    private ControllerCadastroUsuario controller;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtRepetirSenha;


    public ViewCadastroUsuario(ControllerCadastroUsuario c){
        controller = c;
    }

    public void janelaCadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelCadastro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título "Cadastro de Usuário"
        JLabel lblTitulo = new JLabel("Cadastro de Usuário");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10); // Espaçamento maior embaixo
        painelCadastro.add(lblTitulo, gbc);

// Resetando as configurações de grid para os campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

// 2. Campo "Nome"
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblNome, gbc);

        txtNome = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtNome, gbc);

// 3. Campo "CPF"
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblCpf, gbc);

        txtCpf = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtCpf, gbc);

// 4. Campo "E-mail"
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblEmail, gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtEmail, gbc);

// 5. Campo "Senha"
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblSenha, gbc);

        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtSenha, gbc);

// 6. Campo "Repita a senha" (NOVO)
        JLabel lblRepetirSenha = new JLabel("Repita a senha:");
        lblRepetirSenha.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblRepetirSenha, gbc);

        txtRepetirSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtRepetirSenha, gbc);

// 7. Botão "Confirmar" (Agora no gridy = 6)
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnConfirmar.setPreferredSize(new Dimension(150, 35));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Ocupa as duas colunas para centralizar
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10); // Espaçamento maior em cima
        painelCadastro.add(btnConfirmar, gbc);

// 8. Texto "Já tem uma conta?" (Agora no gridy = 7)
        JLabel lblPerguntaConta = new JLabel("Já tem uma conta?");
        lblPerguntaConta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        painelCadastro.add(lblPerguntaConta, gbc);

// 9. Botão "Login" (Agora no gridy = 8)
        JButton btnIrParaLogin = new JButton("Login");
        btnIrParaLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIrParaLogin.setPreferredSize(new Dimension(150, 35));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        painelCadastro.add(btnIrParaLogin, gbc);

        // AÇÕES ABAIXO VVVVVVVVVV

        btnConfirmar.addActionListener(e -> {
            Command command = new CadastroUsuarioCommand(this, controller);
            RetornoValidaMsg retorno;
            try {
                retorno = command.execute();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(this, retorno.mensagem());
            if(retorno.valido()){
                controller.menuEntrar();
                dispose();
            }
        });
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = painelCadastro.getInputMap(condition);
        ActionMap actionMap = painelCadastro.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConfirmar.doClick();
            }
        });

        btnIrParaLogin.addActionListener(e -> {
            controller.telaLogin();
            dispose();
        });

        add(painelCadastro);
        setVisible(true);
    }

    @Override
    public List<Object> retornaInputs(){
        List<Object> listaRetorno = new ArrayList<>();
        listaRetorno.add(txtNome.getText().strip());
        listaRetorno.add(txtCpf.getText().strip());
        listaRetorno.add(txtEmail.getText().strip());
        listaRetorno.add(new String(txtSenha.getPassword()));
        listaRetorno.add(new String(txtRepetirSenha.getPassword()));
        return listaRetorno;
    }
}
