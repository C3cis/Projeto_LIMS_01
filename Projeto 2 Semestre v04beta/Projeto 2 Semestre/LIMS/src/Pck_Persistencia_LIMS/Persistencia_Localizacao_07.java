package Pck_Persistencia_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Localizacao_07;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia_Localizacao_07 {

    public boolean inserirLocalizacao(Model_Localizacao_07 m) {
        try (Connection conn = DAO_Conexao.connect();
             CallableStatement cs = conn.prepareCall("{CALL SP_INSERIR_LOCALIZACAO_07(?, ?, ?)}")) {

            cs.setString(1, m.getA07_identificacao());
            cs.setString(2, m.getA07_setor());
            cs.setInt(3, m.getA07_id_usuario());

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean atualizarLocalizacao(Model_Localizacao_07 m) {
        try (Connection conn = DAO_Conexao.connect();
             CallableStatement cs = conn.prepareCall("{CALL SP_ATUALIZAR_LOCALIZACAO_07(?, ?, ?, ?)}")) {

            cs.setInt(1, m.getA07_id_localizacao());
            cs.setString(2, m.getA07_identificacao());
            cs.setString(3, m.getA07_setor());
            cs.setInt(4, m.getA07_id_usuario());

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean excluirLocalizacao(int id) {
        try (Connection conn = DAO_Conexao.connect();
             CallableStatement cs = conn.prepareCall("{CALL SP_EXCLUIR_LOCALIZACAO_07(?)}")) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Model_Localizacao_07 buscarLocalizacao(int id) {
        try (Connection conn = DAO_Conexao.connect();
             CallableStatement cs = conn.prepareCall("{CALL SP_BUSCAR_LOCALIZACAO_07(?)}")) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Model_Localizacao_07 m = new Model_Localizacao_07();
                m.setA07_id_localizacao(rs.getInt("A07_ID_LOCALIZACAO"));
                m.setA07_identificacao(rs.getString("A07_IDENTIFICACAO"));
                m.setA07_setor(rs.getString("A07_SETOR"));
                m.setA07_id_usuario(rs.getInt("A07_ID_USUARIO"));
                return m;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Model_Localizacao_07> listarLocalizacoes() {
        List<Model_Localizacao_07> lista = new ArrayList<>();

        try (Connection conn = DAO_Conexao.connect();
             CallableStatement cs = conn.prepareCall("{CALL SP_LISTAR_LOCALIZACAO_07()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Model_Localizacao_07 m = new Model_Localizacao_07();
                m.setA07_id_localizacao(rs.getInt("A07_ID_LOCALIZACAO"));
                m.setA07_identificacao(rs.getString("A07_IDENTIFICACAO"));
                m.setA07_setor(rs.getString("A07_SETOR"));
                m.setA07_id_usuario(rs.getInt("A07_ID_USUARIO"));
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}


