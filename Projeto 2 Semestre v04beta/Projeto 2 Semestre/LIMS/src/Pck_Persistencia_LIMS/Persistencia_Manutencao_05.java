package Pck_Persistencia_LIMS;


import Pck_Model_LIMS.Model_Manutencao_05;
import Pck_DAO_LIMS.DAO_Conexao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;


public class Persistencia_Manutencao_05 {
    private Connection conec;
    private CallableStatement callable;
    private ResultSet rs;

    public Persistencia_Manutencao_05() {
        // abrir/fechar em cada método
    }

    // INSERIR
    public boolean inserir_manutencao(Model_Manutencao_05 m) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_INSERIR_MANUTENCAO_05(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            // ordem: V_DATA_MANUTENCAO, V_TIPO_MANUTENCAO, V_DESCRICAO,
            // V_STATUS_RESULTADO, V_RELATORIO, V_ID_PROJETO, V_ID_USUARIO, V_ID_LOCALIZACAO, V_ID_PRODUTO

            // data (aceita null)
            Date d = m.getA05_data_manutencao();
            if (d != null) callable.setDate(1, d);
            else callable.setNull(1, java.sql.Types.DATE);

            callable.setString(2, m.getA05_tipo_manutencao());
            callable.setString(3, m.getA05_descricao());
            callable.setString(4, m.getA05_status_resultado());
            callable.setString(5, m.getA05_relatorio());
            callable.setInt(6, m.getA05_id_projeto());
            callable.setInt(7, m.getA05_id_usuario());
            callable.setInt(8, m.getA05_id_localizacao());
            callable.setInt(9, m.getA05_id_produto());

            callable.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // ATUALIZAR
    public boolean atualizar_manutencao(Model_Manutencao_05 m) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_ATUALIZAR_MANUTENCAO_05(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            // ordem: V_ID_MANUTENCAO, V_DATA_MANUTENCAO, V_TIPO_MANUTENCAO, V_DESCRICAO,
            // V_STATUS_RESULTADO, V_RELATORIO, V_ID_PROJETO, V_ID_USUARIO, V_ID_LOCALIZACAO, V_ID_PRODUTO

            callable.setInt(1, m.getA05_id_manutencao());

            Date d = m.getA05_data_manutencao();
            if (d != null) callable.setDate(2, d);
            else callable.setNull(2, java.sql.Types.DATE);

            callable.setString(3, m.getA05_tipo_manutencao());
            callable.setString(4, m.getA05_descricao());
            callable.setString(5, m.getA05_status_resultado());
            callable.setString(6, m.getA05_relatorio());
            callable.setInt(7, m.getA05_id_projeto());
            callable.setInt(8, m.getA05_id_usuario());
            callable.setInt(9, m.getA05_id_localizacao());
            callable.setInt(10, m.getA05_id_produto());

            callable.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // DELETAR
    public boolean deletar_manutencao(int id) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_EXCLUIR_MANUTENCAO_05(?) }");
            callable.setInt(1, id);
            callable.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // BUSCAR por ID -> retorna Model preenchido
    public Model_Manutencao_05 buscar_manutencao(int id) {
        Model_Manutencao_05 m = null;
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_BUSCAR_MANUTENCAO_05(?) }");
            callable.setInt(1, id);
            rs = callable.executeQuery();

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
        return m;
    }

    // LISTAR -> retorna lista
    public ArrayList<Model_Manutencao_05> listar_manutencao() {
        ArrayList<Model_Manutencao_05> lista = new ArrayList<>();
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_LISTAR_MANUTENCAO_05() }");
            rs = callable.executeQuery();

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
        return lista;
    }
}



