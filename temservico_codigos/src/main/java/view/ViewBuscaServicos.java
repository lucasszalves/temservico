package view;

import controller.*;
import model.Agendamento;
import model.Avaliacao;
import model.Servico;
import model.TipoServico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static controller.myUtils.capitalize;
import static controller.myUtils.validateDate;

public class ViewBuscaServicos extends JFrame {
    private ControllerBuscaServicos controller;
    private Servico servicoSelecionadoBusca;

    public ViewBuscaServicos(ControllerBuscaServicos controller){
        this.controller = controller;
    }

    public void janelaBusca() {
        setTitle("Buscar serviço");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelBusca = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título
        JLabel lblTitulo = new JLabel("Buscar Serviço");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 30, 10);
        painelBusca.add(lblTitulo, gbc);

// Resetando configurações do grid para os campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

// 2. Tipo
        JCheckBox chkTipo = new JCheckBox("Tipo:");
        chkTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkTipo.setHorizontalTextPosition(SwingConstants.LEFT); // Deixa o texto à esquerda da caixinha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkTipo, gbc);

// Como você já tem o Enum TipoServico, podemos passar o .values() direto
        JComboBox<TipoServico> cbTipo = new JComboBox<>(TipoServico.values());
        cbTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbTipo.setPreferredSize(new Dimension(250, 35));
        cbTipo.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(cbTipo, gbc);

        chkTipo.addActionListener(e -> cbTipo.setEnabled(chkTipo.isSelected()));

// 3. Cidades
        JCheckBox chkCidade = new JCheckBox("Cidade:");
        chkCidade.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkCidade.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkCidade, gbc);

        JTextField txtCidade = new JTextField();
        txtCidade.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtCidade.setPreferredSize(new Dimension(250, 35));
        txtCidade.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(txtCidade, gbc);

        chkCidade.addActionListener(e -> txtCidade.setEnabled(chkCidade.isSelected()));

// 4. Preço Mínimo (Spinner)
        JCheckBox chkPrecoMin = new JCheckBox("Preço Mínimo:");
        chkPrecoMin.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkPrecoMin.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkPrecoMin, gbc);

// Spinner configurado para Double (Valor inicial 0.0, min 0.0, max 99999.0, step 10.0)
        JSpinner spinPrecoMin = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 99999.0, 10.0));
        spinPrecoMin.setEditor(new JSpinner.NumberEditor(spinPrecoMin, "0.00")); // Formata com duas casas decimais
        spinPrecoMin.setPreferredSize(new Dimension(250, 35));
        spinPrecoMin.setFont(new Font("SansSerif", Font.PLAIN, 16));
        spinPrecoMin.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(spinPrecoMin, gbc);

        chkPrecoMin.addActionListener(e -> spinPrecoMin.setEnabled(chkPrecoMin.isSelected()));

// 5. Preço Máximo (Spinner)
        JCheckBox chkPrecoMax = new JCheckBox("Preço Máximo:");
        chkPrecoMax.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkPrecoMax.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkPrecoMax, gbc);

        JSpinner spinPrecoMax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 99999.0, 10.0));
        spinPrecoMax.setEditor(new JSpinner.NumberEditor(spinPrecoMax, "0.00"));
        spinPrecoMax.setPreferredSize(new Dimension(250, 35));
        spinPrecoMax.setFont(new Font("SansSerif", Font.PLAIN, 16));
        spinPrecoMax.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(spinPrecoMax, gbc);

        chkPrecoMax.addActionListener(e -> spinPrecoMax.setEnabled(chkPrecoMax.isSelected()));

// 6. Nota Mínima (Slider)
        JCheckBox chkNota = new JCheckBox("Nota Mínima:");
        chkNota.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkNota.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkNota, gbc);

// Sub-painel para o Slider e a Label dinâmica
        JPanel painelSlider = new JPanel(new BorderLayout(10, 0));

