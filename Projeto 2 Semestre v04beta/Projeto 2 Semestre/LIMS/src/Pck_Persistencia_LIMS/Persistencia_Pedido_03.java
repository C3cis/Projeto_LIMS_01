package Pck_Persistencia_LIMS;


import java.sql.*;
import java.util.ArrayList;
import Pck_Model_LIMS.Model_Pedido_03;
import Pck_DAO_LIMS.DAO_Conexao;

public class Persistencia_Pedido_03 {

    private Connection conec;
    private CallableStatement callable;

    public Persistencia_Pedido_03() {
        // abrimos/fechamos em cada método para segurança
    }

    // inserir
    public boolean inserir_pedido(Model_Pedido_03 p) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_INSERIR_PEDIDO_03(?, ?, ?, ?, ?) }");

            // ordem: V_DATA, V_STATUS, V_OBS, V_ID_USUARIO, V_ID_FORNECEDOR
            callable.setDate(1, p.getA03_data_pedido());
            callable.setString(2, p.getA03_status_pedido());
            callable.setString(3, p.getA03_observacoes());
            callable.setInt(4, p.getA03_id_usuario());
            callable.setInt(5, p.getA03_id_fornecedor());

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

    // atualizar
    public boolean atualizar_pedido(Model_Pedido_03 p) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_ATUALIZAR_PEDIDO_03(?, ?, ?, ?, ?, ?) }");

            // ordem: pID, pDATA, pSTATUS, pOBS, pID_USUARIO, pID_FORNECEDOR
            callable.setInt(1, p.getA03_id_pedido());
            callable.setDate(2, p.getA03_data_pedido());
            callable.setString(3, p.getA03_status_pedido());
            callable.setString(4, p.getA03_observacoes());
            callable.setInt(5, p.getA03_id_usuario());
            callable.setInt(6, p.getA03_id_fornecedor());

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

    // deletar
    public boolean deletar_pedido(int id) {
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_EXCLUIR_PEDIDO_03(?) }");

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

    // buscar por id -> retorna Model
    public Model_Pedido_03 buscar_pedido(int id) {
        ResultSet rs = null;
        Model_Pedido_03 p = null;
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_BUSCAR_PEDIDO_03(?) }");
            callable.setInt(1, id);
            rs = callable.executeQuery();

            if (rs.next()) {
                p = new Model_Pedido_03();
                p.setA03_id_pedido(rs.getInt("A03_ID_PEDIDO"));
                p.setA03_data_pedido(rs.getDate("A03_DATA_PEDIDO"));
                p.setA03_status_pedido(rs.getString("A03_STATUS_PEDIDO"));
                p.setA03_observacoes(rs.getString("A03_OBSERVACOES"));
                p.setA03_id_usuario(rs.getInt("A03_ID_USUARIO"));
                p.setA03_id_fornecedor(rs.getInt("A03_ID_FORNECEDOR"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (callable != null) callable.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
        return p;
    }

    // listar -> retorna lista para Controller/View montar a tabela
    public ArrayList<Model_Pedido_03> listar_pedido() {
        ArrayList<Model_Pedido_03> lista = new ArrayList<>();
        ResultSet rs = null;
        try {
            conec = DAO_Conexao.connect();
            callable = conec.prepareCall("{ CALL SP_LISTAR_PEDIDO_03() }");
            rs = callable.executeQuery();

            while (rs.next()) {
                Model_Pedido_03 p = new Model_Pedido_03();
                p.setA03_id_pedido(rs.getInt("A03_ID_PEDIDO"));
                p.setA03_data_pedido(rs.getDate("A03_DATA_PEDIDO"));
                p.setA03_status_pedido(rs.getString("A03_STATUS_PEDIDO"));
                p.setA03_observacoes(rs.getString("A03_OBSERVACOES"));
                p.setA03_id_usuario(rs.getInt("A03_ID_USUARIO"));
                p.setA03_id_fornecedor(rs.getInt("A03_ID_FORNECEDOR"));
                lista.add(p);
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

