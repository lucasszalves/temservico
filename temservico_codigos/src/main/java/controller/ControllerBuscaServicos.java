package controller;

import model.Servico;
import model.Usuario;
import view.ViewBuscaServicos;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerBuscaServicos {
    private MainController mainController;
    private ViewBuscaServicos view;
    private ArrayList<Servico> resultadoBusca;

    public ControllerBuscaServicos(MainController mainController) {
        this.mainController = mainController;
        this.view = new ViewBuscaServicos(this);
    }

    public void inicia(){
        view.janelaBusca();
    }

    public ArrayList<Servico> buscaServicosByParams(ParamsBuscaServico params){
        return mainController.buscaServicosByParams(params);
    }

    public RetornoValidaMsg validaParamsBusca(ParamsBuscaServico params) {
        boolean valido = true;
        String msg = "Busca concluída!";
        if(params.isBuscaCidade() && params.getCidade().isBlank()){
            valido = false;
            msg = "Insira uma cidade ao selecionar esse parâmetro para busca";
        }
        else if(params.isBuscaPrecoMin() && params.isBuscaPrecoMax() && params.getPrecoMin() >= params.getPrecoMax()){
            valido = false;
            msg = "O preço mínimo deve ser menor que o preço máximo";
        }
        if(valido){
            this.resultadoBusca = buscaServicosByParams(params);
        }
        return new RetornoValidaMsg(valido, msg);
    }

    public ArrayList<Servico> getResultadoBusca() {
        return resultadoBusca;
    }

    public boolean agendaServico(LocalDate data, Servico servico){
        Usuario prestador = mainController.getUsuarioByCPF(servico.getCPFprestador());
        return false;
    }
}