// JSlider vai de 0 a 500 para podermos dividir por 100 e ter 2 casas decimais
        JSlider sliderNota = new JSlider(0, 500, 0);
        sliderNota.setPreferredSize(new Dimension(200, 35));
        sliderNota.setEnabled(false);

        JLabel lblValorNota = new JLabel("0.00");
        lblValorNota.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValorNota.setEnabled(false); // Fica cinza quando o slider está desabilitado

        painelSlider.add(sliderNota, BorderLayout.CENTER);
        painelSlider.add(lblValorNota, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(painelSlider, gbc);

// Atualiza a label em tempo real
        sliderNota.addChangeListener(e -> {
            double valorConvertido = sliderNota.getValue() / 100.0;
            // O String.format com locale US garante o ponto decimal ("1.02") em vez de vírgula
            lblValorNota.setText(String.format(java.util.Locale.US, "%.2f", valorConvertido));
        });

        chkNota.addActionListener(e -> {
            boolean selecionado = chkNota.isSelected();
            sliderNota.setEnabled(selecionado);
            lblValorNota.setEnabled(selecionado);
        });

// 7. Botão Buscar
        JButton btnBuscar = new JButton("Buscar!");
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBuscar.setPreferredSize(new Dimension(180, 45));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Volta a ocupar as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 20, 10); // Espaçamento maior em cima
        painelBusca.add(btnBuscar, gbc);

        btnBuscar.addActionListener(e -> {
            boolean buscaTipo = chkTipo.isSelected();
            boolean buscaCidade = chkCidade.isSelected();
            boolean buscaPrecoMin = chkPrecoMin.isSelected();
            boolean buscaPrecoMax = chkPrecoMax.isSelected();
            boolean buscaNota = chkNota.isSelected();
            ParamsBuscaServico params = new ParamsBuscaServico(buscaTipo, buscaCidade, buscaPrecoMin, buscaPrecoMax, buscaNota, (TipoServico) cbTipo.getSelectedItem(), txtCidade.getText(), (double) spinPrecoMin.getValue(), (double) spinPrecoMax.getValue(), (double) sliderNota.getValue() / 100);
            RetornoValidaMsg retorno = controller.validaParamsBusca(params);
            JOptionPane.showMessageDialog(this, retorno.mensagem());
            if(retorno.valido()){
                resultadosBusca();
            }
        });

        add(painelBusca);
        setVisible(true);
    }

    private void resultadosBusca() {
        ArrayList<Servico> servicosEncontrados = controller.getResultadoBusca();
        if(servicosEncontrados.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nenhum serviço encontrado :(", "Sem resultados", JOptionPane.WARNING_MESSAGE);
        }
        else {
            JFrame frame = new JFrame();
            frame.setTitle("Resultados da busca");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            JPanel painelResultados = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

// 1. Título
            JLabel lblTitulo = new JLabel("Resultados da Busca");
            lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 10, 30, 10);
            painelResultados.add(lblTitulo, gbc);

// 2. Configurando o Modelo da Tabela
            String[] colunas = {"Preço", "Tipo", "Nota Média"};
            DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Retorna false para impedir que o usuário edite o texto direto na célula
                    return false;
                }
            };

            for (Servico servico : servicosEncontrados) {
                String notaMedia = String.format("%.2f", servico.getNotaMedia());
                if(notaMedia.equals("6,00")){
                    notaMedia = "N/A";
                }
                Object[] linha = {
                    String.format("R$ %.2f", servico.getPreco()), // Exemplo de formatação
                    servico.getTipo(),
                    notaMedia
                };
                modeloTabela.addRow(linha);
            }


// 3. Criando a Tabela com o Modelo
            JTable tabelaResultados = new JTable(modeloTabela);
            tabelaResultados.setFont(new Font("SansSerif", Font.PLAIN, 14));
            tabelaResultados.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
            tabelaResultados.setRowHeight(25);
            tabelaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite selecionar só uma linha

// O JScrollPane adiciona a barra de rolagem e exibe os nomes das colunas
            JScrollPane scrollPane = new JScrollPane(tabelaResultados);
            scrollPane.setPreferredSize(new Dimension(600, 300));

