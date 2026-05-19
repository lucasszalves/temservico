import db.PostgresDB;
import sec.SHA256Hasher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class viewUsuario {

    private static Servico servicoSelecionado;

    public static void janelaServicosPrestados(Usuario usuario){
        JFrame frame = new JFrame("Serviços prestados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        int width = 400;
        int height = 300;
        Dimension dimension = new Dimension(width, height);

        // gera o painel principal de login
        JPanel panel = servicosJPanel(width, height, usuario);

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

    private static JPanel servicosJPanel(int width, int height, Usuario usuario) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        int fieldHeight = 25;

        JLabel title = new JLabel("Serviços Prestados", SwingConstants.CENTER);
        title.setBounds(width/2 - 100, 10, 200, fieldHeight + 10);
        title.setFont(title.getFont().deriveFont(20.0f));
        panel.add(title);

        String stringPrestador = "Prestador: " + usuario.getNome();
        JLabel nomeUserLabel = new JLabel(stringPrestador);
        nomeUserLabel.setBounds(0, 30 + fieldHeight, width, fieldHeight);
        panel.add(nomeUserLabel);

        ArrayList<Servico> servicos = usuario.getServicosPrestados();

        String[] colunas = {"ID", "Tipo", "Preço", "Nota média", "Nº de agendamentos"};
        Object[][] dados = new Object[servicos.size()][5];
        for (int i = 0; i < servicos.size(); i++) {
            Servico s = servicos.get(i);
            dados[i][0] = s.getId();
            dados[i][1] = s.getTipo();
            dados[i][2] = s.getPreco();
            dados[i][3] = s.getNotaMedia();
            dados[i][4] = s.getNumAgendamentos();
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis diretamente
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
                // Verifica se foi um clique duplo (evita cliques acidentais)
                int linhaSelecionada = tabela.getSelectedRow();

                // Garante que o usuário realmente clicou em uma linha válida
                if (linhaSelecionada != -1) {
                    // Pegar os dados da linha clicada
//                    Object id = tabela.getValueAt(linhaSelecionada, 0);
                    Servico servico = usuario.getServicosPrestados().get(linhaSelecionada);

                    editarButton.setEnabled(true);
                    excluirButton.setEnabled(true);

                    atualizaServicoSelecionado(servico);
                }
            }
        });

        novoServicoButton.addActionListener(e -> {
            janelaNovoServico(usuario);
        });

        editarButton.addActionListener(e -> {
            if(servicoSelecionado.getNumAgendamentos() >= 1){
                String mensagem = "Serviço de ID " + servicoSelecionado.getId() + " possui agendamentos, portanto ele não pode ser editado.";
                JOptionPane.showMessageDialog(null, mensagem);
            }
            else{
                janelaEditaServico(servicoSelecionado);
            }
        });

        excluirButton.addActionListener(e -> {
            if(servicoSelecionado.getNumAgendamentos() >= 1){
                String mensagem = "Serviço de ID " + servicoSelecionado.getId() + " possui agendamentos, portanto ele não pode ser editado.";
                JOptionPane.showMessageDialog(null, mensagem);
            }
            else{
                janelaEditaServico(servicoSelecionado);
            }
        });

        return panel;
    }

    private static void janelaNovoServico(Usuario usuario) {
        JFrame frame = new JFrame("Novo serviço");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 500;
        int height = 500;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        int leftMargin = 30;
        int supMargin = 100;
        int fieldHeight = 30;
        int fieldHeight2 = 25;
        int widthLabels = 300;

        JLabel title = new JLabel("Novo serviço");
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setBounds(leftMargin, supMargin-20, 300, 30);
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

            // verifica inputs
            if(cidadesInput.isEmpty()){
                JOptionPane.showMessageDialog(null, "Insira pelo menos uma cidade.");
            }

            String[] cidadesStrings = cidadesInput.split(",");
            ArrayList<String> cidades = new ArrayList<>(Arrays.asList(cidadesStrings));

            String[] datasStrings = datasInput.split(",");
            ArrayList<LocalDate> datasIndisponiveis = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                for (String data : datasStrings) {
                    data = data.strip();
                    datasIndisponiveis.add(LocalDate.parse(data, formatter));
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Insira uma data válida.");
            }

            Servico novoServico = new Servico(2, tipoServico, precoInput, datasIndisponiveis, cidades, usuario);
            usuario.addServicosPrestados(novoServico);
            JOptionPane.showMessageDialog(null, "Serviço criado com sucesso!");



        });


            frame.setVisible(true);
    }

    private static void janelaEditaServico(Servico servicoSelecionado) {
        JFrame frame = new JFrame("Editar serviço");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = 500;
        int height = 500;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        JLabel title = new JLabel("Editar serviço", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(20.0f));
        frame.add(title);




        frame.setVisible(true);
    }

    private static void atualizaServicoSelecionado(Object servico) {
        servicoSelecionado = (Servico) servico;
    }
}
