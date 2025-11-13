package Pck_Model_LIMS;

import java.util.Date;


public class Model_Estoque_03 {
    private int a03_id_estoque;
    private int a03_id_produto;
    private int a03_id_localizacao;
    private int a03_quantidade;
    private Date a03_data_chegada;
    private String a03_lote;


    public Model_Estoque_03() {}


    public Model_Estoque_03(int a03_id_estoque, int a03_id_produto, int a03_id_localizacao,
                             int a03_quantidade, Date a03_data_chegada, String a03_lote) {
        this.a03_id_estoque = a03_id_estoque;
        this.a03_id_produto = a03_id_produto;
        this.a03_id_localizacao = a03_id_localizacao;
        this.a03_quantidade = a03_quantidade;
        this.a03_data_chegada = a03_data_chegada;
        this.a03_lote = a03_lote;
    }


    // SET
    public void setA03_id_estoque(int a03_id_estoque) { this.a03_id_estoque = a03_id_estoque; }
    public void setA03_id_produto(int a03_id_produto) { this.a03_id_produto = a03_id_produto; }
    public void setA03_id_localizacao(int a03_id_localizacao) { this.a03_id_localizacao = a03_id_localizacao; }
    public void setA03_quantidade(int a03_quantidade) { this.a03_quantidade = a03_quantidade; }
    public void setA03_data_chegada(Date a03_data_chegada) { this.a03_data_chegada = a03_data_chegada; }
    public void setA03_lote(String a03_lote) { this.a03_lote = a03_lote; }


    // GET
    public int getA03_id_estoque() { return a03_id_estoque; }
    public int getA03_id_produto() { return a03_id_produto; }
    public int getA03_id_localizacao() { return a03_id_localizacao; }
    public int getA03_quantidade() { return a03_quantidade; }
    public Date getA03_data_chegada() { return a03_data_chegada; }
    public String getA03_lote() { return a03_lote; }
}