// Configurando o layout para a tabela esticar e ocupar o espaço
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0; // Faz a tabela absorver o espaço vertical da janela
            gbc.fill = GridBagConstraints.BOTH; // Estica horizontalmente e verticalmente
            gbc.insets = new Insets(0, 20, 20, 20);
            painelResultados.add(scrollPane, gbc);

// 4. Botão Detalhes
            JButton btnDetalhes = new JButton("Detalhes");
            btnDetalhes.setFont(new Font("SansSerif", Font.BOLD, 16));
            btnDetalhes.setPreferredSize(new Dimension(150, 40));
            btnDetalhes.setEnabled(false); // Já começa desabilitado

// Resetando os pesos para o botão não esticar
            gbc.gridy = 2;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 10, 20, 10);
            painelResultados.add(btnDetalhes, gbc);

// 5. Ação: Ativar botão apenas se houver linha selecionada
            tabelaResultados.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int linhaSelecionada = tabelaResultados.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        btnDetalhes.setEnabled(true);
                        setServicoSelecionadoBusca(servicosEncontrados.get(linhaSelecionada));
                    }
                }
            });

            btnDetalhes.addActionListener(e -> {
                detalhesServico(frame);
            });

            frame.add(painelResultados);
            frame.setVisible(true);
        }
    }

    private void detalhesServico(JFrame framePai) {
        JFrame frame = new JFrame();
        frame.setTitle("Detalhes do serviço");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        ArrayList<Avaliacao> listaAvaliacoes = servicoSelecionadoBusca.getAvaliacoes();

        JPanel painelServico = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título principal
        String titulo = "Serviço de " + capitalize(String.valueOf(servicoSelecionadoBusca.getTipo()));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 30, 10);
        painelServico.add(lblTitulo, gbc);

// ==========================================
// 2. PAINEL CENTRAL (Força o balanceamento 50/50)
// 1 linha, 2 colunas, 40px de espaçamento no meio
// ==========================================
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 40, 0));

// --- 2.1 LADO ESQUERDO: Dados do Serviço ---
        JPanel painelDadosEsquerda = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDados = new GridBagConstraints();
        gbcDados.insets = new Insets(10, 10, 10, 10);

        String nomePrestador = controller.getUsuarioByCPF(servicoSelecionadoBusca.getCPFprestador()).getNome();
        String[] chaves = {"Nome do prestador:", "Preço:", "Nota Média:", "Cidades:", "Atendimentos Concluídos:"};
        for (int i = 0; i < chaves.length; i++) {
            gbcDados.gridy = i;

            // Label da chave (Ex: "Preço:")
            gbcDados.gridx = 0;
            JLabel lblChave = new JLabel(chaves[i]);
            lblChave.setFont(new Font("SansSerif", Font.BOLD, 16));
            painelDadosEsquerda.add(lblChave, gbcDados);

            // Label do valor (Ex: "...")
            String dado;
            switch (i){
                case 0 -> dado = nomePrestador;
                case 1 -> dado = String.format("%.2f", servicoSelecionadoBusca.getPreco());
                case 2 -> dado = (servicoSelecionadoBusca.getNotaMedia() == 6) ? "N/A" : String.format("%.2f", servicoSelecionadoBusca.getNotaMedia());
                case 3 -> dado = String.valueOf(servicoSelecionadoBusca.getCidades());
                case 4 -> dado = String.valueOf(servicoSelecionadoBusca.getServicosConcluidos());
                default -> dado = "";
            }
            gbcDados.gridx = 1;
            JLabel lblValor = new JLabel(dado);
            lblValor.setFont(new Font("SansSerif", Font.PLAIN, 16));
            painelDadosEsquerda.add(lblValor, gbcDados);
        }

// Wrapper para as labels ficarem coladas no topo
        JPanel painelEsquerdaWrapper = new JPanel(new BorderLayout());
        painelEsquerdaWrapper.add(painelDadosEsquerda, BorderLayout.NORTH);
        painelCentral.add(painelEsquerdaWrapper); // Adiciona ao lado esquerdo do GridLayout

