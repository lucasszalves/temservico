package controller;

import model.Usuario;
import view.ViewCadastroUsuario;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ControllerCadastroUsuario {
    private MainController mainController;
    private ViewCadastroUsuario viewCadastroUsuario;
    private ArrayList<Usuario> usuariosGerais;

    public ControllerCadastroUsuario(MainController mc, ArrayList<Usuario> usuariosGerais){
        mainController = mc;
        this.usuariosGerais = usuariosGerais;
        viewCadastroUsuario = new ViewCadastroUsuario(this);
    }

    public void inicia(){
        viewCadastroUsuario.janelaCadastroUsuario();
    }

    public record RetornoValidaCadastro (boolean valido, String mensagem) implements  Retorno {}

    public RetornoValidaCadastro ValidaCadastro(String nome, String CPF, String email, String senha, String repetirSenha) throws NoSuchAlgorithmException {
        String mensagem = "Usuário cadastrado com sucesso!";
        boolean valido = true;
        if(nome.isEmpty()){
            mensagem = "Insira um nome.";
            valido = false;
        }
        else if (CPF.length() != 11 || !myUtils.isNumeric(CPF)){
            mensagem = "Insira um CPF válido.";
            valido = false;
        }
        else if (!email.contains("@")){
            mensagem = "Insira um e-mail válido.";
            valido = false;
        }
        else if (senha.length() < 8){
            mensagem = "A senha deve ter mais de 8 caracteres.";
            valido = false;
        }
        else if (!senha.equals(repetirSenha)){
            mensagem = "As senhas não batem.";
            valido = false;
        }
        else if (usuarioExistente(CPF, email)){
            mensagem = "CPF ou e-mail já cadastrado.";
            valido = false;
        }

        if(valido){
            cadastraUsuario(nome, CPF, email, senha);
        }

        return new RetornoValidaCadastro(valido, mensagem);
    }

    public void cadastraUsuario(String nome, String CPF, String email, String senha) throws NoSuchAlgorithmException {
        Usuario novoUsuario = new Usuario(CPF, email, nome, senha);
        mainController.appendUsuariosGerais(novoUsuario);
    }

    public void menuEntrar(){
        mainController.menuEntrar();
    }

    public void telaLogin() {
        mainController.uc02_login();
    }

    public boolean usuarioExistente(String CPF, String email){
        boolean existe = false;
        for (Usuario uExistente : usuariosGerais){
            if(CPF.equals(uExistente.getCPF()) || email.equals(uExistente.getEmail())){
                existe = true;
                break;
            }
        }
        return existe;
    }
}
