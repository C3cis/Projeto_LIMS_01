package Pck_Persistencia_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Estoque_06;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia_Estoque_06 {

    // ================================
    // INSERIR
    // ================================
    public boolean inserirEstoque(Model_Estoque_06 m) {
        String sql = "{ CALL SP_INSERIR_ESTOQUE_06(?, ?, ?, ?) }";

        try (Connection con = DAO_Conexao.connect();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, m.getA06_quantidade());

            if (m.getA06_data_entrada() != null) {
                cs.setDate(2, new java.sql.Date(m.getA06_data_entrada().getTime()));
            } else {
                cs.setNull(2, Types.DATE);
            }

            cs.setInt(3, m.getA06_id_produto());
            cs.setInt(4, m.getA06_id_localizacao());

            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoque(Model_Estoque_06 m) {
        String sql = "{ CALL SP_ATUALIZAR_ESTOQUE_06(?, ?, ?, ?, ?) }";

        try (Connection con = DAO_Conexao.connect();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, m.getA06_id_estoque());
            cs.setInt(2, m.getA06_quantidade());

            if (m.getA06_data_entrada() != null) {
                cs.setDate(3, new java.sql.Date(m.getA06_data_entrada().getTime()));
            } else {
                cs.setNull(3, Types.DATE);
            }

            cs.setInt(4, m.getA06_id_produto());
            cs.setInt(5, m.getA06_id_localizacao());

            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirEstoque(int id) {
        String sql = "{ CALL SP_EXCLUIR_ESTOQUE_06(?) }";

        try (Connection con = DAO_Conexao.connect();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Model_Estoque_06 buscarEstoque(int id) {
        String sql = "{ CALL SP_BUSCAR_ESTOQUE_06(?) }";

        try (Connection con = DAO_Conexao.connect();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Model_Estoque_06 m = new Model_Estoque_06();
                m.setA06_id_estoque(rs.getInt("A06_ID_ESTOQUE"));
                m.setA06_quantidade(rs.getInt("A06_QUANTIDADE"));
                m.setA06_data_entrada(rs.getDate("A06_DATA_ENTRADA"));
                m.setA06_id_produto(rs.getInt("A06_ID_PRODUTO"));
                m.setA06_id_localizacao(rs.getInt("A06_ID_LOCALIZACAO"));

                return m;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Model_Estoque_06> listarEstoques() {
        List<Model_Estoque_06> lista = new ArrayList<>();
        String sql = "{ CALL SP_LISTAR_ESTOQUE_06() }";

        try (Connection con = DAO_Conexao.connect();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Model_Estoque_06 m = new Model_Estoque_06();

                m.setA06_id_estoque(rs.getInt("A06_ID_ESTOQUE"));
                m.setA06_quantidade(rs.getInt("A06_QUANTIDADE"));
                m.setA06_data_entrada(rs.getDate("A06_DATA_ENTRADA"));
                m.setA06_id_produto(rs.getInt("A06_ID_PRODUTO"));
                m.setA06_id_localizacao(rs.getInt("A06_ID_LOCALIZACAO"));

                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
