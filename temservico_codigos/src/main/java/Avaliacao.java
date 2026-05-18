import java.time.LocalDate;

public class Avaliacao {
    private int id;
    private Servico servico;
    private LocalDate data;
    private String descricao;
    private int nota;

    Avaliacao(int id, Servico servico, LocalDate data, String descricao, int nota){
        this.id = id;
        this.servico = servico;
        this.data = data;
        this.descricao = descricao;
        this.nota = nota;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNota() {
        return nota;
    }
}
