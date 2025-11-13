package Pck_Persistencia_LIMS;

import java.sql.*;
import javax.swing.JOptionPane;
import Pck_DAO_LIMS.Conexao;
import Pck_Model_LIMS.Model_Projeto_01;

public class Persistencia_Projeto_01 {

    private Connection conec;
    private CallableStatement preparedState;

    // ==============================
    // INSERIR
    // ==============================
    public void inserir_projeto(Model_Projeto_01 projeto) {
        try {
            conec = Conexao.getConnection();
            preparedState = conec.prepareCall("{CALL PCK_PROJETO_01_PROC_INSERIR(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            preparedState.setString(1, projeto.getA01_NOME_PROJETO());
            preparedState.setString(2, projeto.getA01_DESCRICAO());
            preparedState.setDate(3, projeto.getA01_DATA_INICIAL());
            preparedState.setDate(4, projeto.getA01_DATA_FINAL());
            preparedState.setDouble(5, projeto.getA01_ORCAMENTO());
            preparedState.setString(6, projeto.getA01_STATUS_PROJETO());
            preparedState.setString(7, projeto.getA01_DEPARTAMENTO());
            preparedState.setInt(8, projeto.getA01_ID_USUARIO());
            preparedState.registerOutParameter(9, Types.INTEGER);

            preparedState.execute();
            int novoId = preparedState.getInt(9);
            JOptionPane.showMessageDialog(null, "Projeto inserido com sucesso! ID: " + novoId);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir projeto: " + e.getMessage());
        } finally {
            Conexao.closeConnection(conec, preparedState);
        }
    }

    // ==============================
    // ATUALIZAR
    // ==============================
    public void atualizar_projeto(Model_Projeto_01 projeto) {
        try {
            conec = Conexao.getConnection();
            preparedState = conec.prepareCall("{CALL PCK_PROJETO_01_PROC_ALTERAR(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            preparedState.setInt(1, projeto.getA01_ID_PROJETO());
            preparedState.setString(2, projeto.getA01_NOME_PROJETO());
            preparedState.setString(3, projeto.getA01_DESCRICAO());
            preparedState.setDate(4, projeto.getA01_DATA_INICIAL());
            preparedState.setDate(5, projeto.getA01_DATA_FINAL());
            preparedState.setDouble(6, projeto.getA01_ORCAMENTO());
            preparedState.setString(7, projeto.getA01_STATUS_PROJETO());
            preparedState.setString(8, projeto.getA01_DEPARTAMENTO());
            preparedState.setInt(9, projeto.getA01_ID_USUARIO());

            preparedState.execute();
            JOptionPane.showMessageDialog(null, "Projeto atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar projeto: " + e.getMessage());
        } finally {
            Conexao.closeConnection(conec, preparedState);
        }
    }

    // ==============================
    // DELETAR
    // ==============================
    public void deletar_projeto(int idProjeto) {
        try {
            conec = Conexao.getConnection();
            preparedState = conec.prepareCall("{CALL PCK_PROJETO_01_PROC_DELETAR(?)}");
            preparedState.setInt(1, idProjeto);
            preparedState.execute();
            JOptionPane.showMessageDialog(null, "Projeto deletado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar projeto: " + e.getMessage());
        } finally {
            Conexao.closeConnection(conec, preparedState);
        }
    }

    // ==============================
    // CONSULTAR POR ID
    // ==============================
    public Model_Projeto_01 consultar_projeto_por_id(int idProjeto) {
        Model_Projeto_01 projeto = null;
        try {
            conec = Conexao.getConnection();
            preparedState = conec.prepareCall("SELECT * FROM PROJETO_01 WHERE A01_ID_PROJETO = ?");
            preparedState.setInt(1, idProjeto);
            ResultSet rs = preparedState.executeQuery();

            if (rs.next()) {
                projeto = new Model_Projeto_01();
                projeto.setA01_ID_PROJETO(rs.getInt("A01_ID_PROJETO"));
                projeto.setA01_NOME_PROJETO(rs.getString("A01_NOME_PROJETO"));
                projeto.setA01_DESCRICAO(rs.getString("A01_DESCRICAO"));
                projeto.setA01_DATA_INICIAL(rs.getDate("A01_DATA_INICIAL"));
                projeto.setA01_DATA_FINAL(rs.getDate("A01_DATA_FINAL"));
                projeto.setA01_ORCAMENTO(rs.getDouble("A01_ORCAMENTO"));
                projeto.setA01_STATUS_PROJETO(rs.getString("A01_STATUS_PROJETO"));
                projeto.setA01_DEPARTAMENTO(rs.getString("A01_DEPARTAMENTO"));
                projeto.setA01_ID_USUARIO(rs.getInt("A01_ID_USUARIO"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar projeto: " + e.getMessage());
        } finally {
            Conexao.closeConnection(conec, preparedState);
        }
        return projeto;
    }

    // ==============================
    // LISTAR TODOS
    // ==============================
    public ResultSet listar_projetos() {
        ResultSet rs = null;
        try {
            conec = Conexao.getConnection();
            preparedState = conec.prepareCall("SELECT * FROM PROJETO_01");
            rs = preparedState.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar projetos: " + e.getMessage());
        }
        return rs;
    }
}