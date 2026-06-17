package view;

import controller.ControllerMenuPrincipal;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class ViewMenuPrincipal extends JFrame {
    private ControllerMenuPrincipal controller;
    private Usuario usuarioLogado;

    public ViewMenuPrincipal(ControllerMenuPrincipal c, Usuario usuarioLogado){
        controller = c;
        this.usuarioLogado = usuarioLogado;
    }

    public void janelaPrincipal(){
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelHome = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// Permite que a tela principal estique os componentes horizontalmente
        gbc.gridx = 0;
        gbc.weightx = 1.0;

// 1. Título "TemServiço?" centralizado
        JLabel lblTitulo = new JLabel("TemServiço?");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 10, 20, 10);
        painelHome.add(lblTitulo, gbc);

// =========================================================
// 2. Sub-painel "Meus dados" (Agora com seu próprio GridBagLayout)
// =========================================================
        JPanel painelDados = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDados = new GridBagConstraints();
        painelDados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Meus dados",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.PLAIN, 14)
        ));
        gbcDados.insets = new Insets(5, 10, 5, 10); // Margem interna dos itens

// Label Usuário (Esquerda, Esticável)
        String nome = usuarioLogado.getNome();
        String lblUsuarioTexto = "Usuário: " + nome;
        JLabel lblUsuario = new JLabel(lblUsuarioTexto);
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbcDados.gridx = 0;
        gbcDados.gridy = 0;
        gbcDados.weightx = 1.0; // Absorve espaço extra ao redimensionar a janela
        gbcDados.fill = GridBagConstraints.HORIZONTAL; // Permite que a label cresça
        gbcDados.anchor = GridBagConstraints.WEST; // Cola na esquerda
        painelDados.add(lblUsuario, gbcDados);

// Label CPF (Centro, Esticável)
        String CPF = usuarioLogado.getCPF();
        String lblCpfTexto = "CPF: " + CPF;
        JLabel lblCpf = new JLabel(lblCpfTexto);
        lblCpf.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER); // Garante que o texto fique no meio da sua área
        gbcDados.gridx = 1;
        gbcDados.weightx = 1.0; // Também absorve espaço extra
        painelDados.add(lblCpf, gbcDados);

// Botão Detalhes (Direita, Fixo)
        JButton btnDetalhes = new JButton("Detalhes da Conta");
        btnDetalhes.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbcDados.gridx = 2;
        gbcDados.weightx = 0.0; // NÃO absorve espaço extra (mantém tamanho fixo)
        gbcDados.fill = GridBagConstraints.NONE;
        gbcDados.anchor = GridBagConstraints.EAST; // Cola na direita
        painelDados.add(btnDetalhes, gbcDados);

// Adicionando o painel de dados na tela principal
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Estica o quadro para ocupar a largura da janela principal
        gbc.insets = new Insets(10, 20, 30, 20);
        painelHome.add(painelDados, gbc);
// =========================================================

// Configuração para os botões principais (Centralizados e Fixos)
        gbc.fill = GridBagConstraints.NONE; // Reseta para os botões não esticarem
        gbc.insets = new Insets(10, 10, 10, 10);
        Dimension tamanhoBotao = new Dimension(280, 45);

// 3. Botão "Meus Serviços Prestados"
        JButton btnServicosPrestados = new JButton("Meus Serviços Prestados");
        btnServicosPrestados.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnServicosPrestados.setPreferredSize(tamanhoBotao);
        gbc.gridy = 2;
        painelHome.add(btnServicosPrestados, gbc);

// 4. Botão "Buscar Serviços"
        JButton btnBuscarServicos = new JButton("Buscar Serviços");
        btnBuscarServicos.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnBuscarServicos.setPreferredSize(tamanhoBotao);
        gbc.gridy = 3;
        painelHome.add(btnBuscarServicos, gbc);

// 5. Botão "Meus Agendamentos"
        JButton btnAgendamentos = new JButton("Meus Agendamentos");
        btnAgendamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAgendamentos.setPreferredSize(tamanhoBotao);
        gbc.gridy = 4;
        painelHome.add(btnAgendamentos, gbc);

// 6. Botão "Logout" (Fixo no Canto Inferior Esquerdo)
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 5;
        gbc.weighty = 1.0; // Absorve to do o espaço vertical restante, empurrando o botão pro final
        gbc.anchor = GridBagConstraints.SOUTHWEST; // Ancora no Sul-Oeste
        gbc.insets = new Insets(10, 20, 20, 10);
        painelHome.add(btnLogout, gbc);

        // AÇÕES ABAIXO

        btnDetalhes.addActionListener(e -> { // DONE
            controller.detalhes();
        });
        btnServicosPrestados.addActionListener(e -> { // DONE
            controller.servicosPrestados();
        });
        btnBuscarServicos.addActionListener(e -> { // DOING
            controller.buscaServicos();
        });
        btnAgendamentos.addActionListener(e -> { // DONE
            controller.agendamentosUsuario();
        });
        btnLogout.addActionListener(e -> { //DONE
            controller.logout();
            dispose();
        });

        add(painelHome);
        setVisible(true);
    }
}