// --- 2.2 LADO DIREITO: Painel de Avaliações ---
        JPanel painelListaAvaliacoes = new JPanel();
        painelListaAvaliacoes.setLayout(new BoxLayout(painelListaAvaliacoes, BoxLayout.Y_AXIS));

        if (listaAvaliacoes != null) {
            for (Avaliacao av : listaAvaliacoes) {
                // Cria um "cartão" para cada avaliação
                JPanel cardAvaliacao = new JPanel(new BorderLayout());
                cardAvaliacao.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5), // Espaçamento externo
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY) // Borda cinza
                ));

                JLabel lblNota = new JLabel("Nota: " + av.getNota());
                lblNota.setFont(new Font("SansSerif", Font.BOLD, 14));
                lblNota.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
                cardAvaliacao.add(lblNota, BorderLayout.NORTH);

                // JTextArea para suportar comentários longos com quebra de linha
                JTextArea txtComentario = new JTextArea(av.getDescricao());
                txtComentario.setFont(new Font("SansSerif", Font.PLAIN, 14));
                txtComentario.setLineWrap(true);
                txtComentario.setWrapStyleWord(true);
                txtComentario.setEditable(false); // Apenas leitura
                txtComentario.setOpaque(false); // Fica com o fundo transparente igual ao painel
                txtComentario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                cardAvaliacao.add(txtComentario, BorderLayout.CENTER);

                painelListaAvaliacoes.add(cardAvaliacao);
            }
        }

        JScrollPane scrollAvaliacoes = new JScrollPane(painelListaAvaliacoes);
        scrollAvaliacoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Avaliações",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));
        scrollAvaliacoes.getVerticalScrollBar().setUnitIncrement(16);

        painelCentral.add(scrollAvaliacoes); // Adiciona ao lado direito do GridLayout

// Adiciona o Painel Central ao GridBagLayout principal
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Faz o meio absorver o espaço vertical
        gbc.fill = GridBagConstraints.BOTH; // Estica para todos os lados
        gbc.insets = new Insets(0, 20, 20, 20);
        painelServico.add(painelCentral, gbc);

// ==========================================
// 3. BASE DA TELA: Área de Agendamento
// ==========================================
        JPanel painelAgendamento = new JPanel(new GridBagLayout());
        GridBagConstraints gbcAg = new GridBagConstraints();

// Linha da data
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel lblAgendar = new JLabel("Agendar para dia:");
        lblAgendar.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextField txtData = new JTextField(12);
        txtData.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtData.setHorizontalAlignment(JTextField.CENTER);
        txtData.setText("DD/MM/AAAA");
        txtData.setForeground(Color.GRAY);

        txtData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtData.getText().equals("DD/MM/AAAA")) {
                    txtData.setText("");
                    txtData.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtData.getText().isEmpty()) {
                    txtData.setForeground(Color.GRAY);
                    txtData.setText("DD/MM/AAAA");
                }
            }
        });

        painelData.add(lblAgendar);
        painelData.add(txtData);

        gbcAg.gridy = 0;
        painelAgendamento.add(painelData, gbcAg);

// Botão de Agendar
        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnAgendar.setPreferredSize(new Dimension(180, 45));

        gbcAg.gridy = 1;
        gbcAg.insets = new Insets(15, 0, 0, 0);
        painelAgendamento.add(btnAgendar, gbcAg);

// Inserindo o painel inferior no GridBagLayout principal
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Volta a não esticar na vertical
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 30, 10);
        painelServico.add(painelAgendamento, gbc);

        btnAgendar.addActionListener(e -> {
            LocalDate data;
            try {
                data = validateDate(txtData.getText());
                if(controller.agendaServico(data, servicoSelecionadoBusca)){
                    JOptionPane.showMessageDialog(frame, "Agendamento concluído!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    framePai.dispose();
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(frame, "O prestador está ocupado nessa data :(", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Insira uma data válida", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = painelServico.getInputMap(condition);
        ActionMap actionMap = painelServico.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "enter");
        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAgendar.doClick();
            }
        });


        frame.add(painelServico);
        frame.setVisible(true);
    }

    private void setServicoSelecionadoBusca(Servico servico){
        this.servicoSelecionadoBusca = servico;
    }
}
