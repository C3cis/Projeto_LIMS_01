package Pck_Controller_LIMS;

public class Controller_Pedido_Produto_09 {
    private int a09_id_pedido;
    private int a09_id_projeto;
    private int a09_quantidade;
    private double a09_preco_unitario;


    public Controller_Pedido_Produto_09() {}


    public Controller_Pedido_Produto_09(int a09_id_pedido, int a09_id_projeto,
                                        int a09_quantidade, double a09_preco_unitario) {
        this.a09_id_pedido = a09_id_pedido;
        this.a09_id_projeto = a09_id_projeto;
        this.a09_quantidade = a09_quantidade;
        this.a09_preco_unitario = a09_preco_unitario;
    }


    // SET
    public void setA09_id_pedido(int a09_id_pedido) { this.a09_id_pedido = a09_id_pedido; }
    public void setA09_id_projeto(int a09_id_projeto) { this.a09_id_projeto = a09_id_projeto; }
    public void setA09_quantidade(int a09_quantidade) { this.a09_quantidade = a09_quantidade; }
    public void setA09_preco_unitario(double a09_preco_unitario) { this.a09_preco_unitario = a09_preco_unitario; }


    // GET
    public int getA09_id_pedido() { return a09_id_pedido; }
    public int getA09_id_projeto() { return a09_id_projeto; }
    public int getA09_quantidade() { return a09_quantidade; }
    public double getA09_preco_unitario() { return a09_preco_unitario; }
}
