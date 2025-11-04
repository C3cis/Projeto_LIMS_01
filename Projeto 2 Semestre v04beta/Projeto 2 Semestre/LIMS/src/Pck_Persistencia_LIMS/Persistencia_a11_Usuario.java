package Pck_Persistencia_LIMS;

public class Persistencia_a11_Usuario {
    private int a11_id_usuario;
    private String a11_nome;
    private String a11_email;
    private String a11_titulo;
    private String a11_senha;
    private String a11_status_usuario;


    public Persistencia_a11_Usuario() {}


    public Persistencia_a11_Usuario(int a11_id_usuario, String a11_nome, String a11_email,
                                    String a11_titulo, String a11_senha, String a11_status_usuario) {
        this.a11_id_usuario = a11_id_usuario;
        this.a11_nome = a11_nome;
        this.a11_email = a11_email;
        this.a11_titulo = a11_titulo;
        this.a11_senha = a11_senha;
        this.a11_status_usuario = a11_status_usuario;
    }


    // SET
    public void setA11_id_usuario(int a11_id_usuario) { this.a11_id_usuario = a11_id_usuario; }
    public void setA11_nome(String a11_nome) { this.a11_nome = a11_nome; }
    public void setA11_email(String a11_email) { this.a11_email = a11_email; }
    public void setA11_titulo(String a11_titulo) { this.a11_titulo = a11_titulo; }
    public void setA11_senha(String a11_senha) { this.a11_senha = a11_senha; }
    public void setA11_status_usuario(String a11_status_usuario) { this.a11_status_usuario = a11_status_usuario; }


    // GET
    public int getA11_id_usuario() { return a11_id_usuario; }
    public String getA11_nome() { return a11_nome; }
    public String getA11_email() { return a11_email; }
    public String getA11_titulo() { return a11_titulo; }
    public String getA11_senha() { return a11_senha; }
    public String getA11_status_usuario() { return a11_status_usuario; }
}

