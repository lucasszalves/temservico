import java.time.LocalDate;
import java.util.ArrayList;

public class EditorServicoConfigs {
    private int id;
    private boolean editaCidades;
    private boolean editaDatasIndisp;
    private boolean editaPreco;
    private boolean editaTipo;
    private ArrayList<String> novasCidades;
    private ArrayList<LocalDate> novasDatasIndisp;
    private double novoPreco;
    private TipoServico novoTipo;

    public EditorServicoConfigs(int id, boolean editaCidades, boolean editaDatasIndisp, boolean editaPreco, boolean editaTipo) {
        this.id = id;
        this.editaCidades = editaCidades;
        this.editaDatasIndisp = editaDatasIndisp;
        this.editaPreco = editaPreco;
        this.editaTipo = editaTipo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEditaCidades() {
        return editaCidades;
    }

    public void setEditaCidades(boolean editaCidades) {
        this.editaCidades = editaCidades;
    }

    public boolean isEditaDatasIndisp() {
        return editaDatasIndisp;
    }

    public void setEditaDatasIndisp(boolean editaDatasIndisp) {
        this.editaDatasIndisp = editaDatasIndisp;
    }

    public boolean isEditaPreco() {
        return editaPreco;
    }

    public void setEditaPreco(boolean editaPreco) {
        this.editaPreco = editaPreco;
    }

    public boolean isEditaTipo() {
        return editaTipo;
    }

    public void setEditaTipo(boolean editaTipo) {
        this.editaTipo = editaTipo;
    }

    public ArrayList<String> getNovasCidades() {
        return novasCidades;
    }

    public void setNovasCidades(ArrayList<String> novasCidades) {
        this.novasCidades = novasCidades;
    }

    public ArrayList<LocalDate> getNovasDatasIndisp() {
        return novasDatasIndisp;
    }

    public void setNovasDatasIndisp(ArrayList<LocalDate> novasDatasIndisp) {
        this.novasDatasIndisp = novasDatasIndisp;
    }

    public double getNovoPreco() {
        return novoPreco;
    }

    public void setNovoPreco(double novoPreco) {
        this.novoPreco = novoPreco;
    }

    public TipoServico getNovoTipo() {
        return novoTipo;
    }

    public void setNovoTipo(TipoServico novoTipo) {
        this.novoTipo = novoTipo;
    }
}
