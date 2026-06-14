package view;

import command.CadastroUsuarioCommand;
import command.Command;
import controller.ControllerCadastroUsuario;
import controller.ControllerCadastroUsuario.RetornoValidaCadastro;
import controller.MainController;

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
        gbc.gridwidth = 2; // Faz o título ocupar as duas colunas (labels e textfields)
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10); // Espaçamento maior na parte inferior
        painelCadastro.add(lblTitulo, gbc);

// Resetando o gridwidth para os campos de formulário
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10); // Espaçamento padrão entre as linhas

// 2. Campo "Nome"
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; // Alinha o texto da label à direita
        painelCadastro.add(lblNome, gbc);

        txtNome = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // Alinha a caixa de texto à esquerda
        painelCadastro.add(txtNome, gbc);

// 3. Campo "CPF"
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("SansSerif", Font.PLAIN, 16));
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
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 16));
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
        lblSenha.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelCadastro.add(lblSenha, gbc);

        txtSenha = new JPasswordField(20); // Usando JPasswordField para esconder os caracteres
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        painelCadastro.add(txtSenha, gbc);

// 6. Botão "Confirmar"
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnConfirmar.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Ocupa as duas colunas para ficar centralizado
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10); // Espaçamento maior na parte superior para separar do formulário
        painelCadastro.add(btnConfirmar, gbc);
        btnConfirmar.addActionListener(e -> {
            Command command = new CadastroUsuarioCommand(this, controller);
            RetornoValidaCadastro retorno;
            try {
                retorno = (RetornoValidaCadastro) command.execute();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            if(retorno != null && retorno.valido()){
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                controller.menuEntrar();
                dispose();
            }
            else{
                assert retorno != null;
                JOptionPane.showMessageDialog(this, retorno.mensagem());
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
        return listaRetorno;
    }
}
