import java.time.LocalDate;
import java.util.ArrayList;

public class GUIServicosPrestados {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("12631096916", "souzaalves.lucas.2017@gmail.com", "Lucas");
        Usuario user2 = new Usuario("11111111111", "bababab@ababa.com", "bababa");
        ArrayList<Servico> servicos = new ArrayList<>();
        ArrayList<String> cidades = new ArrayList<>();
        cidades.add("Florianopolis");

        servicos.add(new Servico(0, TipoServico.ELETRICA, 100, new ArrayList<LocalDate>(), cidades, user1));
        Servico servico1 = new Servico(1, TipoServico.ENCANAMENTO, 250, new ArrayList<LocalDate>(), cidades, user1);
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        agendamentos.add(new Agendamento(0, servico1, user2, LocalDate.of(2026, 5, 18)));
        servico1.setAgendamentos(agendamentos);
        servicos.add(servico1);
        user1.setServicosPrestados(servicos);
        viewUsuario.janelaServicosPrestados(user1);
    }
}
