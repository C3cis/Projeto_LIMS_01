package Pck_Persistencia_LIMS;
import Pck_Model_LIMS.Model_Fornecedor_04;
import Pck_DAO_LIMS.DAO_Conexao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Persistencia_Fornecedor_04 {
    // --------------------- INSERIR ------------------------
    public boolean inserir_fornecedor(Model_Fornecedor_04 f) {
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            conn = DAO_Conexao.connect();

            stmt = conn.prepareCall("{CALL SP_INSERIR_FORNECEDOR_04(?,?,?,?,?)}");
            stmt.setString(1, f.getA04_nome_fornecedor());
            stmt.setString(2, f.getA04_cnpj_fornecedor());
            stmt.setString(3, f.getA04_telefone_fornecedor());
            stmt.setString(4, f.getA04_email_fornecedor());
            stmt.setString(5, f.getA04_endereco_fornecedor());

            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    // --------------------- ATUALIZAR ------------------------
    public boolean atualizar_fornecedor(Model_Fornecedor_04 f) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = DAO_Conexao.connect();

            stmt = conn.prepareCall("{CALL SP_ATUALIZAR_FORNECEDOR_04(?,?,?,?,?,?)}");
            stmt.setInt(1, f.getA04_id_fornecedor());
            stmt.setString(2, f.getA04_nome_fornecedor());
            stmt.setString(3, f.getA04_contato_fornecedor());
            stmt.setString(4, f.getA04_email_fornecedor());
            stmt.setString(5, f.getA04_telefone_fornecedor());
            stmt.setString(6, f.getA04_cnpj_fornecedor());

            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    // --------------------- BUSCAR ------------------------
    public Model_Fornecedor_04 buscar_fornecedor(int id) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DAO_Conexao.connect();

            stmt = conn.prepareCall("{CALL SP_BUSCAR_FORNECEDOR_04(?)}");
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Model_Fornecedor_04 f = new Model_Fornecedor_04();

                f.setA04_id_fornecedor(rs.getInt("A04_ID_FORNECEDOR"));
                f.setA04_nome_fornecedor(rs.getString("A04_NOME"));
                f.setA04_cnpj_fornecedor(rs.getString("A04_CNPJ"));
                f.setA04_telefone_fornecedor(rs.getString("A04_TELEFONE"));
                f.setA04_email_fornecedor(rs.getString("A04_EMAIL"));
                f.setA04_endereco_fornecedor(rs.getString("A04_ENDERECO"));
                return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return null;
    }

    // --------------------- LISTAR ------------------------
    public ArrayList<Model_Fornecedor_04> listar_fornecedor() {
        ArrayList<Model_Fornecedor_04> lista = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DAO_Conexao.connect();

            stmt = conn.prepareCall("{CALL SP_LISTAR_FORNECEDOR_04()}");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Model_Fornecedor_04 f = new Model_Fornecedor_04();

                f.setA04_id_fornecedor(rs.getInt("A04_ID_FORNECEDOR"));
                f.setA04_nome_fornecedor(rs.getString("A04_NOME"));
                f.setA04_cnpj_fornecedor(rs.getString("A04_CNPJ"));
                f.setA04_telefone_fornecedor(rs.getString("A04_TELEFONE"));
                f.setA04_email_fornecedor(rs.getString("A04_EMAIL"));
                f.setA04_endereco_fornecedor(rs.getString("A04_ENDERECO"));

                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return lista;
    }

    // --------------------- DELETAR ------------------------
    public boolean deletar_fornecedor(int id) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = DAO_Conexao.connect();

            stmt = conn.prepareCall("{CALL SP_EXCLUIR_FORNECEDOR_04(?)}");
            stmt.setInt(1, id);

            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}

