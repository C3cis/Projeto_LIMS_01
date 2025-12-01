package Pck_Model_LIMS;
import java.sql.Date;

public class Model_Produto_02 {

    private int A02_id_produto;
    private String A02_nome_produto;
    private String A02_descricao;
    private String A02_tipo;
    private Date A02_data_cadastro;
    private Date A02_data_chegada;
    private double A02_valor_unitario;
    private int A02_id_projeto;
    private int A02_id_fornecedor;

    // ------------------------------------------------------------
    // GETTERS & SETTERS
    // ------------------------------------------------------------

    public int getA02_id_produto() {
        return A02_id_produto;
    }

    public void setA02_id_produto(int A02_id_produto) {
        this.A02_id_produto = A02_id_produto;
    }

    public String getA02_nome_produto() {
        return A02_nome_produto;
    }

    public void setA02_nome_produto(String A02_nome_produto) {
        this.A02_nome_produto = A02_nome_produto;
    }

    public String getA02_descricao() {
        return A02_descricao;
    }

    public void setA02_descricao(String A02_descricao) {
        this.A02_descricao = A02_descricao;
    }

    public String getA02_tipo() {
        return A02_tipo;
    }

    public void setA02_tipo(String A02_tipo) {
        this.A02_tipo = A02_tipo;
    }

    public Date getA02_data_cadastro() {
        return A02_data_cadastro;
    }

    public void setA02_data_cadastro(Date A02_data_cadastro) {
        this.A02_data_cadastro = A02_data_cadastro;
    }

    public Date getA02_data_chegada() {
        return A02_data_chegada;
    }

    public void setA02_data_chegada(Date A02_data_chegada) {
        this.A02_data_chegada = A02_data_chegada;
    }

    public double getA02_valor_unitario() {
        return A02_valor_unitario;
    }

    public void setA02_valor_unitario(double A02_valor_unitario) {
        this.A02_valor_unitario = A02_valor_unitario;
    }

    public int getA02_id_projeto() {
        return A02_id_projeto;
    }

    public void setA02_id_projeto(int A02_id_projeto) {
        this.A02_id_projeto = A02_id_projeto;
    }

    public int getA02_id_fornecedor() {
        return A02_id_fornecedor;
    }

    public void setA02_id_fornecedor(int A02_id_fornecedor) {
        this.A02_id_fornecedor = A02_id_fornecedor;
    }
}