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
        mainController.logout();
    }

    public void detalhes() {

    }
}
