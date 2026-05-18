import java.time.LocalDate;
import java.util.ArrayList;

enum TipoServico{
    LIMPEZA,
    ENCANAMENTO,
    ELETRICA,
    CUIDADOGERIATRICO,
    CONSTRUCAO,
    MARCENARIA
}

public class Servico {
    private int id;
    private ArrayList<String> cidades;
    private ArrayList<LocalDate> datasIndisponiveis;
    private ArrayList<String> imagensPaths;
    private ArrayList<Avaliacao> avaliacoes;
    private ArrayList<Agendamento> agendamentos;
    private double notaMedia;
    private double preco;
    private TipoServico tipo;
    private Usuario prestador;

    Servico(int id, TipoServico tipo, double preco, ArrayList<LocalDate> datasIndisponiveis, ArrayList<String> cidades, Usuario prestador){
        this.id = id;
        this.tipo = tipo;
        this.preco = preco;
        this.datasIndisponiveis = datasIndisponiveis;
        this.cidades = cidades;
        this.prestador = prestador;
        this.notaMedia = 0;
    }

    public Usuario getPrestador() {
        return prestador;
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

    public ArrayList<Agendamento> getAgendamentos() {
        return agendamentos;
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

    public void setAgendamentos(ArrayList<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
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
}
