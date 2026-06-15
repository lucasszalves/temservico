package controller;

import model.Usuario;
import view.ViewReadUpdtDelUsuario;

public class ControllerReadUpdtDelUsuario {
    private MainController mainController;
    private Usuario usuarioLogado;
    private ViewReadUpdtDelUsuario view;

    public ControllerReadUpdtDelUsuario(MainController mainController, Usuario usuarioLogado) {
        this.mainController = mainController;
        this.usuarioLogado = usuarioLogado;
        view = new ViewReadUpdtDelUsuario(this, usuarioLogado);
    }

    public void inicia() {
        view.janelaDetalhes();
    }
}
