package controller;

import model.Usuario;
import view.ViewUnloggedMenu;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainController {
    private ArrayList<Usuario> usuariosGerais;
    private Usuario usuarioLogado;

    public MainController() throws NoSuchAlgorithmException {
        usuariosGerais = new ArrayList<>();
        appendUsuariosGerais(new Usuario("11111111111", "admin@admin.com", "admin", "1234"));
    }

    public void inicia(){
        menuEntrar();
    }

    public void setUsuariosGerais(ArrayList<Usuario> usuariosGerais) {
        this.usuariosGerais = usuariosGerais;
    }

    public void appendUsuariosGerais(Usuario usuario){
        this.usuariosGerais.add(usuario);
        printUsuarios();
    }

    public void menuEntrar(){
        ViewUnloggedMenu viewUnloggedMenu = new ViewUnloggedMenu(this);
        viewUnloggedMenu.janelaMenuEntrar();
    }

    public void uc01_cadastrarUsuario() {
        ControllerCadastroUsuario controllerCadastroUsuario = new ControllerCadastroUsuario(this);
        controllerCadastroUsuario.inicia();
    }

    public void uc02_login(){
        ControllerLogin controllerLogin = new ControllerLogin(usuariosGerais, this);
        controllerLogin.inicia();
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

}
