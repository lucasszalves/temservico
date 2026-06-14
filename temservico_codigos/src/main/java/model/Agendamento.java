package model;

import java.time.LocalDate;

public class Agendamento {
    private int id;
    static int idClasse = 0;
    private int IDservico;
    private String CPFcontratante;
    private LocalDate data;

    public Agendamento(Servico servico, Usuario contratante, LocalDate data){
        this.id = idClasse++;
        this.IDservico = servico.getId();
        this.CPFcontratante = contratante.getCPF();
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getServico() {
        return IDservico;
    }

    public String getContratante() {
        return CPFcontratante;
    }

    public LocalDate getData() {
        return data;
    }
}
