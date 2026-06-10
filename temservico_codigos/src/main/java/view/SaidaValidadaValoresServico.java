package view;

import java.time.LocalDate;
import java.util.ArrayList;

public class SaidaValidadaValoresServico {
    private boolean valido;
    private ArrayList<String> cidades;

    public ArrayList<LocalDate> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<LocalDate> datas) {
        this.datas = datas;
    }

    public ArrayList<String> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<String> cidades) {
        this.cidades = cidades;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    private ArrayList<LocalDate> datas;

}