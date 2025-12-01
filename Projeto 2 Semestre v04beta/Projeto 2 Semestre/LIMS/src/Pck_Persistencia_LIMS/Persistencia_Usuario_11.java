package Pck_Persistencia_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Usuario_11;
import java.sql.*;
import java.util.ArrayList;

public class Persistencia_Usuario_11 {

    private Connection conec;
    private CallableStatement preparedState;

    private void abrir() throws SQLException {
        conec = DAO_Conexao.connect();
        if (conec == null) {
            throw new SQLException("Falha ao conectar: conec == null.");
        }
    }
    private void fechar() {
        try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
        try { if (conec != null) conec.close(); } catch (Exception ignored) {}
    }
    public boolean inserir(Model_Usuario_11 u) {
        try {
            abrir();
            preparedState = conec.prepareCall("{CALL SP_INSERIR_USUARIO_11(?,?,?,?,?,?)}");

            preparedState.setString(1, u.getA11_nome());
            preparedState.setString(2, u.getA11_email());
            preparedState.setString(3, u.getA11_cargo());
            preparedState.setString(4, u.getA11_senha());
            preparedState.setString(5, u.getA11_status_usuario());
            preparedState.setString(6, u.getA11_codigo_usuario());

            preparedState.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            fechar();
        }
    }
    public boolean atualizar(Model_Usuario_11 u) {
        try {
            abrir();
            preparedState = conec.prepareCall("{CALL SP_ATUALIZAR_USUARIO_11(?,?,?,?,?,?)}");

            preparedState.setInt(1, u.getA11_id_usuario());
            preparedState.setString(2, u.getA11_nome());
            preparedState.setString(3, u.getA11_email());
            preparedState.setString(4, u.getA11_cargo());
            preparedState.setString(5, u.getA11_senha());
            preparedState.setString(6, u.getA11_status_usuario());

            preparedState.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            fechar();
        }
    }
    public boolean excluir(int id) {
        try {
            abrir();
            preparedState = conec.prepareCall("{CALL SP_EXCLUIR_USUARIO_11(?)}");
            preparedState.setInt(1, id);

            preparedState.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            fechar();
        }
    }
    public Model_Usuario_11 buscar(int id) {

        ResultSet rs = null;
        Model_Usuario_11 u = null;

        try {
            abrir();
            preparedState = conec.prepareCall("{CALL SP_BUSCAR_USUARIO_11(?)}");
            preparedState.setInt(1, id);

            rs = preparedState.executeQuery();

            if (rs.next()) {
                u = new Model_Usuario_11();
                u.setA11_id_usuario(rs.getInt("A11_ID_USUARIO"));
                u.setA11_nome(rs.getString("A11_NOME"));
                u.setA11_email(rs.getString("A11_EMAIL"));
                u.setA11_cargo(rs.getString("A11_CARGO"));
                u.setA11_senha(rs.getString("A11_SENHA"));
                u.setA11_status_usuario(rs.getString("A11_STATUS_USUARIO"));
                u.setA11_codigo_usuario(rs.getString("A11_CODIGO_USUARIO"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            fechar();
        }

        return u;
    }
    public ArrayList<Model_Usuario_11> listar() {

        ArrayList<Model_Usuario_11> lista = new ArrayList<>();
        ResultSet rs = null;

        try {
            abrir();
            preparedState = conec.prepareCall("{CALL SP_LISTAR_USUARIO_11()}");

            rs = preparedState.executeQuery();

            while (rs.next()) {
                Model_Usuario_11 u = new Model_Usuario_11();
                u.setA11_id_usuario(rs.getInt("A11_ID_USUARIO"));
                u.setA11_nome(rs.getString("A11_NOME"));
                u.setA11_email(rs.getString("A11_EMAIL"));
                u.setA11_cargo(rs.getString("A11_CARGO"));
                u.setA11_senha(rs.getString("A11_SENHA"));
                u.setA11_status_usuario(rs.getString("A11_STATUS_USUARIO"));
                u.setA11_codigo_usuario(rs.getString("A11_CODIGO_USUARIO"));

                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            fechar();
        }

        return lista;
    }
    public Model_Usuario_11 login(String email, String senha) {

        ResultSet rs = null;
        Model_Usuario_11 u = null;

        try {
            abrir();
            preparedState = conec.prepareCall(
                    "SELECT * FROM USUARIO_11 WHERE A11_EMAIL = ? AND A11_SENHA = ?"
            );

            preparedState.setString(1, email);
            preparedState.setString(2, senha);

            rs = preparedState.executeQuery();

            if (rs.next()) {
                u = new Model_Usuario_11();
                u.setA11_id_usuario(rs.getInt("A11_ID_USUARIO"));
                u.setA11_nome(rs.getString("A11_NOME"));
                u.setA11_email(rs.getString("A11_EMAIL"));
                u.setA11_cargo(rs.getString("A11_CARGO"));
                u.setA11_status_usuario(rs.getString("A11_STATUS_USUARIO"));
                u.setA11_codigo_usuario(rs.getString("A11_CODIGO_USUARIO"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            fechar();
        }

        return u;
    }
}

