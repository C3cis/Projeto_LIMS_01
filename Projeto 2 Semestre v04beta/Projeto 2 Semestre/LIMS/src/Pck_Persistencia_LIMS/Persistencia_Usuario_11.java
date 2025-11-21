package Pck_Persistencia_LIMS;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Usuario_11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia_Usuario_11 {

    private Connection conn;

    public Persistencia_Usuario_11() {
        try {
            conn = DAO_Conexao.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // INSERIR
    // ============================================
    public void inserir(Model_Usuario_11 m) throws Exception {
        CallableStatement cs = conn.prepareCall("{CALL SP_INSERIR_USUARIO_11(?,?,?,?,?)}");
        cs.setString(1, m.getA11_nome());
        cs.setString(2, m.getA11_email());
        cs.setString(3, m.getA11_cargo());
        cs.setString(4, m.getA11_senha());
        cs.setString(5, m.getA11_status_usuario());
        cs.execute();
        cs.close();
    }

    // ============================================
    // ATUALIZAR
    // ============================================
    public void atualizar(Model_Usuario_11 m) throws Exception {
        CallableStatement cs = conn.prepareCall("{CALL SP_ATUALIZAR_USUARIO_11(?,?,?,?,?,?)}");
        cs.setInt(1, m.getA11_id_usuario());
        cs.setString(2, m.getA11_nome());
        cs.setString(3, m.getA11_email());
        cs.setString(4, m.getA11_cargo());
        cs.setString(5, m.getA11_senha());
        cs.setString(6, m.getA11_status_usuario());
        cs.execute();
        cs.close();
    }

    // ============================================
    // EXCLUIR
    // ============================================
    public void excluir(int id) throws Exception {
        CallableStatement cs = conn.prepareCall("{CALL SP_EXCLUIR_USUARIO_11(?)}");
        cs.setInt(1, id);
        cs.execute();
        cs.close();
    }

    // ============================================
    // BUSCAR POR ID
    // ============================================
    public Model_Usuario_11 buscar(int id) throws Exception {
        CallableStatement cs = conn.prepareCall("{CALL SP_BUSCAR_USUARIO_11(?)}");
        cs.setInt(1, id);

        ResultSet rs = cs.executeQuery();

        Model_Usuario_11 m = null;

        if (rs.next()) {
            m = new Model_Usuario_11();
            m.setA11_id_usuario(rs.getInt("A11_ID_USUARIO"));
            m.setA11_nome(rs.getString("A11_NOME"));
            m.setA11_email(rs.getString("A11_EMAIL"));
            m.setA11_cargo(rs.getString("A11_CARGO"));
            m.setA11_senha(rs.getString("A11_SENHA"));
            m.setA11_status_usuario(rs.getString("A11_STATUS_USUARIO"));
        }

        rs.close();
        cs.close();
        return m;
    }

    // ============================================
    // LISTAR TODOS
    // ============================================
    public List<Model_Usuario_11> listar() throws Exception {
        CallableStatement cs = conn.prepareCall("{CALL SP_LISTAR_USUARIO_11()}");

        ResultSet rs = cs.executeQuery();
        List<Model_Usuario_11> lista = new ArrayList<>();

        while (rs.next()) {
            Model_Usuario_11 m = new Model_Usuario_11();
            m.setA11_id_usuario(rs.getInt("A11_ID_USUARIO"));
            m.setA11_nome(rs.getString("A11_NOME"));
            m.setA11_email(rs.getString("A11_EMAIL"));
            m.setA11_cargo(rs.getString("A11_CARGO"));
            m.setA11_senha(rs.getString("A11_SENHA"));
            m.setA11_status_usuario(rs.getString("A11_STATUS_USUARIO"));
            lista.add(m);
        }

        rs.close();
        cs.close();
        return lista;
    }
}

