package Pck_Model_LIMS;

import java.util.Date;

public class Model_Estoque_06 {

    private int a06_id_estoque;
    private int a06_quantidade;
    private Date a06_data_entrada;
    private int a06_id_produto;
    private int a06_id_localizacao;

    // GETTERS E SETTERS

    public int getA06_id_estoque() {
        return a06_id_estoque;
    }

    public void setA06_id_estoque(int a06_id_estoque) {
        this.a06_id_estoque = a06_id_estoque;
    }

    public int getA06_quantidade() {
        return a06_quantidade;
    }

    public void setA06_quantidade(int a06_quantidade) {
        this.a06_quantidade = a06_quantidade;
    }

    public Date getA06_data_entrada() {
        return a06_data_entrada;
    }

    public void setA06_data_entrada(Date a06_data_entrada) {
        this.a06_data_entrada = a06_data_entrada;
    }

    public int getA06_id_produto() {
        return a06_id_produto;
    }

    public void setA06_id_produto(int a06_id_produto) {
        this.a06_id_produto = a06_id_produto;
    }

    public int getA06_id_localizacao() {
        return a06_id_localizacao;
    }

    public void setA06_id_localizacao(int a06_id_localizacao) {
        this.a06_id_localizacao = a06_id_localizacao;
    }
}