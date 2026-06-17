package controller;

import model.TipoServico;

import java.util.ArrayList;

public class ParamsBuscaServico {
    private boolean buscaTipo;
    private boolean buscaCidade;
    private boolean buscaPrecoMin;
    private boolean buscaPrecoMax;
    private boolean buscaNota;

    private TipoServico tipoServico;
    private String cidade;
    private double precoMin;
    private double precoMax;
    private double notaMin;

    public ParamsBuscaServico(boolean buscaTipo, boolean buscaCidade, boolean buscaPrecoMin, boolean buscaPrecoMax, boolean buscaNota, TipoServico tipoServico, String cidade, double precoMin, double precoMax, double notaMin) {
        this.buscaTipo = buscaTipo;
        this.buscaCidade = buscaCidade;
        this.buscaPrecoMin = buscaPrecoMin;
        this.buscaPrecoMax = buscaPrecoMax;
        this.buscaNota = buscaNota;
        this.tipoServico = tipoServico;
        this.cidade = cidade;
        this.precoMin = precoMin;
        this.precoMax = precoMax;
        this.notaMin = notaMin;
    }

    public boolean isBuscaTipo() {
        return buscaTipo;
    }

    public boolean isBuscaCidade() {
        return buscaCidade;
    }

    public boolean isBuscaPrecoMin() {
        return buscaPrecoMin;
    }

    public boolean isBuscaPrecoMax() {
        return buscaPrecoMax;
    }

    public boolean isBuscaNota() {
        return buscaNota;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public String getCidade() {
        return cidade;
    }

    public double getPrecoMin() {
        return precoMin;
    }

    public double getPrecoMax() {
        return precoMax;
    }

    public double getNotaMin() {
        return notaMin;
    }


}
