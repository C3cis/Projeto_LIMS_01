package Pck_DAO_LIMS;

import java.util.Date;


public class DAO_a02_Pedidos {
    private int a02_id_pedido_externo;
    private int a02_id_usuario;
    private Date a02_data_pedido;
    private String a02_status_pedido;
    private String a02_observacoes;


    public DAO_a02_Pedidos() {}


    public DAO_a02_Pedidos(int a02_id_pedido_externo, int a02_id_usuario,
                           Date a02_data_pedido, String a02_status_pedido, String a02_observacoes) {
        this.a02_id_pedido_externo = a02_id_pedido_externo;
        this.a02_id_usuario = a02_id_usuario;
        this.a02_data_pedido = a02_data_pedido;
        this.a02_status_pedido = a02_status_pedido;
        this.a02_observacoes = a02_observacoes;
    }


    // SET
    public void setA02_id_pedido_externo(int a02_id_pedido_externo) { this.a02_id_pedido_externo = a02_id_pedido_externo; }
    public void setA02_id_usuario(int a02_id_usuario) { this.a02_id_usuario = a02_id_usuario; }
    public void setA02_data_pedido(Date a02_data_pedido) { this.a02_data_pedido = a02_data_pedido; }
    public void setA02_status_pedido(String a02_status_pedido) { this.a02_status_pedido = a02_status_pedido; }
    public void setA02_observacoes(String a02_observacoes) { this.a02_observacoes = a02_observacoes; }


    // GET
    public int getA02_id_pedido_externo() { return a02_id_pedido_externo; }
    public int getA02_id_usuario() { return a02_id_usuario; }
    public Date getA02_data_pedido() { return a02_data_pedido; }
    public String getA02_status_pedido() { return a02_status_pedido; }
    public String getA02_observacoes() { return a02_observacoes; }
}

