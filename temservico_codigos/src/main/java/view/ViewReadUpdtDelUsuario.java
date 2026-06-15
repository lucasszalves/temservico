package view;

import controller.ControllerReadUpdtDelUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

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
//            janelaEditarUsuario();
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
}
