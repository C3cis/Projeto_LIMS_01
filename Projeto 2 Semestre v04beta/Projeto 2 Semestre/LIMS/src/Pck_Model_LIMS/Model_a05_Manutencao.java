package Pck_Model_LIMS;

import java.util.Date;


public class Model_a05_Manutencao {
    private int a05_id_manutencao;
    private int a05_id_produto;
    private int a05_id_localizacao;
    private int a05_id_usuario;
    private Date a05_data_manutencao;
    private String a05_tipo_manutencao;
    private String a05_descricao;
    private String a05_menu_relatorio;


    public Model_a05_Manutencao() {}


    public Model_a05_Manutencao(int a05_id_manutencao, int a05_id_produto, int a05_id_localizacao,
                                int a05_id_usuario, Date a05_data_manutencao, String a05_tipo_manutencao,
                                String a05_descricao, String a05_menu_relatorio) {
        this.a05_id_manutencao = a05_id_manutencao;
        this.a05_id_produto = a05_id_produto;
        this.a05_id_localizacao = a05_id_localizacao;
        this.a05_id_usuario = a05_id_usuario;
        this.a05_data_manutencao = a05_data_manutencao;
        this.a05_tipo_manutencao = a05_tipo_manutencao;
        this.a05_descricao = a05_descricao;
        this.a05_menu_relatorio = a05_menu_relatorio;
    }


    // SET
    public void setA05_id_manutencao(int a05_id_manutencao) { this.a05_id_manutencao = a05_id_manutencao; }
    public void setA05_id_produto(int a05_id_produto) { this.a05_id_produto = a05_id_produto; }
    public void setA05_id_localizacao(int a05_id_localizacao) { this.a05_id_localizacao = a05_id_localizacao; }
    public void setA05_id_usuario(int a05_id_usuario) { this.a05_id_usuario = a05_id_usuario; }
    public void setA05_data_manutencao(Date a05_data_manutencao) { this.a05_data_manutencao = a05_data_manutencao; }
    public void setA05_tipo_manutencao(String a05_tipo_manutencao) { this.a05_tipo_manutencao = a05_tipo_manutencao; }
    public void setA05_descricao(String a05_descricao) { this.a05_descricao = a05_descricao; }
    public void setA05_menu_relatorio(String a05_menu_relatorio) { this.a05_menu_relatorio = a05_menu_relatorio; }


    // GET
    public int getA05_id_manutencao() { return a05_id_manutencao; }
    public int getA05_id_produto() { return a05_id_produto; }
    public int getA05_id_localizacao() { return a05_id_localizacao; }
    public int getA05_id_usuario() { return a05_id_usuario; }
    public Date getA05_data_manutencao() { return a05_data_manutencao; }
    public String getA05_tipo_manutencao() { return a05_tipo_manutencao; }
    public String getA05_descricao() { return a05_descricao; }
    public String getA05_menu_relatorio() { return a05_menu_relatorio; }
}


