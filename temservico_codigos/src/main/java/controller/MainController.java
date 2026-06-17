package controller;

import model.Agendamento;
import model.Avaliacao;
import model.Servico;
import model.Usuario;
import view.ViewServicosPrestados;
import view.ViewUnloggedMenu;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import static controller.myUtils.capitalize;

public class MainController {
    private ArrayList<Usuario> usuariosGerais;
    private ArrayList<Servico> servicosGerais;
    private ArrayList<Agendamento> agendamentosGerais;
    private ControllerMenuPrincipal controllerMenuPrincipal;
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
    }

    public void addServicosGerais(Servico servico){
        this.servicosGerais.add(servico);
        addDatasIndispUsuario(servico.getDatasIndisponiveis(), servico.getCPFprestador());
        printaTudo();
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
        ControllerReadUpdtDelUsuario controllerRUDUsuario = new ControllerReadUpdtDelUsuario(this, usuarioLogado, this.controllerMenuPrincipal);
        controllerRUDUsuario.inicia();
    }

    public void uc02_login(){
        ControllerLogin controllerLogin = new ControllerLogin(usuariosGerais, this);
        controllerLogin.inicia();
    }

    public void mainMenu(){
        this.controllerMenuPrincipal = new ControllerMenuPrincipal(this, usuarioLogado);
        this.controllerMenuPrincipal.inicia();
    }

    public void uc03_CRUDServicosPrestados(){
        // feito antes de ser implementado o MVC corretamente, por isso ta tudo na View :|
        ViewServicosPrestados view = new ViewServicosPrestados(this, usuarioLogado);
        view.janelaServicosPrestados();
    }

    public void uc04_06_10_11_agendamentosUsuario() {
        ControllerAgendamentosUsuario controllerAgendamentosUsuario = new ControllerAgendamentosUsuario(this.controllerMenuPrincipal, this.usuarioLogado, this);
        controllerAgendamentosUsuario.inicia();
    }

    public void loginSuccess(Usuario usuarioLogadoInput) {
        usuarioLogado = usuarioLogadoInput;
        mainMenu();
    }

    public void uc12_logout(){
        usuarioLogado = null;
        menuEntrar();
    }


    public void uc07_buscaServicos() {
        ControllerBuscaServicos controllerBuscaServicos = new ControllerBuscaServicos(this);
        controllerBuscaServicos.inicia();
    }

    public void printUsuarios(){
        for(Usuario usuario : usuariosGerais){
            System.out.println(usuario);
        }
    }

    public Usuario getUsuarioByCPF(String CPF) {
        for(Usuario u : usuariosGerais){
            if(u.getCPF().equals(CPF)){
                return u;
            }
        }
        return null;
    }

    public Servico getServicoByID(int id){
        for(Servico s : servicosGerais){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    public Agendamento getAgendamentoByID(int id){
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

    public ArrayList<Servico> buscaServicosByParams(ParamsBuscaServico params){
        ArrayList<Servico> servicosBuscados = new ArrayList<>();
        for(Servico servico : this.servicosGerais){
            if(params.isBuscaTipo() && !params.getTipoServico().equals(servico.getTipo())){
                // se a busca procura por um tipo específico e o serviço não é desse tipo, continua
                continue;
            }
            if(params.isBuscaNota() && !(params.getNotaMin() <= servico.getNotaMedia())) {
                // se a busca procura por uma nota mínima e o serviço não tem a nota maior que ela, continua
                continue;
            }
            if(params.isBuscaCidade() && !(servico.getCidades().contains(capitalize(params.getCidade())))) {
                // se a busca procura por cidades específicas e o serviço não atende um subset dessas cidades, continua
                continue;
            }
            if(params.isBuscaPrecoMin() && !(params.getPrecoMin() <= servico.getPreco())) {
                // se a busca procura por um preço mínimo e o serviço não tem o preço acima, continua
                continue;
            }
            if(params.isBuscaPrecoMax() && !(params.getPrecoMax() >= servico.getPreco())) {
                // se a busca procura por um preço máximo e o serviço não tem o preço abaixo, continua
                continue;
            }
            servicosBuscados.add(servico);
        }
        return servicosBuscados;
    }

    public void agendaServico(Agendamento agendamento){
        ArrayList<LocalDate> data = new ArrayList<>();
        data.add(agendamento.getData());
        Usuario contratante = getUsuarioByCPF(agendamento.getCPFcontratante());
        Usuario prestador = getUsuarioByCPF(getServicoByID(agendamento.getIDservico()).getCPFprestador());
        addDatasIndispUsuario(data, contratante.getCPF());
        addDatasIndispUsuario(data, prestador.getCPF());
        addAgendamentosGerais(agendamento);
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

    public void editaUsuario(String CPF, EditorUsuarioConfigs configs) throws NoSuchAlgorithmException {
        for(Usuario u : this.usuariosGerais){
            if(u.getCPF().equals(CPF)){
                if(configs.isEditaNome()){
                    u.setNome(configs.getNovoNome());
                }
                if(configs.isEditaEmail()){
                    u.setEmail(configs.getNovoEmail());
                }
                if(configs.isEditaSenha()){
                    u.changeSenha(configs.getNovaSenha());
                }
                return;
            }
        }
    }

    public void addDatasIndispUsuario(ArrayList<LocalDate> datas, String CPF){
        for(Usuario u : this.usuariosGerais){
            if(u.getCPF().equals(CPF)){
                for(LocalDate d : datas){
                    u.addDatasIndisponiveis(d);
                }
                return;
            }
        }
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

    public void addAvaliacaoServico(int idServico, Avaliacao avaliacao){
        for (int i = 0; i < servicosGerais.size(); i++) {
            if(servicosGerais.get(i).getId() == idServico){
                servicosGerais.get(i).addAvaliacao(avaliacao);
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
                printaTudo();
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
                Usuario prestador = getUsuarioByCPF(servicosGerais.get(i).getCPFprestador());
                for(LocalDate data : servicosGerais.get(i).getDatasIndisponiveis()){
                    liberaData(prestador, data);
                }
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

    public void excluiAgendamento(int id){
        for (int i = 0; i < agendamentosGerais.size(); i++) {
            if(agendamentosGerais.get(i).getId() == id){
                Servico servico = getServicoByID(agendamentosGerais.get(i).getIDservico());
                Usuario prestador = getUsuarioByCPF(servico.getCPFprestador());
                Usuario contratante = getUsuarioByCPF(agendamentosGerais.get(i).getCPFcontratante());
                LocalDate data = agendamentosGerais.get(i).getData();
                servico.rmAgendamento();
                liberaData(prestador, data);
                liberaData(contratante, data);
                agendamentosGerais.remove(i);
                printaTudo();
                return;
            }
        }
    }

    private void liberaData(Usuario usuario, LocalDate data) {
        usuario.liberaData(data);
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















