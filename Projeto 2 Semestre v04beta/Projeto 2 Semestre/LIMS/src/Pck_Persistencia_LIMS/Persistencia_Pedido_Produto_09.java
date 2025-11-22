package Pck_Persistencia_LIMS;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Pedido_Produto_09;

import java.sql.*;
import java.util.ArrayList;

public class Persistencia_Pedido_Produto_09 {

    private Connection conec;
    private CallableStatement preparedState;
    private ResultSet rs;

    public boolean inserir_pedido_produto(Model_Pedido_Produto_09 m) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_INSERIR_PEDIDO_PRODUTO_09(?,?,?,?)}");

            preparedState.setInt(1, m.getA09_id_pedido());
            preparedState.setInt(2, m.getA09_id_produto());
            preparedState.setInt(3, m.getA09_quantidade());
            preparedState.setDouble(4, m.getA09_valor_total());

            preparedState.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fechar();
        }
    }

    public boolean excluir_pedido_produto(int idPedido, int idProduto) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_EXCLUIR_PEDIDO_PRODUTO_09(?,?)}");
            preparedState.setInt(1, idPedido);
            preparedState.setInt(2, idProduto);
            preparedState.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            fechar();
        }
    }

    public ArrayList<Model_Pedido_Produto_09> listar_por_pedido(int idPedido) {
        ArrayList<Model_Pedido_Produto_09> lista = new ArrayList<>();

        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_LISTAR_PEDIDO_PRODUTO_09(?)}");
            preparedState.setInt(1, idPedido);

            rs = preparedState.executeQuery();

            while (rs.next()) {
                Model_Pedido_Produto_09 m = new Model_Pedido_Produto_09();

                m.setA09_id_pedido(rs.getInt("A09_ID_PEDIDO"));
                m.setA09_id_produto(rs.getInt("A09_ID_PRODUTO"));
                m.setA09_quantidade(rs.getInt("A09_QUANTIDADE"));
                m.setA09_valor_total(rs.getDouble("A09_VALOR_TOTAL"));

                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fechar();
        }

        return lista;
    }

    private void fechar() {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
        try { if (conec != null) conec.close(); } catch (Exception ignored) {}
    }
}