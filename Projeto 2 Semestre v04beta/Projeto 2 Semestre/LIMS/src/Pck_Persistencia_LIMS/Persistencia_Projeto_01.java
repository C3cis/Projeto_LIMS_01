package Pck_Persistencia_LIMS;

import java.sql.*;
import java.util.ArrayList;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_DAO_LIMS.DAO_Conexao;

public class Persistencia_Projeto_01 {

    private Connection conec;
    private CallableStatement preparedState;

    public Persistencia_Projeto_01() {
        // abrimos/fechamos em cada método
    }

    // inserir
    public boolean inserir_projeto(Model_Projeto_01 p) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_INSERIR_PROJETO_01(?,?,?,?,?,?,?,?)}");

            preparedState.setString(1, p.getA01_nome_projeto());
            preparedState.setString(2, p.getA01_descricao());
            preparedState.setDate(3, p.getA01_data_inicial());
            preparedState.setDate(4, p.getA01_data_final());
            preparedState.setDouble(5, p.getA01_orcamento());
            preparedState.setString(6, p.getA01_status_projeto());
            preparedState.setString(7, p.getA01_departamento());
            preparedState.setInt(8, p.getA01_id_usuario());

            preparedState.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // atualizar
    public boolean atualizar_projeto(Model_Projeto_01 p) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_ATUALIZAR_PROJETO_01(?,?,?,?,?,?,?,?,?)}");

            preparedState.setInt(1, p.getA01_id_projeto());
            preparedState.setString(2, p.getA01_nome_projeto());
            preparedState.setString(3, p.getA01_descricao());
            preparedState.setDate(4, p.getA01_data_inicial());
            preparedState.setDate(5, p.getA01_data_final());
            preparedState.setDouble(6, p.getA01_orcamento());
            preparedState.setString(7, p.getA01_status_projeto());
            preparedState.setString(8, p.getA01_departamento());
            preparedState.setInt(9, p.getA01_id_usuario());

            preparedState.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // deletar
    public boolean deletar_projeto(int id) {
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_EXCLUIR_PROJETO_01(?)}");
            preparedState.setInt(1, id);
            preparedState.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
    }

    // buscar por id -> retorna Model (pode ser usado pelo view para preencher edicao)
    public Model_Projeto_01 buscar_projeto(int id) {
        ResultSet rs = null;
        Model_Projeto_01 p = null;
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_BUSCAR_PROJETO_01(?)}");
            preparedState.setInt(1, id);
            rs = preparedState.executeQuery();

            if (rs.next()) {
                p = new Model_Projeto_01();
                p.setA01_id_projeto(rs.getInt("A01_ID_PROJETO"));
                p.setA01_nome_projeto(rs.getString("A01_NOME_PROJETO"));
                p.setA01_descricao(rs.getString("A01_DESCRICAO"));
                p.setA01_data_inicial(rs.getDate("A01_DATA_INICIAL"));
                p.setA01_data_final(rs.getDate("A01_DATA_FINAL"));
                p.setA01_orcamento(rs.getDouble("A01_ORCAMENTO"));
                p.setA01_status_projeto(rs.getString("A01_STATUS_PROJETO"));
                p.setA01_departamento(rs.getString("A01_DEPARTAMENTO"));
                p.setA01_id_usuario(rs.getInt("A01_ID_USUARIO"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
        return p;
    }

    // listar -> retorna lista para Controller/View montar a tabela
    public ArrayList<Model_Projeto_01> listar_projeto() {
        ArrayList<Model_Projeto_01> lista = new ArrayList<>();
        ResultSet rs = null;
        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_LISTAR_PROJETO_01()}");
            rs = preparedState.executeQuery();

            while (rs.next()) {
                Model_Projeto_01 p = new Model_Projeto_01();
                p.setA01_id_projeto(rs.getInt("A01_ID_PROJETO"));
                p.setA01_nome_projeto(rs.getString("A01_NOME_PROJETO"));
                p.setA01_descricao(rs.getString("A01_DESCRICAO"));
                p.setA01_data_inicial(rs.getDate("A01_DATA_INICIAL"));
                p.setA01_data_final(rs.getDate("A01_DATA_FINAL"));
                p.setA01_orcamento(rs.getDouble("A01_ORCAMENTO"));
                p.setA01_status_projeto(rs.getString("A01_STATUS_PROJETO"));
                p.setA01_departamento(rs.getString("A01_DEPARTAMENTO"));
                p.setA01_id_usuario(rs.getInt("A01_ID_USUARIO"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (preparedState != null) preparedState.close(); } catch (Exception ignored) {}
            try { if (conec != null) conec.close(); } catch (Exception ignored) {}
        }
        return lista;
    }
}