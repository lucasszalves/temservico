package guiTests;

import controller.MainController;
import model.Agendamento;
import model.Servico;
import model.TipoServico;
import model.Usuario;
import view.ViewServicosPrestados;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GUIServicosPrestados_Teste {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MainController mc = new MainController();
        Usuario user1 = new Usuario("12631096916", "souzaalves.lucas.2017@gmail.com", "Lucas", "banana");
        Usuario user2 = new Usuario("12312312311", "admin@admin.com", "admin", "1234");


        ArrayList<String> cidades = new ArrayList<>();
        cidades.add("florianopolis");
        cidades.add("palhoca");
        Servico servico1 = new Servico(TipoServico.ENCANAMENTO, 250, new ArrayList<>(), cidades, user1);
        Servico servico2 = new Servico(TipoServico.ELETRICA, 100, new ArrayList<>(), cidades, user1);
//
        Agendamento agendamento = new Agendamento(servico1, user2, LocalDate.of(2026, 5, 18));
//        servico1.addAgendamento(agendamento);
//
//        user1.addIdServicosPrestados(servico1);
//        user1.addIdServicosPrestados(servico2);
        mc.addUsuariosGerais(user1);
        mc.addUsuariosGerais(user2);
        mc.addServicosGerais(servico1);
        mc.addServicosGerais(servico2);
        mc.addAgendamentosGerais(agendamento);
        mc.setUsuarioLogado(user1);
//        mc.excluiUsuario(user1.getCPF());
        mc.mainMenu();
        mc.printaTudo();
    }
}
