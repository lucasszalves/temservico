package controller;

import model.Usuario;
import view.ViewCadastroUsuario;

import java.security.NoSuchAlgorithmException;

public class ControllerCadastroUsuario {
    private MainController mainController;
    private ViewCadastroUsuario viewCadastroUsuario;

    public ControllerCadastroUsuario(MainController mc){
        mainController = mc;
        viewCadastroUsuario = new ViewCadastroUsuario(this);
    }

    public void inicia(){
        viewCadastroUsuario.janelaCadastroUsuario();
    }

    public record RetornoValidaCadastro (boolean valido, String mensagem) implements  Retorno {}

    public RetornoValidaCadastro ValidaCadastro(String nome, String CPF, String email, String senha) throws NoSuchAlgorithmException {
        String mensagem = "Cadastro bem-sucedido!";
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
            mensagem = "A senha deve ter mais de 8 caracteres";
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
}
