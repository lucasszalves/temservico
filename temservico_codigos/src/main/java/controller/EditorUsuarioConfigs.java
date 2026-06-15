package controller;

public class EditorUsuarioConfigs {
    private int CPF;
    private boolean editaNome;
    private boolean editaEmail;
    private boolean editaSenha;
    private String novoNome;
    private String novoEmail;
    private String novaSenha;

    public EditorUsuarioConfigs(int CPF, boolean editaNome, boolean editaEmail, boolean editaSenha) {
        this.CPF = CPF;
        this.editaNome = editaNome;
        this.editaEmail = editaEmail;
        this.editaSenha = editaSenha;
    }

    public int getCPF() {
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

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
