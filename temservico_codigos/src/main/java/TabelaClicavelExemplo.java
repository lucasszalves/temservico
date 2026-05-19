import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabelaClicavelExemplo {

    public static void main(String[] args) {
        // Garante que a GUI seja criada na Thread do Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Exemplo de Tabela Clicável");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null); // Centraliza a janela

            // 1. Criar as colunas e os dados da tabela
            String[] colunas = {"ID", "Nome", "Profissão"};
            Object[][] dados = {
                    {1, "Ana Silva", "Desenvolvedora Backend"},
                    {2, "Bruno Costa", "Designer UX"},
                    {3, "Carlos Souza", "Gerente de Projetos"}
            };

            // 2. Inicializar o modelo e a JTable
            DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Torna as células não editáveis diretamente
                }
            };

            JTable tabela = new JTable(modelo);

            // 3. Adicionar o Evento de Clique na Linha
            tabela.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Verifica se foi um clique duplo (evita cliques acidentais)
                    if (e.getClickCount() == 2) {
                        int linhaSelecionada = tabela.getSelectedRow();

                        // Garante que o usuário realmente clicou em uma linha válida
                        if (linhaSelecionada != -1) {
                            // Pegar os dados da linha clicada
                            Object id = tabela.getValueAt(linhaSelecionada, 0);
                            Object nome = tabela.getValueAt(linhaSelecionada, 1);
                            Object profissao = tabela.getValueAt(linhaSelecionada, 2);

                            // Chamar a função desejada passando os dados
                            executarAcaoDaLinha(id, nome, profissao);
                        }
                    }
                }
            });

            // 4. Adicionar a tabela a um JScrollPane (necessário para ver os cabeçalhos)
            JScrollPane scrollPane = new JScrollPane(tabela);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    // 5. A sua função que será executada ao clicar na linha
    private static void executarAcaoDaLinha(Object id, Object nome, Object profissao) {
        String mensagem = String.format("Usuário Selecionado:\nID: %s\nNome: %s\nProfissão: %s", id, nome, profissao);

        // Aqui você pode abrir outra tela, deletar, editar, etc.
        JOptionPane.showMessageDialog(null, mensagem, "Ação Executada", JOptionPane.INFORMATION_MESSAGE);
    }
}