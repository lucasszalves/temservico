package controller;

import model.Agendamento;
import model.Servico;
import model.Usuario;
import view.ViewServicosPrestados;
import view.ViewUnloggedMenu;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainController {
    private ArrayList<Usuario> usuariosGerais;
    private ArrayList<Servico> servicosGerais;
    private ArrayList<Agendamento> agendamentosGerais;
    private Usuario usuarioLogado;

    public MainController() throws NoSuchAlgorithmException {
        usuariosGerais = new ArrayList<>();
        servicosGerais = new ArrayList<>();
        agendamentosGerais = new ArrayList<>();
        addUsuariosGerais(new Usuario("11111111111", "admin@admin.com", "admin", "1234"));
    }

    public void inicia(){
        menuEntrar();
    }

    public void setUsuariosGerais(ArrayList<Usuario> usuariosGerais) {
        this.usuariosGerais = usuariosGerais;
    }

    public void addUsuariosGerais(Usuario usuario){
        this.usuariosGerais.add(usuario);
        printUsuarios();
    }

    public void addServicosGerais(Servico servico){
        this.servicosGerais.add(servico);
    }

    public void addAgendamentosGerais(Agendamento agendamento){
        this.agendamentosGerais.add(agendamento);
        getServicoByID(agendamento.getIDservico()).addAgendamento();
    }

    public void menuEntrar(){
        ViewUnloggedMenu viewUnloggedMenu = new ViewUnloggedMenu(this);
        viewUnloggedMenu.janelaMenuEntrar();
    }

    public void uc01_CreateUsuario() {
        ControllerCadastroUsuario controllerCadastroUsuario = new ControllerCadastroUsuario(this, usuariosGerais);
        controllerCadastroUsuario.inicia();
    }

    public void uc01_ReadUpdtDelUsuario() {
        ControllerReadUpdtDelUsuario controllerRUDUsuario = new ControllerReadUpdtDelUsuario(this, usuarioLogado);
        controllerRUDUsuario.inicia();
    }

    public void uc02_login(){
        ControllerLogin controllerLogin = new ControllerLogin(usuariosGerais, this);
        controllerLogin.inicia();
    }

    public void uc03_CRUDServicosPrestados(){
        // feito antes de ser implementado o MVC corretamente, por isso ta tudo na View :|
        ViewServicosPrestados view = new ViewServicosPrestados(this, usuarioLogado);
        view.janelaServicosPrestados();
    }

    public void loginSuccess(Usuario usuarioLogadoInput) {
        usuarioLogado = usuarioLogadoInput;
        mainMenu();
    }

    public void logout(){
        usuarioLogado = null;
        menuEntrar();
    }

    public void mainMenu(){
        ControllerMenuPrincipal controllerMenuPrincipal = new ControllerMenuPrincipal(this, usuarioLogado);
        controllerMenuPrincipal.inicia();
    }

    public void printUsuarios(){
        for(Usuario usuario : usuariosGerais){
            System.out.println(usuario);
        }
    }

    private Usuario getUsuarioByCPF(String CPF) {
        for(Usuario u : usuariosGerais){
            if(u.getCPF().equals(CPF)){
                return u;
            }
        }
        return null;
    }

    private Servico getServicoByID(int id){
        for(Servico s : servicosGerais){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    private Agendamento getAgendamentoByID(int id){
        for(Agendamento a : agendamentosGerais){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    // serviços prestados pelo usuário de CPF indicado
    public ArrayList<Servico> getServicosPrestados(String CPF) {
        ArrayList<Servico> servicosPrestados = new ArrayList<>();
        for(Servico s : servicosGerais){
            if(s.getCPFprestador().equals(CPF)){
                servicosPrestados.add(s);
            }
        }
        return servicosPrestados;
    }

    // agendamentos de serviços prestados pelo ID do servico
    public ArrayList<Agendamento> getAgendamentosServicoPrestado(int id){
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        for(Agendamento a : agendamentosGerais){
            if(a.getIDservico() == id){
                agendamentos.add(a);
            }
        }
        return agendamentos;
    }

    public void editaServico(int id, EditorServicoConfigs configs){
        for(Servico servico : this.servicosGerais){
            if (servico.getId() == id){
                if (configs.isEditaCidades()){
                    servico.setCidades(configs.getNovasCidades());
                }
                if (configs.isEditaDatasIndisp()){
                    servico.setDatasIndisponiveis(configs.getNovasDatasIndisp());
                }
                if (configs.isEditaPreco()){
                    servico.setPreco(configs.getNovoPreco());
                }
                if (configs.isEditaTipo()){
                    servico.setTipo(configs.getNovoTipo());
                }
                return;
            }
        }
    }

    public void excluiUsuario(String CPF){
        for (int i = 0; i < usuariosGerais.size(); i++) {
            if(usuariosGerais.get(i).getCPF().equals(CPF)){
                excluiAgendamentosDeServicoContratado(usuariosGerais.get(i));
                excluiServicosPrestados(usuariosGerais.get(i));
                usuariosGerais.remove(i);
                return;
            }
        }
    }

    private void excluiAgendamentosDeServicoContratado(Usuario usuario) {
        for (int i = agendamentosGerais.size() - 1; i >= 0; i--) {
            if(agendamentosGerais.get(i).getCPFcontratante().equals(usuario.getCPF())){
                getServicoByID(agendamentosGerais.get(i).getIDservico()).rmAgendamento();
                agendamentosGerais.remove(i);
            }
        }
    }

    public void excluiServicosPrestados(Usuario usuario){
        for (int i = servicosGerais.size() - 1; i >= 0; i--) {
            if(servicosGerais.get(i).getCPFprestador().equals(usuario.getCPF())){
                System.out.println(servicosGerais.get(i));
                excluiAgendamentosDoServico(servicosGerais.get(i));
                servicosGerais.remove(i);
            }
        }
    }

    public void excluiServico(Servico servico){
        excluiAgendamentosDoServico(servico);
        servicosGerais.remove(servico);
    }

    public void excluiServico(int id){
        for (int i = 0; i < servicosGerais.size(); i++) {
            if(servicosGerais.get(i).getId() == id){
                excluiAgendamentosDoServico(servicosGerais.get(i));
                servicosGerais.remove(i);
                return;
            }
        }
    }

    public void excluiAgendamentosDoServico(Servico servico){
        for(int i = 0; i < agendamentosGerais.size(); i++){
            if(agendamentosGerais.get(i).getIDservico() == servico.getId()){
                servico.rmAgendamento();
                agendamentosGerais.remove(i);
                return;
            }
        }
    }

    // agendamentos de serviços CONTRATADOS pelo usuário de CPF indicado
    public ArrayList<Agendamento> getAgendamentosContratados(String CPF){
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        for(Agendamento a : agendamentosGerais){
            if(a.getCPFcontratante().equals(CPF)){
                agendamentos.add(a);
            }
        }
        return agendamentos;
    }

    public void excluiAgendamento(int id){
        for (int i = 0; i < agendamentosGerais.size(); i++) {
            if(agendamentosGerais.get(i).getId() == id){
                getServicoByID(agendamentosGerais.get(i).getIDservico()).rmAgendamento();
                agendamentosGerais.remove(i);
                return;
            }
        }
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void printaTudo(){
        System.out.println("========= USUÁRIOS =========");
        for(Usuario u : usuariosGerais){
            System.out.println(u);
        }
        System.out.println("========= SERVIÇOS =========");
        for(Servico s : servicosGerais){
            System.out.println(s);
        }
        System.out.println("========= AGENDAMENTOS =========");
        for(Agendamento a : agendamentosGerais){
            System.out.println(a);
        }
    }
}















