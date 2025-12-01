package Pck_Persistencia_LIMS;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Visualizar_Dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Persistencia_Visualizar_Dados {

    public List<Model_Visualizar_Dados> listarTudo() {

        List<Model_Visualizar_Dados> lista = new ArrayList<>();

        String sql = "SELECT * FROM vw_visualizar_dados";

        try (Connection conn = DAO_Conexao.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Model_Visualizar_Dados m = new Model_Visualizar_Dados();

                m.setIdProduto(rs.getInt("ID_PRODUTO"));
                m.setNomeProduto(rs.getString("NOME_PRODUTO"));
                m.setTipoProduto(rs.getString("TIPO_PRODUTO"));
                m.setDataChegada(rs.getDate("DATA_CHEGADA"));
                m.setNomeFornecedor(rs.getString("NOME_FORNECEDOR"));
                m.setIdProjeto(rs.getInt("ID_PROJETO"));

                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}