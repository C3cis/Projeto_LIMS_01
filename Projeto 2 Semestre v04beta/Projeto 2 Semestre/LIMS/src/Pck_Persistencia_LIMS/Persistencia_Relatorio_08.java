package Pck_Persistencia_LIMS;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Relatorio_08;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Persistencia_Relatorio_08 {

    private Connection conn;

    public Persistencia_Relatorio_08() {
        try {
            conn = DAO_Conexao.connect(); // <- CORRETO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------
    // INSERT
    // ------------------------------------------------------
    public boolean inserirRelatorio(Model_Relatorio_08 m) {
        try {
            CallableStatement cs = conn.prepareCall("{CALL SP_INSERIR_RELATORIO_08(?,?,?,?,?)}");
            cs.setString(1, m.getA08_titulo());
            cs.setString(2, m.getA08_data_geracao());
            cs.setString(3, m.getA08_conteudo());
            cs.setInt(4, m.getA08_id_usuario());
            cs.setInt(5, m.getA08_id_projeto());
            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------
    // UPDATE
    // ------------------------------------------------------
    public boolean atualizarRelatorio(Model_Relatorio_08 m) {
        try {
            CallableStatement cs = conn.prepareCall("{CALL SP_ATUALIZAR_RELATORIO_08(?,?,?,?,?,?)}");
            cs.setInt(1, m.getA08_id_relatorio());
            cs.setString(2, m.getA08_titulo());
            cs.setString(3, m.getA08_data_geracao());
            cs.setString(4, m.getA08_conteudo());
            cs.setInt(5, m.getA08_id_usuario());
            cs.setInt(6, m.getA08_id_projeto());
            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------
    // DELETE
    // ------------------------------------------------------
    public boolean excluirRelatorio(int id) {
        try {
            CallableStatement cs = conn.prepareCall("{CALL SP_EXCLUIR_RELATORIO_08(?)}");
            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------
    // BUSCAR
    // ------------------------------------------------------
    public Model_Relatorio_08 buscarRelatorio(int id) {
        try {
            CallableStatement cs = conn.prepareCall("{CALL SP_BUSCAR_RELATORIO_08(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Model_Relatorio_08 m = new Model_Relatorio_08();
                m.setA08_id_relatorio(rs.getInt("A08_ID_RELATORIO"));
                m.setA08_titulo(rs.getString("A08_TITULO"));
                m.setA08_data_geracao(rs.getString("A08_DATA_GERACAO"));
                m.setA08_conteudo(rs.getString("A08_CONTEUDO"));
                m.setA08_id_usuario(rs.getInt("A08_ID_USUARIO"));
                m.setA08_id_projeto(rs.getInt("A08_ID_PROJETO"));
                return m;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------------------------
    // LISTAR
    // ------------------------------------------------------
    public List<Model_Relatorio_08> listarRelatorios() {
        List<Model_Relatorio_08> lista = new ArrayList<>();

        try {
            CallableStatement cs = conn.prepareCall("{CALL SP_LISTAR_RELATORIO_08()}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Model_Relatorio_08 m = new Model_Relatorio_08();
                m.setA08_id_relatorio(rs.getInt("A08_ID_RELATORIO"));
                m.setA08_titulo(rs.getString("A08_TITULO"));
                m.setA08_data_geracao(rs.getString("A08_DATA_GERACAO"));
                m.setA08_conteudo(rs.getString("A08_CONTEUDO"));
                m.setA08_id_usuario(rs.getInt("A08_ID_USUARIO"));
                m.setA08_id_projeto(rs.getInt("A08_ID_PROJETO"));
                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}

