package Pck_Controller_LIMS;

import java.util.Date;


public class Controller_Projeto_Produto_10 {
    private int a08_id_produto;
    private int a08_id_projeto;
    private int a08_quantidade_usada;
    private Date a08_data_vinculo;


    public Controller_Projeto_Produto_10() {}


    public Controller_Projeto_Produto_10(int a08_id_produto, int a08_id_projeto,
                                         int a08_quantidade_usada, Date a08_data_vinculo) {
        this.a08_id_produto = a08_id_produto;
        this.a08_id_projeto = a08_id_projeto;
        this.a08_quantidade_usada = a08_quantidade_usada;
        this.a08_data_vinculo = a08_data_vinculo;
    }


    // SET
    public void setA08_id_produto(int a08_id_produto) { this.a08_id_produto = a08_id_produto; }
    public void setA08_id_projeto(int a08_id_projeto) { this.a08_id_projeto = a08_id_projeto; }
    public void setA08_quantidade_usada(int a08_quantidade_usada) { this.a08_quantidade_usada = a08_quantidade_usada; }
    public void setA08_data_vinculo(Date a08_data_vinculo) { this.a08_data_vinculo = a08_data_vinculo; }


    // GET
    public int getA08_id_produto() { return a08_id_produto; }
    public int getA08_id_projeto() { return a08_id_projeto; }
    public int getA08_quantidade_usada() { return a08_quantidade_usada; }
    public Date getA08_data_vinculo() { return a08_data_vinculo; }
}

