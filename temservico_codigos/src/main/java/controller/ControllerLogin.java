package controller;

import model.Usuario;
import sec.SHA256Hasher;
import view.ViewLogin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ControllerLogin {
    private ArrayList<Usuario> usuariosGerais;
    private ViewLogin viewLogin;
    private MainController mainController;
    private Usuario usuarioLogado;

    public ControllerLogin(ArrayList<Usuario> usuarios, MainController mainControllerInput){
        usuariosGerais = usuarios;
        mainController = mainControllerInput;
        viewLogin = new ViewLogin(this);
    }

    public void inicia(){
        viewLogin.janelaLogin();
    }

    public LoginTypes checkLoginInput(String loginInput) {
        if(myUtils.isNumeric(loginInput) && loginInput.length() == 11){
            return LoginTypes.CPF;
        }
        if(loginInput.contains("@")){
            return LoginTypes.EMAIL;
        }
        return LoginTypes.NAME;
    }

    public boolean validLogin(String loginInput, String password) throws NoSuchAlgorithmException {
        LoginTypes loginType = checkLoginInput(loginInput);
        boolean resultado = false;
        String passwordHashInput = SHA256Hasher.hashString(password);
        for(Usuario usuario : usuariosGerais){
            switch (loginType){
                case NAME -> {
                    if(loginInput.equals(usuario.getNome()) && passwordHashInput.equals(usuario.getHashSenha())){
                        resultado = true;
                    }
                }
                case EMAIL -> {
                    if(loginInput.equals(usuario.getEmail()) && passwordHashInput.equals(usuario.getHashSenha())){
                        resultado = true;
                    }
                }
                case CPF -> {
                    if(loginInput.equals(usuario.getCPF()) && passwordHashInput.equals(usuario.getHashSenha())){
                        resultado = true;
                    }
                }
                default -> {
                    return false;
                }
            }
            if (resultado) {
                usuarioLogado = usuario;
                break;
            }
        }
        return resultado;
    }

    public void loginSuccess() {
        mainController.loginSuccess(usuarioLogado);
    }

    public void telaCadastro(){
        mainController.uc01_CreateUsuario();
    }
}
