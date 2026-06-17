package model;

import controller.EditorServicoConfigs;
import sec.SHA256Hasher;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {
    static int idClasse = 0;
    private int id;
    private String CPF;
    private String email;
    private String nome;
    private String hashSenha;
    private ArrayList<LocalDate> datasIndisponiveis;
//    private ArrayList<Integer> idsServicosPrestados;
//    private ArrayList<Integer> idsServicosContratados;
//    private ArrayList<Integer> idsAgendamentosContratados;
    private ArrayList<Certificado> certificados;
    private ArrayList<Integer> idsServicosFavoritos;

    public Usuario(String CPF, String email, String nome){
        this.id = idClasse++;
        this.CPF = CPF;
        this.email = email;
        this.nome = nome;
        this.datasIndisponiveis = new ArrayList<>();
    }

    public Usuario(String CPF, String email, String nome, String senha) throws NoSuchAlgorithmException {
        this.CPF = CPF;
        this.email = email;
        this.nome = nome;
        this.hashSenha = SHA256Hasher.hashString(senha);
        this.datasIndisponiveis = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public ArrayList<Integer> getIdsServicosPrestados() {
//        return idsServicosPrestados;
//    }

    public ArrayList<LocalDate> getDatasIndisponiveis() {
        return this.datasIndisponiveis;
    }

//    public ArrayList<Integer> getIdsServicosContratados() {
//        return idsServicosContratados;
//    }

    public ArrayList<Integer> getIdsServicosFavoritos() {
        return idsServicosFavoritos;
    }

    public ArrayList<Certificado> getCertificados() {
        return certificados;
    }

//    public ArrayList<Integer> getIdsAgendamentosContratados() {
//        return idsAgendamentosContratados;
//    }

    public void addDatasIndisponiveis(LocalDate data){
        this.datasIndisponiveis.add(data);
    }

    public void setDatasIndisponiveis(ArrayList<LocalDate> datasIndisponiveis) {
        this.datasIndisponiveis = datasIndisponiveis;
    }

//    public void setIdsServicosContratados(ArrayList<Integer> idsServicosContratados) {
//        this.idsServicosContratados = idsServicosContratados;
//    }

    public void setIdsServicosFavoritos(ArrayList<Integer> idsServicosFavoritos) {
        this.idsServicosFavoritos = idsServicosFavoritos;
    }

    public void setCertificados(ArrayList<Certificado> certificados) {
        this.certificados = certificados;
    }

//    public void setIdsServicosPrestados(ArrayList<Integer> idsServicosPrestados) {
//        this.idsServicosPrestados = idsServicosPrestados;
//    }

//    public void setIdsAgendamentosContratados(ArrayList<Integer> idsAgendamentosContratados) {
//        this.idsAgendamentosContratados = idsAgendamentosContratados;
//    }

//    public void addIdServicosPrestados(Servico servico){
//        idsServicosPrestados.add(servico.getId());
//    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }

    public void changeSenha(String novaSenha) throws NoSuchAlgorithmException {
        this.hashSenha = SHA256Hasher.hashString(novaSenha);
    }

    @Override
    public String toString() {
        return this.nome + ", " + this.CPF + ", " + this.email + ", " + this.datasIndisponiveis;
    }

}
