import javax.swing.*;
import java.awt.*;

public class ComboBoxEnumExemplo {

    // 1. Criando o Enumerate (Enum)
    public enum PerfilUsuario {
        ADMINISTRADOR,
        GERENTE,
        OPERADOR,
        CONVIDADO
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ComboBox com Enum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 150);
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);

            // 2. Criando o JComboBox passando os valores do Enum diretamente
            // Usamos Generics <PerfilUsuario> para garantir a tipagem do componente
            JComboBox<PerfilUsuario> comboPerfil = new JComboBox<>(PerfilUsuario.values());

            // 3. Criando um botão para testar a captura do item selecionado
            JButton btnExibir = new JButton("Confirmar Perfil");

            btnExibir.addActionListener(e -> {
                // Ao pegar o item, ele já vem convertido no tipo do seu Enum!
                PerfilUsuario perfilSelecionado = (PerfilUsuario) comboPerfil.getSelectedItem();

                // Agora você pode usar em estruturas de controle (switch/if) com total segurança
                switch (perfilSelecionado) {
                    case ADMINISTRADOR -> JOptionPane.showMessageDialog(frame, "Acesso total liberado.");
                    case CONVIDADO -> JOptionPane.showMessageDialog(frame, "Acesso estrito de leitura.");
                    default -> JOptionPane.showMessageDialog(frame, "Perfil selecionado: " + perfilSelecionado);
                }
            });

            // Adicionando os componentes na tela
            frame.add(new JLabel("Selecione o nível de acesso:"));
            frame.add(comboPerfil);
            frame.add(btnExibir);

            frame.setVisible(true);
        });
    }
}