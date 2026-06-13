package controller;

import model.Usuario;
import view.ViewLogin;
import view.ViewServicosPrestados;

import java.util.ArrayList;
import java.util.Objects;

public class ControllerLogin {
    private ArrayList<Usuario> usuariosGerais;
    private ViewLogin viewLogin;

    public ControllerLogin(ArrayList<Usuario> usuarios){
        usuariosGerais = usuarios;
        viewLogin = new ViewLogin(this);
    }

    public void inicia(){
        viewLogin.janelaLogin();
    }

    public ViewServicosPrestados.UsersCols checkLoginInput(String loginInput) {
        if(myUtils.isNumeric(loginInput) && loginInput.length() == 11){
            return ViewServicosPrestados.UsersCols.CPF;
        }
        if(loginInput.contains("@")){
            return ViewServicosPrestados.UsersCols.EMAIL;
        }
        return ViewServicosPrestados.UsersCols.NAME;
    }

    public String getPasswordHash(String param, ViewServicosPrestados.UsersCols col) {
        for(Usuario usuario : usuariosGerais){
            switch (col){
                case NAME -> {
                    if(Objects.equals(param, usuario.getNome())){
                        return usuario.getHashSenha();
                    }
                }
                case EMAIL -> {
                    if(Objects.equals(param, usuario.getEmail())){
                        return usuario.getHashSenha();
                    }
                }
                case CPF -> {
                    if(Objects.equals(param, usuario.getCPF())){
                        return usuario.getHashSenha();
                    }
                }
                default -> {
                    return "";
                }
            }
        }
        return "";
    }
}
