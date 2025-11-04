package Pck_Model_LIMS;

import java.util.Date;


public class Model_a04_Produto {
    private int a04_id_produto;
    private String a04_nome_produto;
    private String a04_descricao;
    private String a04_finalidade;
    private Date a04_data_registro;
    private Date a04_data_recebimento;
    private int a04_id_fornecedor;


    public Model_a04_Produto() {}


    public Model_a04_Produto(int a04_id_produto, String a04_nome_produto, String a04_descricao,
                             String a04_finalidade, Date a04_data_registro, Date a04_data_recebimento,
                             int a04_id_fornecedor) {
        this.a04_id_produto = a04_id_produto;
        this.a04_nome_produto = a04_nome_produto;
        this.a04_descricao = a04_descricao;
        this.a04_finalidade = a04_finalidade;
        this.a04_data_registro = a04_data_registro;
        this.a04_data_recebimento = a04_data_recebimento;
        this.a04_id_fornecedor = a04_id_fornecedor;
    }


    // SET
    public void setA04_id_produto(int a04_id_produto) { this.a04_id_produto = a04_id_produto; }
    public void setA04_nome_produto(String a04_nome_produto) { this.a04_nome_produto = a04_nome_produto; }
    public void setA04_descricao(String a04_descricao) { this.a04_descricao = a04_descricao; }
    public void setA04_finalidade(String a04_finalidade) { this.a04_finalidade = a04_finalidade; }
    public void setA04_data_registro(Date a04_data_registro) { this.a04_data_registro = a04_data_registro; }
    public void setA04_data_recebimento(Date a04_data_recebimento) { this.a04_data_recebimento = a04_data_recebimento; }
    public void setA04_id_fornecedor(int a04_id_fornecedor) { this.a04_id_fornecedor = a04_id_fornecedor; }


    // GET
    public int getA04_id_produto() { return a04_id_produto; }
    public String getA04_nome_produto() { return a04_nome_produto; }
    public String getA04_descricao() { return a04_descricao; }
    public String getA04_finalidade() { return a04_finalidade; }
    public Date getA04_data_registro() { return a04_data_registro; }
    public Date getA04_data_recebimento() { return a04_data_recebimento; }
    public int getA04_id_fornecedor() { return a04_id_fornecedor; }
}
