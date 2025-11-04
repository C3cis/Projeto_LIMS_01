package Pck_Persistencia_LIMS;

public class Persistencia_a07_Localizacao {
    private int a07_id_localizacao;
    private int a07_id_usuario_responsavel;
    private String a07_identificacao;
    private String a07_setor;


    public Persistencia_a07_Localizacao() {}


    public Persistencia_a07_Localizacao(int a07_id_localizacao, int a07_id_usuario_responsavel,
                                        String a07_identificacao, String a07_setor) {
        this.a07_id_localizacao = a07_id_localizacao;
        this.a07_id_usuario_responsavel = a07_id_usuario_responsavel;
        this.a07_identificacao = a07_identificacao;
        this.a07_setor = a07_setor;
    }


    // SET
    public void setA07_id_localizacao(int a07_id_localizacao) { this.a07_id_localizacao = a07_id_localizacao; }
    public void setA07_id_usuario_responsavel(int a07_id_usuario_responsavel) { this.a07_id_usuario_responsavel = a07_id_usuario_responsavel; }
    public void setA07_identificacao(String a07_identificacao) { this.a07_identificacao = a07_identificacao; }
    public void setA07_setor(String a07_setor) { this.a07_setor = a07_setor; }


    // GET
    public int getA07_id_localizacao() { return a07_id_localizacao; }
    public int getA07_id_usuario_responsavel() { return a07_id_usuario_responsavel; }
    public String getA07_identificacao() { return a07_identificacao; }
    public String getA07_setor() { return a07_setor; }
}


