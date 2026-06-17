package view;

import controller.ControllerAgendamentosUsuario;
import model.Agendamento;
import model.Avaliacao;
import model.Servico;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewAgendamentosUsuario extends JFrame {
    private ControllerAgendamentosUsuario controller;
    private Usuario usuarioLogado;
    private Agendamento agendamentoSelecionado;

    public ViewAgendamentosUsuario(ControllerAgendamentosUsuario controller, Usuario usuario){
        this.controller = controller;
        this.usuarioLogado = usuario;
    }

    public void janelaAgendamentos() {
        setTitle("Detalhes da conta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        ArrayList<Agendamento> agendamentos = controller.getAgendamentosContratados(usuarioLogado.getCPF());

        JPanel painelAgendamentos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título
        JLabel lblTitulo = new JLabel("Meus Agendamentos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 30, 10);
        painelAgendamentos.add(lblTitulo, gbc);

// 2. Configurando o Modelo da Tabela
        String[] colunas = {"Data", "Tipo", "Nome do Prestador", "Preço"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição direta do texto na célula
            }
        };

        if (agendamentos != null) {
            for (Agendamento agendamento : agendamentos) {
                Servico servico = controller.getServicoByID(agendamento.getIDservico());
                Usuario prestador = controller.getUsuarioByCPF(servico.getCPFprestador());
                Object[] linha = {
                    agendamento.getData(),
                    servico.getTipo(),
                    prestador.getNome(),
                    servico.getPreco()
                };
                modeloTabela.addRow(linha);
            }
        }

// 3. Criando a Tabela com o Modelo
        JTable tabelaAgendamentos = new JTable(modeloTabela);
        tabelaAgendamentos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaAgendamentos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaAgendamentos.setRowHeight(25);
        tabelaAgendamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);
        scrollPane.setPreferredSize(new Dimension(600, 300));

// Configurando o layout para a tabela esticar
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Tabela absorve o espaço vertical
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 20, 20);
        painelAgendamentos.add(scrollPane, gbc);

// 4. Sub-painel para os Botões (Cancelar e Pagar)
// Usando GridBagLayout para garantir que NUNCA fiquem um em cima do outro
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBotoes = new GridBagConstraints();
        gbcBotoes.insets = new Insets(0, 15, 0, 15); // Espaçamento de 15px nas laterais de cada botão (30px no meio)

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.setEnabled(false); // Já começa desabilitado

        JButton btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnPagar.setPreferredSize(new Dimension(150, 40));
        btnPagar.setEnabled(false); // Já começa desabilitado

// Adiciona Cancelar na coluna 0
        gbcBotoes.gridx = 0;
        gbcBotoes.gridy = 0;
        painelBotoes.add(btnCancelar, gbcBotoes);

// Adiciona Pagar na coluna 1
        gbcBotoes.gridx = 1;
        painelBotoes.add(btnPagar, gbcBotoes);

// Resetando os pesos do GridBagLayout principal para adicionar o painelBotoes
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10);
        painelAgendamentos.add(painelBotoes, gbc);

        // AÇÕES ABAIXO

        tabelaAgendamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabelaAgendamentos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Agendamento agendamento = agendamentos.get(linhaSelecionada);
                    btnCancelar.setEnabled(true);
                    btnPagar.setEnabled(true);
                    setAgendamentoSelecionado(agendamento);
                }
            }
        });

        btnPagar.addActionListener(e -> {
            pagarAgendamento(agendamentoSelecionado, controller.getServicoByID(agendamentoSelecionado.getIDservico()));
        });
        btnCancelar.addActionListener(e -> {
            cancelarAgendamento(agendamentoSelecionado);
        });


        add(painelAgendamentos);
        setVisible(true);
    }

    private void pagarAgendamento(Agendamento agendamentoSelecionado, Servico servico) {
        if(JOptionPane.showConfirmDialog(this,
                "O serviço foi concluído? (PROSSEGUE PARA PAGAMENTO SIMULADO)",
                "Confirma?",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE)
                == JOptionPane.OK_OPTION){
            String msg = String.format("Transferindo um total de R$ %.2f", servico.getPreco());
            JOptionPane optPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optPane.createDialog(this, "Por favor aguarde...");
            Timer timer = new Timer(2000, e -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();
            dialog.setVisible(true);
            controller.excluiAgendamento(agendamentoSelecionado);
            controller.addServicoConcluido(servico);
            controller.refresh();
            if(JOptionPane.showConfirmDialog(this,
                    "Deseja avaliar o serviço?",
                    "Confirma?",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE)
                    == JOptionPane.OK_OPTION){
                avaliarServico(servico, agendamentoSelecionado.getData());
            }
        }
    }

    private void avaliarServico(Servico servico, LocalDate data) {
        JFrame frameAvaliar = new JFrame();
        frameAvaliar.setTitle("Avaliar serviço");
        frameAvaliar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAvaliar.setSize(500, 500);
        frameAvaliar.setLocationRelativeTo(null);
        frameAvaliar.setLayout(new BorderLayout());

        JPanel painelAvaliacao = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título
        JLabel lblTitulo = new JLabel("Avaliar Serviço");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 30, 10);
        painelAvaliacao.add(lblTitulo, gbc);

// 2. Linha da Nota (Label "Nota:" + Radio Buttons)
        JLabel lblNota = new JLabel("Nota:");
        lblNota.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        painelAvaliacao.add(lblNota, gbc);

// Sub-painel para agrupar os RadioButtons horizontalmente
        JPanel painelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        ButtonGroup grupoNotas = new ButtonGroup(); // Garante que apenas 1 seja selecionado

// Laço para criar os botões de 0 a 5 rapidamente
        for (int i = 0; i <= 5; i++) {
            JRadioButton rdbNota = new JRadioButton(String.valueOf(i));
            rdbNota.setFont(new Font("SansSerif", Font.PLAIN, 16));
            rdbNota.setActionCommand(String.valueOf(i));
            grupoNotas.add(rdbNota); // Adiciona ao grupo lógico
            painelRadios.add(rdbNota); // Adiciona ao painel visual
        }

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelAvaliacao.add(painelRadios, gbc);

// 3. Label Comentário
        JLabel lblComentario = new JLabel("Comentário:");
        lblComentario.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Volta a ocupar a linha toda
        gbc.anchor = GridBagConstraints.WEST; // Alinha à esquerda
        gbc.insets = new Insets(20, 10, 5, 10);
        painelAvaliacao.add(lblComentario, gbc);

// 4. Área de Texto para o Comentário
        JTextArea txtComentario = new JTextArea(6, 30); // 6 linhas de altura, 30 colunas de largura
        txtComentario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtComentario.setLineWrap(true); // Quebra a linha automaticamente ao chegar na borda
        txtComentario.setWrapStyleWord(true); // Garante que a quebra ocorra no espaço entre palavras, não no meio de uma

// Adiciona a barra de rolagem à área de texto
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH; // Permite esticar a caixa de texto
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Absorve o espaço vertical livre da janela
        gbc.insets = new Insets(0, 10, 20, 10);
        painelAvaliacao.add(scrollComentario, gbc);

// 5. Botão Confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnConfirmar.setPreferredSize(new Dimension(150, 40));

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE; // Reseta para não esticar o botão
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o botão
        gbc.insets = new Insets(10, 10, 30, 10);
        painelAvaliacao.add(btnConfirmar, gbc);

        btnConfirmar.addActionListener(e -> {
            ButtonModel modeloSelecionado = grupoNotas.getSelection();

            if (modeloSelecionado != null) {
                int nota = Integer.parseInt(modeloSelecionado.getActionCommand());
                String descricao = txtComentario.getText();
                controller.addAvaliacaoServico(servico, data, descricao, nota);
                frameAvaliar.dispose();
                dispose();
                JOptionPane.showMessageDialog(this, "Avaliação concluída!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frameAvaliar,
                        "Selecione uma nota antes de confirmar.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        frameAvaliar.add(painelAvaliacao);
        frameAvaliar.setVisible(true);
    }

    private void cancelarAgendamento(Agendamento agendamentoSelecionado) {
        if(JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja cancelar o agendamento?",
                "Confirma?",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE)
                == JOptionPane.OK_OPTION){
            controller.excluiAgendamento(agendamentoSelecionado);
            controller.refresh();
        }
    }

    private void setAgendamentoSelecionado(Agendamento agendamento) {
        this.agendamentoSelecionado = agendamento;
    }
}
