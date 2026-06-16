package controller;

import model.TipoServico;

import java.util.ArrayList;

public class ParamsBuscaServico {
    private boolean buscaTipo;
    private boolean buscaCidades;
    private boolean buscaPrecoMin;
    private boolean buscaPrecoMax;
    private boolean buscaNota;

    private TipoServico tipoServico;
    private ArrayList<String> cidades;
    private double precoMin;
    private double precoMax;
    private double notaMin;

    public ParamsBuscaServico(boolean buscaTipo, boolean buscaCidades, boolean buscaPrecoMin, boolean buscaPrecoMax, boolean buscaNota) {
        this.buscaTipo = buscaTipo;
        this.buscaCidades = buscaCidades;
        this.buscaPrecoMin = buscaPrecoMin;
        this.buscaPrecoMax = buscaPrecoMax;
        this.buscaNota = buscaNota;
    }

    public boolean isBuscaTipo() {
        return buscaTipo;
    }

    public boolean isBuscaCidades() {
        return buscaCidades;
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

    public ArrayList<String> getCidades() {
        return cidades;
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
