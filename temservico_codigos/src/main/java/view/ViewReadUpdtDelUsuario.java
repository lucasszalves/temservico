package view;

import controller.ControllerReadUpdtDelUsuario;
import controller.EditorUsuarioConfigs;
import controller.RetornoValidaMsg;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;

public class ViewReadUpdtDelUsuario extends JFrame {
    private ControllerReadUpdtDelUsuario controller;
    private Usuario usuarioLogado;

    public ViewReadUpdtDelUsuario(ControllerReadUpdtDelUsuario controllerReadUpdtDelUsuario, Usuario usuarioLogado) {
        this.controller = controllerReadUpdtDelUsuario;
        this.usuarioLogado = usuarioLogado;
    }

    public void janelaDetalhes() {
        setTitle("Detalhes da conta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelDetalhes = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título "Detalhes da Conta"
        JLabel lblTitulo = new JLabel("Detalhes da Conta");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 40, 10); // Espaçamento maior embaixo
        painelDetalhes.add(lblTitulo, gbc);

// Resetando o gridwidth para as linhas de dados
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento padrão entre as linhas

// 2. Linha Nome
        JLabel lblNomeChave = new JLabel("Nome:");
        lblNomeChave.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; // Alinha o texto à direita
        painelDetalhes.add(lblNomeChave, gbc);

        JLabel lblNomeValor = new JLabel(usuarioLogado.getNome()); // Aqui você vai injetar o dado real depois
        lblNomeValor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // Alinha o valor à esquerda
        painelDetalhes.add(lblNomeValor, gbc);

// 3. Linha CPF
        JLabel lblCpfChave = new JLabel("CPF:");
        lblCpfChave.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelDetalhes.add(lblCpfChave, gbc);

        JLabel lblCpfValor = new JLabel(usuarioLogado.getCPF());
        lblCpfValor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelDetalhes.add(lblCpfValor, gbc);

// 4. Linha E-mail
        JLabel lblEmailChave = new JLabel("E-mail:");
        lblEmailChave.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelDetalhes.add(lblEmailChave, gbc);

        JLabel lblEmailValor = new JLabel(usuarioLogado.getEmail());
        lblEmailValor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        painelDetalhes.add(lblEmailValor, gbc);

// 5. Linha Datas Indisponíveis
        JLabel lblDatasChave = new JLabel("Datas Indisponíveis:");
        lblDatasChave.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelDetalhes.add(lblDatasChave, gbc);

        String datasString = String.valueOf(usuarioLogado.getDatasIndisponiveis());
        JLabel lblDatasValor = new JLabel(datasString);
        lblDatasValor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        painelDetalhes.add(lblDatasValor, gbc);

// 6. Sub-painel para os Botões (Editar e Excluir)
// O FlowLayout com center e gap horizontal de 30 garante o espaçamento correto entre eles
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEditar.setPreferredSize(new Dimension(130, 40));

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnExcluir.setPreferredSize(new Dimension(130, 40));

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

// Adicionando o sub-painel de botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // O painel de botões volta a ocupar as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 10, 20, 10); // Espaçamento maior em cima para descolar dos dados
        painelDetalhes.add(painelBotoes, gbc);

        btnEditar.addActionListener(e -> {
            janelaEditarUsuario();
        });
        btnExcluir.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir a sua conta? Todos os seus agendamentos contratados e serviços prestados, juntamente com seus agendamentos, serão excluídos!",
                    "Confirma?",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE)
                    == JOptionPane.OK_OPTION) {
                controller.excluiUsuario();
                dispose();
            }
        });

        add(painelDetalhes);
        setVisible(true);
    }

    private void janelaEditarUsuario() {
        JFrame frameEditar = new JFrame();
        frameEditar.setTitle("Editar conta");
        frameEditar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditar.setSize(500, 500);
        frameEditar.setLocationRelativeTo(null);
        frameEditar.setLayout(new BorderLayout());

        JPanel painelEditar = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título e Instruções
        JLabel lblTitulo = new JLabel("Editar Dados do Usuário");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 5, 10);
        painelEditar.add(lblTitulo, gbc);

        JLabel lblInstrucoes = new JLabel("Assinale os campos a serem editados:");
        lblInstrucoes.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 25, 10);
        painelEditar.add(lblInstrucoes, gbc);

