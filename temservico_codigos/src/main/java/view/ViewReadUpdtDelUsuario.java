package view;

import controller.ControllerReadUpdtDelUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class ViewReadUpdtDelUsuario extends JFrame {
    private ControllerReadUpdtDelUsuario controller;
    private Usuario usuarioLogado;

    public ViewReadUpdtDelUsuario(ControllerReadUpdtDelUsuario controllerReadUpdtDelUsuario, Usuario usuarioLogado) {
        this.controller = controllerReadUpdtDelUsuario;
        this.usuarioLogado = usuarioLogado;
    }

    public void janelaDetalhes() {
        setTitle("Detalhes da conta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setVisible(true);
    }
}
