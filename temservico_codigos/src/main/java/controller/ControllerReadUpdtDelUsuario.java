package controller;

import model.Usuario;
import view.ViewReadUpdtDelUsuario;

import java.security.NoSuchAlgorithmException;

public class ControllerReadUpdtDelUsuario {
    private MainController mainController;
    private Usuario usuarioLogado;
    private ViewReadUpdtDelUsuario view;
    private ControllerMenuPrincipal controllerMenuPrincipal;

    public ControllerReadUpdtDelUsuario(MainController mainController, Usuario usuarioLogado, ControllerMenuPrincipal controllerMenuPrincipal) {
        this.mainController = mainController;
        this.usuarioLogado = usuarioLogado;
        this.controllerMenuPrincipal = controllerMenuPrincipal;
        view = new ViewReadUpdtDelUsuario(this, this.usuarioLogado);
    }

    public void inicia() {
        view.janelaDetalhes();
    }


    public RetornoValidaMsg editaUsuario(EditorUsuarioConfigs configs) throws NoSuchAlgorithmException {
        boolean valido = true;
        String mensagem = "Usuário editado com sucesso!";
        if(!(configs.isEditaEmail() || configs.isEditaNome() || configs.isEditaSenha())){
            valido = false;
            mensagem = "Selecione pelo menos um campo para editar.";
        }
        else if(configs.isEditaNome() && configs.getNovoNome().isBlank()){
            valido = false;
            mensagem = "Insira um nome ao selecionar este campo.";
        }
        else if(configs.isEditaEmail() && (configs.getNovoEmail().isBlank() || !configs.getNovoEmail().contains("@"))){
            valido = false;
            mensagem = "Insira um e-mail válido ao selecionar este campo.";
        }
        else if(configs.isEditaSenha() && configs.getNovaSenha().length() < 8){
            valido = false;
            mensagem = "Insira uma senha com 8 caracteres ou mais ao selecionar este campo.";
        }
        else if(configs.isEditaSenha() && !configs.getNovaSenha().equals(configs.getNovaSenhaRepetida())){
            valido = false;
            mensagem = "As senhas não batem.";
        }

        if(valido){
            mainController.editaUsuario(usuarioLogado.getCPF(), configs);
            mainController.mainMenu();
            this.controllerMenuPrincipal.fecha();
        }

        return new RetornoValidaMsg(valido, mensagem);
    }

    public void excluiUsuario() {
        mainController.excluiUsuario(usuarioLogado.getCPF());
        mainController.uc12_logout();
        this.controllerMenuPrincipal.fecha();
    }
}