// Resetando configurações
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

// 2. Campo Nome
        JCheckBox chkNome = new JCheckBox("Nome:");
        chkNome.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painelEditar.add(chkNome, gbc);

        JTextField txtNome = new JTextField(20);
        txtNome.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtNome.setPreferredSize(new Dimension(250, 35));
        txtNome.setEnabled(false); // Começa desabilitado
        gbc.gridx = 1;
        gbc.gridy = 2;
        painelEditar.add(txtNome, gbc);

// Ação do Checkbox Nome
        chkNome.addActionListener(e -> txtNome.setEnabled(chkNome.isSelected()));

// 3. Campo E-mail
        JCheckBox chkEmail = new JCheckBox("E-mail:");
        chkEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelEditar.add(chkEmail, gbc);

        JTextField txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtEmail.setPreferredSize(new Dimension(250, 35));
        txtEmail.setEnabled(false); // Começa desabilitado
        gbc.gridx = 1;
        gbc.gridy = 3;
        painelEditar.add(txtEmail, gbc);

// Ação do Checkbox E-mail
        chkEmail.addActionListener(e -> txtEmail.setEnabled(chkEmail.isSelected()));

// 4. Campo Senha
        JCheckBox chkSenha = new JCheckBox("Nova Senha:");
        chkSenha.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelEditar.add(chkSenha, gbc);

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtSenha.setPreferredSize(new Dimension(250, 35));
        txtSenha.setEnabled(false); // Começa desabilitado
        gbc.gridx = 1;
        gbc.gridy = 4;
        painelEditar.add(txtSenha, gbc);

// 5. Campo Repita a Senha (Vinculado ao Checkbox da Senha)
        JLabel lblRepetirSenha = new JLabel("Repita a Senha:");
        lblRepetirSenha.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST; // Alinhado à direita para ficar visualmente sob o checkbox
        painelEditar.add(lblRepetirSenha, gbc);

        JPasswordField txtRepetirSenha = new JPasswordField(20);
        txtRepetirSenha.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtRepetirSenha.setPreferredSize(new Dimension(250, 35));
        txtRepetirSenha.setEnabled(false); // Começa desabilitado
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        painelEditar.add(txtRepetirSenha, gbc);

// Ação do Checkbox Senha (Habilita/Desabilita os DOIS campos de senha)
        chkSenha.addActionListener(e -> {
            boolean isSelected = chkSenha.isSelected();
            txtSenha.setEnabled(isSelected);
            txtRepetirSenha.setEnabled(isSelected);
        });

// 6. Botão Confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnConfirmar.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 20, 10); // Espaçamento maior acima do botão
        painelEditar.add(btnConfirmar, gbc);

        btnConfirmar.addActionListener(e -> {
            boolean editaNome = chkNome.isSelected();
            boolean editaEmail = chkEmail.isSelected();
            boolean editaSenha = chkSenha.isSelected();
            EditorUsuarioConfigs configs = new EditorUsuarioConfigs(usuarioLogado.getCPF(), editaNome, editaEmail, editaSenha);

            configs.setNovoNome(txtNome.getText().strip());
            configs.setNovoEmail(txtEmail.getText().strip());
            configs.setNovaSenha(new String(txtSenha.getPassword()).strip());
            configs.setNovaSenhaRepetida(new String(txtRepetirSenha.getPassword()).strip());

            RetornoValidaMsg retorno = null;
            try {
                retorno = controller.editaUsuario(configs);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(frameEditar, retorno.mensagem());
            if(retorno.valido()){
                frameEditar.dispose();
                dispose();
            }
        });

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = painelEditar.getInputMap(condition);
        ActionMap actionMap = painelEditar.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConfirmar.doClick();
            }
        });

        frameEditar.add(painelEditar);
        frameEditar.setVisible(true);
    }
}
