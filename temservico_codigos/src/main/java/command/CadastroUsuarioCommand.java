package command;

import controller.ControllerCadastroUsuario;
import controller.ControllerCadastroUsuario.RetornoValidaCadastro;
import view.ViewCadastroUsuario;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CadastroUsuarioCommand extends Command{
    private ControllerCadastroUsuario controller;

    public CadastroUsuarioCommand(ViewCadastroUsuario view, ControllerCadastroUsuario controller) {
        super(view);
        this.controller = controller;
    }

    @Override
    public RetornoValidaCadastro execute() throws NoSuchAlgorithmException {
        List<Object> listaInputs = view.retornaInputs();
        String nome = listaInputs.getFirst().toString();
        String CPF = listaInputs.get(1).toString();
        String email = listaInputs.get(2).toString();
        String senha = listaInputs.get(3).toString();
        String repetirSenha = listaInputs.get(4).toString();
        return controller.ValidaCadastro(nome, CPF, email, senha, repetirSenha);
    }
}
