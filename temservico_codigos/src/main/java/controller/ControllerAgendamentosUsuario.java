package controller;

import model.Agendamento;
import model.Avaliacao;
import model.Servico;
import model.Usuario;
import view.ViewAgendamentosUsuario;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerAgendamentosUsuario {
    private MainController mainController;
    private Usuario usuarioLogado;
    private ViewAgendamentosUsuario view;

    public ControllerAgendamentosUsuario(ControllerMenuPrincipal controllerMenuPrincipal, Usuario usuarioLogado, MainController mainController) {
        this.usuarioLogado = usuarioLogado;
        this.mainController = mainController;
        view = new ViewAgendamentosUsuario(this, this.usuarioLogado);
    }

    public void inicia(){
        view.janelaAgendamentos();
    }

    public ArrayList<Agendamento> getAgendamentosContratados(String CPF) {
        return mainController.getAgendamentosContratados(CPF);
    }

    public Servico getServicoByID(int id) {
        return mainController.getServicoByID(id);
    }

    public Usuario getUsuarioByCPF(String CPFprestador) {
        return mainController.getUsuarioByCPF(CPFprestador);
    }

    public void excluiAgendamento(Agendamento agendamento) {
        mainController.excluiAgendamento(agendamento.getId());
    }

    public void addAvaliacaoServico(Servico servico, LocalDate data, String descricao, int nota) {
        Avaliacao avaliacao = new Avaliacao(servico, data, descricao, nota);
        mainController.addAvaliacaoServico(servico.getId(), avaliacao);
    }

    public void refresh() {
        view.dispose();
        view = new ViewAgendamentosUsuario(this, usuarioLogado);
        inicia();
    }
}
