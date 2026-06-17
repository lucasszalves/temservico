package view;

import com.sun.tools.javac.Main;
import controller.EditorServicoConfigs;
import controller.MainController;
import model.Servico;
import model.TipoServico;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewServicosPrestados {

    private Servico servicoSelecionado;
    private static final int leftMargin = 30;
    private static final int supMargin = 100;
    private static final int fieldHeight = 30;
    private static final int fieldHeight2 = 25;
    private static final int widthLabels = 300;
    private MainController mainController;
    private Usuario usuario;

    public ViewServicosPrestados(MainController mc, Usuario u){
        this.mainController = mc;
        this.usuario = u;
    }

    public void janelaServicosPrestados(){
//        mainController.printaTudo();
        JFrame frame = new JFrame("Serviços prestados");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        int width = 400;
        int height = 300;
        Dimension dimension = new Dimension(width, height);

        JPanel panel = servicosJPanel(width, height, usuario, frame);

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

    private JPanel servicosJPanel(int width, int height, Usuario usuario, JFrame framePai) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Meus Serviços Prestados", SwingConstants.CENTER);
        title.setBounds(width/2 - 250, 10, 500, fieldHeight + 10);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(title);

        String stringPrestador = "Prestador: " + usuario.getNome();
        JLabel nomeUserLabel = new JLabel(stringPrestador);
        nomeUserLabel.setBounds(0, 30 + fieldHeight, width, fieldHeight);
        panel.add(nomeUserLabel);

        // gera tabela de servicos do usuário
        ArrayList<Servico> servicos = mainController.getServicosPrestados(usuario.getCPF());

        String[] colunas = {"ID", "Tipo", "Preço", "Nota média", "Nº de agendamentos"};
        Object[][] dados = new Object[servicos.size()][5];
        for (int i = 0; i < servicos.size(); i++) {
            Servico s = servicos.get(i);
            String notaMedia = String.valueOf(s.getNotaMedia());
            if(s.getNotaMedia() > 5){
                notaMedia = "N/A";
            }
            dados[i][0] = s.getId();
            dados[i][1] = s.getTipo();
            dados[i][2] = s.getPreco();
            dados[i][3] = notaMedia;
            dados[i][4] = s.getNumAgendamentos();
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(0, 55 + fieldHeight, width, 150);
        panel.add(scrollPane);

        JButton editarButton = new JButton("Editar");
        editarButton.setBounds(0, 220 + fieldHeight , 75, fieldHeight);
        editarButton.setEnabled(false);
        panel.add(editarButton);

        JButton excluirButton = new JButton("Excluir");
        excluirButton.setBounds(90, 220 + fieldHeight, 75, fieldHeight);
        excluirButton.setEnabled(false);
        panel.add(excluirButton);

        JButton novoServicoButton = new JButton("Novo Serviço");
        novoServicoButton.setBounds(width - 150, 220 + fieldHeight, 150, fieldHeight);
        panel.add(novoServicoButton);

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();

                if (linhaSelecionada != -1) {
                    Servico servico = servicos.get(linhaSelecionada);

                    editarButton.setEnabled(true);
                    excluirButton.setEnabled(true);

                    atualizaServicoSelecionado(servico);
                }
            }
        });

        // clicou na opção de novo serviço
        novoServicoButton.addActionListener(e -> {
            janelaNovoServico(usuario, framePai);
        });

        // selecionou um serviço e clicou no botão de editar
        editarButton.addActionListener(e -> {
            janelaEditaServico(servicoSelecionado, framePai);
        });

        // selecionou um serviço e clicou no botão de excluir
        excluirButton.addActionListener(e -> {
            janelaExcluiServico(servicoSelecionado, usuario, framePai);
        });

        return panel;
    }

    private void janelaNovoServico(Usuario usuario, JFrame framePai) {
        JFrame frame = new JFrame("Novo serviço");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 500;
        int height = 500;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel title = new JLabel("Novo serviço");
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(leftMargin, supMargin-20, 300, fieldHeight);
        frame.add(title);

        JLabel tipoServicoLabel = new JLabel("Tipo do serviço: ");
        tipoServicoLabel.setBounds(leftMargin, supMargin+fieldHeight, widthLabels, fieldHeight);
        frame.add(tipoServicoLabel);

        JComboBox<TipoServico> comboTipo = new JComboBox<>(TipoServico.values());
        comboTipo.setBounds(leftMargin + widthLabels - 100, supMargin+fieldHeight, width - widthLabels, fieldHeight2);
        frame.add(comboTipo);

        JLabel precoLabel = new JLabel("Preço do serviço: ");
        precoLabel.setBounds(leftMargin, supMargin+fieldHeight*2, widthLabels, fieldHeight);
        frame.add(precoLabel);

        SpinnerModel model = new SpinnerNumberModel(0, 0, 10000, 1);
        JSpinner precoSpinner = new JSpinner(model);
        precoSpinner.setBounds(leftMargin + widthLabels - 100, supMargin+fieldHeight*2, width - widthLabels, fieldHeight2);
        frame.add(precoSpinner);

        JLabel cidadesLabel = new JLabel("Cidades de antendimento");
        cidadesLabel.setBounds(leftMargin, supMargin+fieldHeight*3, widthLabels, fieldHeight);
        frame.add(cidadesLabel);

        JTextField cidadesText = new JTextField(20);
        cidadesText.setBounds(leftMargin + widthLabels - 100, supMargin+fieldHeight*3, width - widthLabels, fieldHeight2);
        frame.add(cidadesText);

        JLabel cidadesLabel2 = new JLabel("(separe por vírgula): ");
        cidadesLabel2.setBounds(leftMargin, supMargin+fieldHeight*4, widthLabels, fieldHeight);
        frame.add(cidadesLabel2);

        JLabel datasIndispLabel = new JLabel("Datas indisponíveis do serviço");
        datasIndispLabel.setBounds(leftMargin, supMargin+fieldHeight*5, widthLabels, fieldHeight);
        frame.add(datasIndispLabel);

        JTextField datasText = new JTextField(20);
        datasText.setBounds(leftMargin + widthLabels - 100, supMargin+fieldHeight*5, width - widthLabels, fieldHeight2);
        frame.add(datasText);

        JLabel datasIndispLabel2 = new JLabel("(formato DD/MM/AAAA, separados por vírgula): ");
        datasIndispLabel2.setBounds(leftMargin, supMargin+fieldHeight*6, widthLabels, fieldHeight);
        frame.add(datasIndispLabel2);

        JButton novoServicoButton = new JButton("Confirmar");
        novoServicoButton.setBounds(width/2 - widthLabels/2, supMargin+fieldHeight*7, widthLabels, fieldHeight);
        frame.add(novoServicoButton);

        novoServicoButton.addActionListener(e -> {
            // coleta inputs
            String cidadesInput = cidadesText.getText().strip();
            String datasInput = datasText.getText().strip();
            int precoInput = (int) precoSpinner.getValue();
            TipoServico tipoServico = (TipoServico) comboTipo.getSelectedItem();
            SaidaValidadaValoresServico saida = validaValoresServico(true, true, cidadesInput, datasInput);

            // se tudo correu bem, cria novo serviço, atualiza lista de serviços prestados do usuário (para consistência)
            // e atualiza tabela
            if(saida.isValido()) {
                Servico novoServico = new Servico(tipoServico, precoInput, saida.getDatas(), saida.getCidades(), usuario);
                mainController.addServicosGerais(novoServico);
                JOptionPane.showMessageDialog(null, "Serviço criado com sucesso!");
                framePai.dispose();
                janelaServicosPrestados();
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void janelaEditaServico(Servico servicoSelecionado, JFrame framePai) {
        boolean haAgendamentos = haAgendamentosParaServico(servicoSelecionado);

        if(!haAgendamentos) {
            JFrame frame = new JFrame("Editar serviço");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            int width = 700;
            int height = 500;
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            int leftMargin2 = leftMargin + widthLabels + 10;

            JLabel title = new JLabel("Editar serviço");
            title.setFont(title.getFont().deriveFont(20.0f));
            title.setBounds(leftMargin, supMargin - 20, width, fieldHeight);
            frame.add(title);

            JLabel dadosLabel = new JLabel("Dados do serviço:");
            dadosLabel.setFont(dadosLabel.getFont().deriveFont(15.0f));
            dadosLabel.setBounds(leftMargin, supMargin + fieldHeight, widthLabels, fieldHeight);
            frame.add(dadosLabel);

            JLabel idLabel = new JLabel("ID: " + servicoSelecionado.getId());
            idLabel.setBounds(leftMargin, supMargin + fieldHeight * 2, widthLabels, fieldHeight);
            frame.add(idLabel);

            JLabel tipoLabel = new JLabel("Tipo: " + servicoSelecionado.getTipo());
            tipoLabel.setBounds(leftMargin, supMargin + fieldHeight * 3, widthLabels, fieldHeight);
            frame.add(tipoLabel);

            JLabel precoLabel = new JLabel("Preço: " + servicoSelecionado.getPreco());
            precoLabel.setBounds(leftMargin, supMargin + fieldHeight * 4, widthLabels, fieldHeight);
            frame.add(precoLabel);

            JLabel notaMediaLabel = new JLabel("Nota média: " + servicoSelecionado.getNotaMedia());
            notaMediaLabel.setBounds(leftMargin, supMargin + fieldHeight * 5, widthLabels, fieldHeight);
            frame.add(notaMediaLabel);

            JLabel nomePrestadorLabel = new JLabel("Nome do prestador: " + usuario.getNome());
            nomePrestadorLabel.setBounds(leftMargin, supMargin + fieldHeight * 6, widthLabels, fieldHeight);
            frame.add(nomePrestadorLabel);

            JLabel cidadesLabel = new JLabel("Cidades de atendimento: " + servicoSelecionado.getCidades().toString().substring(1, servicoSelecionado.getCidades().toString().length() - 1));
            cidadesLabel.setBounds(leftMargin, supMargin + fieldHeight * 7, widthLabels, fieldHeight);
            frame.add(cidadesLabel);

            ArrayList<LocalDate> datasIndisp = servicoSelecionado.getDatasIndisponiveis();
            String datasIndispString;
            if (datasIndisp.isEmpty()) {
                datasIndispString = "não há";
            } else {
                datasIndispString = datasIndisp.toString().substring(1, datasIndisp.toString().length() - 1);
            }

            JLabel datasIndispLabel = new JLabel("Datas indisponíveis: " + datasIndispString);
            datasIndispLabel.setBounds(leftMargin, supMargin + fieldHeight * 8, widthLabels, fieldHeight);
            frame.add(datasIndispLabel);

            // ===============================================================

            JLabel instrucoesEditarLabel = new JLabel("Assinale os campos a serem editados:");
            instrucoesEditarLabel.setFont(instrucoesEditarLabel.getFont().deriveFont(15.0f));
            instrucoesEditarLabel.setBounds(leftMargin2, supMargin, widthLabels, fieldHeight);
            frame.add(instrucoesEditarLabel);

            JLabel instrucoesEditarLabel2 = new JLabel("(Campos de cidade e datas, separe por vírgula)");
            instrucoesEditarLabel2.setBounds(leftMargin2, supMargin + fieldHeight, widthLabels, fieldHeight);
            frame.add(instrucoesEditarLabel2);

            JComboBox<TipoServico> novoTipoCombo = new JComboBox<>(TipoServico.values());
            novoTipoCombo.setBounds(leftMargin2 + 30, supMargin + fieldHeight * 3, widthLabels - 30, fieldHeight);
            novoTipoCombo.setEnabled(false);
            frame.add(novoTipoCombo);
            JCheckBox checkBoxTipo = new JCheckBox();
            checkBoxTipo.setBounds(leftMargin2, supMargin + fieldHeight * 3, 25, 25);
            checkBoxTipo.addItemListener(e -> {
                novoTipoCombo.setEnabled(checkBoxTipo.isSelected());
            });
            frame.add(checkBoxTipo);

            SpinnerModel model = new SpinnerNumberModel(0, 0, 10000, 1);
            JSpinner novoPrecoSpinner = new JSpinner(model);
            novoPrecoSpinner.setBounds(leftMargin2 + 30, supMargin + fieldHeight * 4, widthLabels - 30, fieldHeight);
            novoPrecoSpinner.setEnabled(false);
            frame.add(novoPrecoSpinner);
            JCheckBox checkBoxPreco = new JCheckBox();
            checkBoxPreco.setBounds(leftMargin2, supMargin + fieldHeight * 4, 25, 25);
            checkBoxPreco.addItemListener(e -> {
                novoPrecoSpinner.setEnabled(checkBoxPreco.isSelected());
            });
            frame.add(checkBoxPreco);

            JTextField novasCidadesText = new JTextField(20);
            novasCidadesText.setBounds(leftMargin2 + 30, supMargin + fieldHeight * 7, widthLabels - 30, fieldHeight);
            novasCidadesText.setEnabled(false);
            frame.add(novasCidadesText);
            JCheckBox checkBoxCidades = new JCheckBox();
            checkBoxCidades.setBounds(leftMargin2, supMargin + fieldHeight * 7, 25, 25);
            checkBoxCidades.addItemListener(e -> {
                novasCidadesText.setEnabled(checkBoxCidades.isSelected());
            });
            frame.add(checkBoxCidades);

            JTextField novasDatasText = new JTextField(20);
            novasDatasText.setBounds(leftMargin2 + 30, supMargin + fieldHeight * 8, widthLabels - 30, fieldHeight);
            novasDatasText.setEnabled(false);
            frame.add(novasDatasText);
            JCheckBox checkBoxDatas = new JCheckBox();
            checkBoxDatas.setBounds(leftMargin2, supMargin + fieldHeight * 8, 25, 25);
            checkBoxDatas.addItemListener(e -> {
                novasDatasText.setEnabled(checkBoxDatas.isSelected());
            });
            frame.add(checkBoxDatas);

            JButton confirmarButton = new JButton("Confirmar");
            confirmarButton.setBounds(width / 2 - widthLabels / 2, supMargin + fieldHeight * 9 + 30, widthLabels, fieldHeight);
            frame.add(confirmarButton);

            confirmarButton.addActionListener(e -> {
                String cidadesInput = novasCidadesText.getText().strip();
                String datasInput = novasDatasText.getText().strip();
                int precoInput = (int) novoPrecoSpinner.getValue();
                TipoServico tipoServico = (TipoServico) novoTipoCombo.getSelectedItem();
                EditorServicoConfigs configs = new EditorServicoConfigs(servicoSelecionado.getId(), checkBoxCidades.isSelected(), checkBoxDatas.isSelected(), checkBoxPreco.isSelected(), checkBoxTipo.isSelected());
                SaidaValidadaValoresServico saida = validaValoresServico(configs.isEditaCidades(), configs.isEditaDatasIndisp(), cidadesInput, datasInput);

                if (saida.isValido()) {
                    if (configs.isEditaTipo()) {
                        configs.setNovoTipo(tipoServico);
                    }
                    if (configs.isEditaPreco()) {
                        configs.setNovoPreco(precoInput);
                    }
                    if (configs.isEditaCidades()) {
                        configs.setNovasCidades(saida.getCidades());
                    }
                    if (configs.isEditaDatasIndisp()) {
                        configs.setNovasDatasIndisp(saida.getDatas());
                    }
                    mainController.editaServico(servicoSelecionado.getId(), configs);
                    JOptionPane.showMessageDialog(null, "Serviço editado com sucesso!");
                    framePai.dispose();
                    janelaServicosPrestados();
                    frame.dispose();
                }
            });
            frame.setVisible(true);
        }
        else{
            String mensagem = "Serviço de ID " + servicoSelecionado.getId() + " possui agendamentos, portanto ele não pode ser editado.";
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }

    private void janelaExcluiServico(Servico servicoSelecionado, Usuario usuario, JFrame framePai) {
        int resposta = JOptionPane.showConfirmDialog(
                framePai,
                "Deseja realmente excluir o serviço?",
                "Confirma?",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resposta == JOptionPane.OK_OPTION) {
            mainController.excluiServico(servicoSelecionado.getId());
            framePai.dispose();
            janelaServicosPrestados();
        }
    }

    private SaidaValidadaValoresServico validaValoresServico(boolean validaCidades, boolean validaDatas, String cidadesInput, String datasInput) {
        SaidaValidadaValoresServico saida = new SaidaValidadaValoresServico();

        if(validaCidades) {
            if (cidadesInput.isBlank()) {
                JOptionPane.showMessageDialog(null, "Insira pelo menos uma cidade.");
                saida.setValido(false);
                return saida;
            }
            String[] cidadesStrings = cidadesInput.split(",");
            for (int i = 0; i < cidadesStrings.length; i++) {
                cidadesStrings[i] = cidadesStrings[i].trim();
            }
            saida.setCidades(new ArrayList<>(Arrays.asList(cidadesStrings)));
        }

        if(validaDatas) {
            ArrayList<LocalDate> datasIndisponiveis = new ArrayList<>();
            if (!datasInput.isBlank()) {
                String[] datasStrings = datasInput.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    for (String data : datasStrings) {
                        data = data.strip();
                        datasIndisponiveis.add(LocalDate.parse(data, formatter));
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Insira uma data válida.");
                    saida.setValido(false);
                    return saida;
                }
            }
            saida.setDatas(datasIndisponiveis);
        }
        saida.setValido(true);
        return saida;
    }

    private boolean haAgendamentosParaServico(Servico servico) {
        return (servico.getNumAgendamentos() > 0);
    }

    private void atualizaServicoSelecionado(Object servico) {
        servicoSelecionado = (Servico) servico;
    }

}
