package Pck_Persistencia_LIMS;

import java.util.Date;


public class Persistencia_a06_Relatorio {
    private int a06_id_relatorio;
    private int a06_id_projeto;
    private int a06_id_produto;
    private int a06_id_usuario;
    private String a06_tipo_relatorio;
    private Date a06_data_geracao;
    private String a06_texto_relatorio;


    public Persistencia_a06_Relatorio() {}


    public Persistencia_a06_Relatorio(int a06_id_relatorio, int a06_id_projeto, int a06_id_produto,
                                      int a06_id_usuario, String a06_tipo_relatorio, Date a06_data_geracao,
                                      String a06_texto_relatorio) {
        this.a06_id_relatorio = a06_id_relatorio;
        this.a06_id_projeto = a06_id_projeto;
        this.a06_id_produto = a06_id_produto;
        this.a06_id_usuario = a06_id_usuario;
        this.a06_tipo_relatorio = a06_tipo_relatorio;
        this.a06_data_geracao = a06_data_geracao;
        this.a06_texto_relatorio = a06_texto_relatorio;
    }


    // SET
    public void setA06_id_relatorio(int a06_id_relatorio) { this.a06_id_relatorio = a06_id_relatorio; }
    public void setA06_id_projeto(int a06_id_projeto) { this.a06_id_projeto = a06_id_projeto; }
    public void setA06_id_produto(int a06_id_produto) { this.a06_id_produto = a06_id_produto; }
    public void setA06_id_usuario(int a06_id_usuario) { this.a06_id_usuario = a06_id_usuario; }
    public void setA06_tipo_relatorio(String a06_tipo_relatorio) { this.a06_tipo_relatorio = a06_tipo_relatorio; }
    public void setA06_data_geracao(Date a06_data_geracao) { this.a06_data_geracao = a06_data_geracao; }
    public void setA06_texto_relatorio(String a06_texto_relatorio) { this.a06_texto_relatorio = a06_texto_relatorio; }


    // GET
    public int getA06_id_relatorio() { return a06_id_relatorio; }
    public int getA06_id_projeto() { return a06_id_projeto; }
    public int getA06_id_produto() { return a06_id_produto; }
    public int getA06_id_usuario() { return a06_id_usuario; }
    public String getA06_tipo_relatorio() { return a06_tipo_relatorio; }
    public Date getA06_data_geracao() { return a06_data_geracao; }
    public String getA06_texto_relatorio() { return a06_texto_relatorio; }
}


