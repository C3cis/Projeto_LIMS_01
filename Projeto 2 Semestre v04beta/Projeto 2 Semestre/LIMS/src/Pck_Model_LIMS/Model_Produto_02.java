package Pck_Model_LIMS;

import java.sql.Date;

public class Model_Produto_02 {

    private int a02_id_produto;
    private String a02_nome_produto;
    private String a02_descricao;
    private String a02_tipo;
    private Date a02_data_cadastro;
    private Date a02_data_chegada;
    private double a02_valor_unitario;
    private int a02_id_projeto;
    private int a02_id_fornecedor;

    public int getA02_id_produto() { return a02_id_produto; }
    public void setA02_id_produto(int a02_id_produto) { this.a02_id_produto = a02_id_produto; }

    public String getA02_nome_produto() { return a02_nome_produto; }
    public void setA02_nome_produto(String a02_nome_produto) { this.a02_nome_produto = a02_nome_produto; }

    public String getA02_descricao() { return a02_descricao; }
    public void setA02_descricao(String a02_descricao) { this.a02_descricao = a02_descricao; }

    public String getA02_tipo() { return a02_tipo; }
    public void setA02_tipo(String a02_tipo) { this.a02_tipo = a02_tipo; }

    public Date getA02_data_cadastro() { return a02_data_cadastro; }
    public void setA02_data_cadastro(Date a02_data_cadastro) { this.a02_data_cadastro = a02_data_cadastro; }

    public Date getA02_data_chegada() { return a02_data_chegada; }
    public void setA02_data_chegada(Date a02_data_chegada) { this.a02_data_chegada = a02_data_chegada; }

    public double getA02_valor_unitario() { return a02_valor_unitario; }
    public void setA02_valor_unitario(double a02_valor_unitario) { this.a02_valor_unitario = a02_valor_unitario; }

    public int getA02_id_projeto() { return a02_id_projeto; }
    public void setA02_id_projeto(int a02_id_projeto) { this.a02_id_projeto = a02_id_projeto; }

    public int getA02_id_fornecedor() { return a02_id_fornecedor; }
    public void setA02_id_fornecedor(int a02_id_fornecedor) { this.a02_id_fornecedor = a02_id_fornecedor; }
}
