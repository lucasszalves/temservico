package model;

import java.time.LocalDate;

public class Avaliacao {
    static int idClasse = 0;
    private int id;
    private Servico servico;
    private LocalDate data;
    private String descricao;
    private int nota;

    public Avaliacao(Servico servico, LocalDate data, String descricao, int nota){
        this.id = idClasse++;
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
