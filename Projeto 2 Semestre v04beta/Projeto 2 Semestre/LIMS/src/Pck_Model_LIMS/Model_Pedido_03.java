package Pck_Model_LIMS;

import java.sql.Date;

public class Model_Pedido_03 {

    private int a03_id_pedido;
    private Date a03_data_pedido;
    private String a03_status_pedido;
    private String a03_observacoes;
    private int a03_id_usuario;
    private int a03_id_fornecedor;

    // GETTERS e SETTERS (nomes exatamente conforme padrão)
    public int getA03_id_pedido() {
        return a03_id_pedido;
    }

    public void setA03_id_pedido(int a03_id_pedido) {
        this.a03_id_pedido = a03_id_pedido;
    }

    public Date getA03_data_pedido() {
        return a03_data_pedido;
    }

    public void setA03_data_pedido(Date a03_data_pedido) {
        this.a03_data_pedido = a03_data_pedido;
    }

    public String getA03_status_pedido() {
        return a03_status_pedido;
    }

    public void setA03_status_pedido(String a03_status_pedido) {
        this.a03_status_pedido = a03_status_pedido;
    }

    public String getA03_observacoes() {
        return a03_observacoes;
    }

    public void setA03_observacoes(String a03_observacoes) {
        this.a03_observacoes = a03_observacoes;
    }

    public int getA03_id_usuario() {
        return a03_id_usuario;
    }

    public void setA03_id_usuario(int a03_id_usuario) {
        this.a03_id_usuario = a03_id_usuario;
    }

    public int getA03_id_fornecedor() {
        return a03_id_fornecedor;
    }

    public void setA03_id_fornecedor(int a03_id_fornecedor) {
        this.a03_id_fornecedor = a03_id_fornecedor;
    }
}