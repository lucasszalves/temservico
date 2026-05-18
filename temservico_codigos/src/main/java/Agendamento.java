import java.time.LocalDate;

public class Agendamento {
    private int id;
    private Servico servico;
    private Usuario contratante;
    private LocalDate data;

    Agendamento(int id, Servico servico, Usuario contratante, LocalDate data){
        this.id = id;
        this.servico = servico;
        this.contratante = contratante;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Servico getServico() {
        return servico;
    }

    public Usuario getContratante() {
        return contratante;
    }

    public LocalDate getData() {
        return data;
    }
}
