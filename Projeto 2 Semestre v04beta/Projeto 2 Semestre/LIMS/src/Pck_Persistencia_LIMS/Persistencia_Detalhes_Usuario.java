package Pck_Persistencia_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Detalhes_Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Persistencia_Detalhes_Usuario {

    public Model_Detalhes_Usuario buscarPorId(int idProduto) {

        Model_Detalhes_Usuario m = null;

        String sql = "SELECT * FROM vw_visualizar_dados WHERE ID_PRODUTO = ?";

        try (Connection conn = DAO_Conexao.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                m = new Model_Detalhes_Usuario();

                m.setIdProduto(rs.getInt("ID_PRODUTO"));
                m.setNomeProduto(rs.getString("NOME_PRODUTO"));
                m.setTipoProduto(rs.getString("TIPO_PRODUTO"));
                m.setDataChegada(rs.getDate("DATA_CHEGADA"));
                m.setNomeFornecedor(rs.getString("NOME_FORNECEDOR"));
                m.setIdProjeto(rs.getInt("ID_PROJETO"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }
}
