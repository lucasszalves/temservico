package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Servico {
    private int id;
    static int idClasse = 0;
    private ArrayList<String> cidades;
    private ArrayList<LocalDate> datasIndisponiveis;
    private ArrayList<String> imagensPaths;
    private ArrayList<Avaliacao> avaliacoes;
    private int numAgendamentos;
    private double notaMedia;
    private double somaNotas;
    private double preco;
    private TipoServico tipo;
    private String CPFprestador;

    public Servico(TipoServico tipo, double preco, ArrayList<LocalDate> datasIndisponiveis, ArrayList<String> cidades, Usuario prestador){
        this.id = idClasse++;
        this.tipo = tipo;
        this.preco = preco;
        this.datasIndisponiveis = datasIndisponiveis;
        this.cidades = cidades;
        this.CPFprestador = prestador.getCPF();
        this.notaMedia = 6;
        this.somaNotas = 0;
        this.numAgendamentos = 0;
        this.avaliacoes = new ArrayList<>();
    }

    public String getPrestador() {
        return CPFprestador;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public double getPreco() {
        return preco;
    }

    public double getNotaMedia() {
        return notaMedia;
    }


    public int getNumAgendamentos() {
        return numAgendamentos;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public ArrayList<String> getImagensPaths() {
        return imagensPaths;
    }

    public ArrayList<LocalDate> getDatasIndisponiveis() {
        return datasIndisponiveis;
    }

    public ArrayList<String> getCidades() {
        return cidades;
    }

    public int getId() {
        return id;
    }

    public void setCidades(ArrayList<String> cidades) {
        this.cidades = cidades;
    }

    public void setDatasIndisponiveis(ArrayList<LocalDate> datasIndisponiveis) {
        this.datasIndisponiveis = datasIndisponiveis;
    }

    public void addAgendamento(){
        this.numAgendamentos++;
    }

    public void rmAgendamento(){
        this.numAgendamentos--;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public void setImagensPaths(ArrayList<String> imagensPaths) {
        this.imagensPaths = imagensPaths;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void addAvaliacao(Avaliacao avaliacao){
        this.avaliacoes.add(avaliacao);
        this.somaNotas += avaliacao.getNota();
        this.notaMedia = this.somaNotas / (this.avaliacoes.size());
    }

    public String getCPFprestador() {
        return CPFprestador;
    }

    public void setCPFprestador(String CPFprestador) {
        this.CPFprestador = CPFprestador;
    }
    @Override
    public String toString(){
        return this.id + " " + this.preco + " " + this.cidades + " " + this.datasIndisponiveis + " " + this.tipo + " " + this.CPFprestador;
    }
}
