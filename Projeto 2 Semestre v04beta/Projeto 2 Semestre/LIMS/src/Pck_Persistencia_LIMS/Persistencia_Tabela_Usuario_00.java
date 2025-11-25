package Pck_Persistencia_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Tabela_Usuario_00;

import java.sql.*;
import java.util.ArrayList;

public class Persistencia_Tabela_Usuario_00 {

    private Connection conec;
    private CallableStatement preparedState;

    public ArrayList<Model_Tabela_Usuario_00> listar_tabela_usuario_00(String filtro) {

        ArrayList<Model_Tabela_Usuario_00> lista = new ArrayList<>();

        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_LISTAR_TELA_USUARIO_FILTRO(?)}");
            preparedState.setString(1, filtro);

            ResultSet rs = preparedState.executeQuery();

            while (rs.next()) {
                Model_Tabela_Usuario_00 m = new Model_Tabela_Usuario_00();

                m.setA00_id_produto(rs.getInt("A00_ID_PRODUTO"));
                m.setA00_nome_produto(rs.getString("A00_NOME_PRODUTO"));
                m.setA00_tipo_produto(rs.getString("A00_TIPO_PRODUTO"));
                m.setA00_nome_projeto(rs.getString("A00_NOME_PROJETO"));
                m.setA00_nome_fornecedor(rs.getString("A00_NOME_FORNECEDOR"));
                m.setA00_data_chegada(rs.getDate("A00_DATA_CHEGADA"));

                lista.add(m);
            }

            conec.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    public Model_Tabela_Usuario_00 buscar_detalhes(int id) {

        Model_Tabela_Usuario_00 m = new Model_Tabela_Usuario_00();

        try {
            conec = DAO_Conexao.connect();
            preparedState = conec.prepareCall("{CALL SP_TELA_USUARIO_DETALHES(?)}");
            preparedState.setInt(1, id);

            ResultSet rs = preparedState.executeQuery();

            if (rs.next()) {

                // ----- PRODUTO -----
                m.setA00_id_produto(id);
                m.setA00_nome_produto(rs.getString("NOME_PRODUTO"));
                m.setA00_descricao_produto(rs.getString("DESCRICAO_PRODUTO"));
                m.setA00_tipo_produto(rs.getString("TIPO_PRODUTO"));
                m.setA00_valor_unitario(rs.getDouble("VALOR_UNITARIO"));
                m.setA00_data_cadastro(rs.getDate("DATA_CADASTRO"));
                m.setA00_data_chegada(rs.getDate("DATA_CHEGADA"));
                // ----- PROJETO -----
                m.setA00_id_projeto(rs.getInt("ID_PROJETO"));
                m.setA00_nome_projeto(rs.getString("NOME_PROJETO"));
                m.setA00_descricao_projeto(rs.getString("DESCRICAO_PROJETO"));
                m.setA00_data_inicio(rs.getDate("DATA_INICIO"));
                m.setA00_data_fim(rs.getDate("DATA_FIM"));
                m.setA00_orcamento(rs.getDouble("ORCAMENTO"));
                m.setA00_status_projeto(rs.getString("STATUS_PROJETO"));
                m.setA00_departamento(rs.getString("DEPARTAMENTO"));

                // ----- FORNECEDOR -----
                m.setA00_nome_fornecedor(rs.getString("NOME_FORNECEDOR"));
                m.setA00_cnpj_fornecedor(rs.getString("CNPJ"));
                m.setA00_telefone_fornecedor(rs.getString("TELEFONE"));
                m.setA00_email_fornecedor(rs.getString("EMAIL"));
                m.setA00_endereco_fornecedor(rs.getString("ENDERECO"));

            }

            conec.close();

        } catch (Exception e) {
            System.out.println("Erro ao buscar detalhes: " + e.getMessage());
        }

        return m;
    }
}