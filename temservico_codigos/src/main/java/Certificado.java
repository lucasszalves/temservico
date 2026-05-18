public class Certificado {
    private int id;
    private String filePath;
    private Usuario usuario;

    public Certificado(int id, String filePath, Usuario usuario) {
        this.id = id;
        this.filePath = filePath;
        this.usuario = usuario;
    }

    public String getFilePath() {
        return filePath;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
