package controller;

import model.Usuario;
import view.ViewMenuPrincipal;

public class ControllerMenuPrincipal {
    private MainController mainController;
    private ViewMenuPrincipal view;
    private Usuario usuarioLogado;

    public ControllerMenuPrincipal(MainController c, Usuario usuarioLogado){
        mainController = c;
        this.usuarioLogado = usuarioLogado;
        view = new ViewMenuPrincipal(this, this.usuarioLogado);
    }

    public void inicia() {
        view.janelaPrincipal();
    }

    public void logout() {
        mainController.uc12_logout();
    }

    public void detalhes() {
        mainController.uc01_ReadUpdtDelUsuario();
    }

    public void servicosPrestados() {
        mainController.uc03_CRUDServicosPrestados();
    }

    public void buscaServicos() {
        mainController.uc07_buscaServicos();
    }

    public void agendamentosUsuario() {
        mainController.uc04_06_10_11_agendamentosUsuario();
    }

    public void fecha() {
        view.dispose();
    }

}
