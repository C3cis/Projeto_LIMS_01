package Pck_Persistencia_LIMS;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Projeto_Produto_10;

import java.sql.*;
import java.util.ArrayList;

public class Persistencia_Projeto_Produto_10 {

    private Connection conec;
    private CallableStatement preparedState;
    private ResultSet rs;

    public boolean inserir_projeto_produto(Model_Projeto_Produto_10 m) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_INSERIR_PROJETO_PRODUTO_10(?,?,?)}");

            preparedState.setInt(1, m.getA10_id_produto());
            preparedState.setInt(2, m.getA10_id_projeto());
            preparedState.setInt(3, m.getA10_quant_proj_produto());

            preparedState.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            fechar();
        }
    }

    public boolean excluir_projeto_produto(int idProduto, int idProjeto) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_EXCLUIR_PROJETO_PRODUTO_10(?,?)}");

            preparedState.setInt(1, idProduto);
            preparedState.setInt(2, idProjeto);

            preparedState.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            fechar();
        }
    }

    public ArrayList<Model_Projeto_Produto_10> listar_por_projeto(int idProjeto) {
        ArrayList<Model_Projeto_Produto_10> lista = new ArrayList<>();

        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_LISTAR_PROJETO_PRODUTO_10(?)}");

            preparedState.setInt(1, idProjeto);
            rs = preparedState.executeQuery();

            while (rs.next()) {
                Model_Projeto_Produto_10 m = new Model_Projeto_Produto_10();

                m.setA10_id_produto(rs.getInt("A10_ID_PRODUTO"));
                m.setA10_id_projeto(rs.getInt("A10_ID_PROJETO"));
                m.setA10_quant_proj_produto(rs.getInt("A10_QUANT_PROJ_PRODUTO"));

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

