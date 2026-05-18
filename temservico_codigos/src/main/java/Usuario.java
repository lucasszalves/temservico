import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {

    private String CPF;
    private String email;
    private String nome;
    private ArrayList<LocalDate> datasIndisponiveis;
    private ArrayList<Integer> idsServicosContratados;
    private ArrayList<Integer> idsServicosFavoritos;
    private ArrayList<Certificado> certificados;
    private ArrayList<Servico> servicosPrestados;
    private ArrayList<Agendamento> agendamentos;

    Usuario(String CPF, String email, String nome){
        this.CPF = CPF;
        this.email = email;
        this.nome = nome;
    }


    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Servico> getServicosPrestados() {
        return servicosPrestados;
    }

    public ArrayList<LocalDate> getDatasIndisponiveis() {
        return datasIndisponiveis;
    }

    public ArrayList<Integer> getIdsServicosContratados() {
        return idsServicosContratados;
    }

    public ArrayList<Integer> getIdsServicosFavoritos() {
        return idsServicosFavoritos;
    }

    public ArrayList<Certificado> getCertificados() {
        return certificados;
    }

    public ArrayList<Agendamento> getAgendamentos() {
        return agendamentos;
    }
}
