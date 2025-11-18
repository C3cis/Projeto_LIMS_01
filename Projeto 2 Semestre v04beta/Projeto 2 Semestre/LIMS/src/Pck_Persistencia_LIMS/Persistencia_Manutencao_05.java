package Pck_Persistencia_LIMS;

import model.Model_Manutencao_05;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Persistencia_Manutencao_05 {
    
    private Connection connection;

    public Persistencia_Manutencao_05(Connection connection) {
        this.connection = connection;
    }

    // INSERT
    public void inserir(Model_Manutencao_05 m) throws Exception {
        CallableStatement stmt = connection.prepareCall("{CALL SP_INSERIR_MANUTENCAO_05(?,?,?,?,?,?,?,?,?)}");

        stmt.setDate(1, new java.sql.Date(m.getA05_data_manutencao().getTime()));
        stmt.setString(2, m.getA05_tipo_manutencao());
        stmt.setString(3, m.getA05_descricao());
        stmt.setString(4, m.getA05_status_resultado());
        stmt.setString(5, m.getA05_relatorio());
        stmt.setInt(6, m.getA05_id_projeto());
        stmt.setInt(7, m.getA05_id_usuario());
        stmt.setInt(8, m.getA05_id_localizacao());
        stmt.setInt(9, m.getA05_id_produto());

        stmt.execute();
        stmt.close();
    }

    // UPDATE
    public void atualizar(Model_Manutencao_05 m) throws Exception {
        CallableStatement stmt = connection.prepareCall("{CALL SP_ATUALIZAR_MANUTENCAO_05(?,?,?,?,?,?,?,?,?,?)}");

        stmt.setInt(1, m.getA05_id_manutencao());
        stmt.setDate(2, new java.sql.Date(m.getA05_data_manutencao().getTime()));
        stmt.setString(3, m.getA05_tipo_manutencao());
        stmt.setString(4, m.getA05_descricao());
        stmt.setString(5, m.getA05_status_resultado());
        stmt.setString(6, m.getA05_relatorio());
        stmt.setInt(7, m.getA05_id_projeto());
        stmt.setInt(8, m.getA05_id_usuario());
        stmt.setInt(9, m.getA05_id_localizacao());
        stmt.setInt(10, m.getA05_id_produto());

        stmt.execute();
        stmt.close();
    }

    // DELETE
    public void excluir(int id) throws Exception {
        CallableStatement stmt = connection.prepareCall("{CALL SP_EXCLUIR_MANUTENCAO_05(?)}");
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }

    // SEARCH
    public Model_Manutencao_05 buscar(int id) throws Exception {
        CallableStatement stmt = connection.prepareCall("{CALL SP_BUSCAR_MANUTENCAO_05(?)}");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        Model_Manutencao_05 m = null;

        if (rs.next()) {
            m = new Model_Manutencao_05();
            m.setA05_id_manutencao(rs.getInt("A05_ID_MANUTENCAO"));
            m.setA05_data_manutencao(rs.getDate("A05_DATA_MANUTENCAO"));
            m.setA05_tipo_manutencao(rs.getString("A05_TIPO_MANUTENCAO"));
            m.setA05_descricao(rs.getString("A05_DESCRICAO"));
            m.setA05_status_resultado(rs.getString("A05_STATUS_RESULTADO"));
            m.setA05_relatorio(rs.getString("A05_RELATORIO"));
            m.setA05_id_projeto(rs.getInt("A05_ID_PROJETO"));
            m.setA05_id_usuario(rs.getInt("A05_ID_USUARIO"));
            m.setA05_id_localizacao(rs.getInt("A05_ID_LOCALIZACAO"));
            m.setA05_id_produto(rs.getInt("A05_ID_PRODUTO"));
        }

        rs.close();
        stmt.close();
        return m;
    }

    // LIST
    public List<Model_Manutencao_05> listar() throws Exception {
        List<Model_Manutencao_05> lista = new ArrayList<>();

        CallableStatement stmt = connection.prepareCall("{CALL SP_LISTAR_MANUTENCAO_05()}");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Model_Manutencao_05 m = new Model_Manutencao_05();

            m.setA05_id_manutencao(rs.getInt("A05_ID_MANUTENCAO"));
            m.setA05_data_manutencao(rs.getDate("A05_DATA_MANUTENCAO"));
            m.setA05_tipo_manutencao(rs.getString("A05_TIPO_MANUTENCAO"));
            m.setA05_descricao(rs.getString("A05_DESCRICAO"));
            m.setA05_status_resultado(rs.getString("A05_STATUS_RESULTADO"));
            m.setA05_relatorio(rs.getString("A05_RELATORIO"));
            m.setA05_id_projeto(rs.getInt("A05_ID_PROJETO"));
            m.setA05_id_usuario(rs.getInt("A05_ID_USUARIO"));
            m.setA05_id_localizacao(rs.getInt("A05_ID_LOCALIZACAO"));
            m.setA05_id_produto(rs.getInt("A05_ID_PRODUTO"));

            lista.add(m);
        }

        rs.close();
        stmt.close();
        return lista;
    }
}



