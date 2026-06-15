package controller;

public class EditorUsuarioConfigs {
    private String CPF;
    private boolean editaNome;
    private boolean editaEmail;
    private boolean editaSenha;
    private String novoNome;
    private String novoEmail;
    private String novaSenha;
    private String novaSenhaRepetida;

    public EditorUsuarioConfigs(String CPF, boolean editaNome, boolean editaEmail, boolean editaSenha) {
        this.CPF = CPF;
        this.editaNome = editaNome;
        this.editaEmail = editaEmail;
        this.editaSenha = editaSenha;
    }

    public String getCPF() {
        return CPF;
    }

    public boolean isEditaNome() {
        return editaNome;
    }

    public boolean isEditaEmail() {
        return editaEmail;
    }

    public boolean isEditaSenha() {
        return editaSenha;
    }

    public String getNovoNome() {
        return novoNome;
    }

    public String getNovoEmail() {
        return novoEmail;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getNovaSenhaRepetida() {
        return novaSenhaRepetida;
    }

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public void setNovaSenhaRepetida(String novaSenhaRepetida) {
        this.novaSenhaRepetida = novaSenhaRepetida;
    }

}
