package Pck_Persistencia_LIMS;

import Pck_Model_LIMS.Model_Produto_02;
import java.sql.*;
import java.util.ArrayList;

public class Persistencia_Produto_02 {

    private Connection conexao;

    public Persistencia_Produto_02(Connection conexao) {
        this.conexao = conexao;
    }
    public boolean inserirProduto(Model_Produto_02 produto) {
        String sql = "{ CALL SP_INSERIR_PRODUTO_02(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setString(1, produto.getA02_nome_produto());
            cs.setString(2, produto.getA02_descricao());
            cs.setString(3, produto.getA02_tipo());
            cs.setDate(4, produto.getA02_data_cadastro());
            cs.setDate(5, produto.getA02_data_chegada());
            cs.setDouble(6, produto.getA02_valor_unitario());
            cs.setInt(7, produto.getA02_id_projeto());
            cs.setInt(8, produto.getA02_id_fornecedor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean atualizarProduto(Model_Produto_02 produto) {
        String sql = "{ CALL SP_ATUALIZAR_PRODUTO_02(?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, produto.getA02_id_produto());
            cs.setString(2, produto.getA02_nome_produto());
            cs.setString(3, produto.getA02_descricao());
            cs.setString(4, produto.getA02_tipo());
            cs.setDate(5, produto.getA02_data_cadastro());
            cs.setDate(6, produto.getA02_data_chegada());
            cs.setDouble(7, produto.getA02_valor_unitario());
            cs.setInt(8, produto.getA02_id_projeto());
            cs.setInt(9, produto.getA02_id_fornecedor());

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean excluirProduto(int idProduto) {
        String sql = "{ CALL SP_EXCLUIR_PRODUTO_02(?) }";

        try (CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, idProduto);
            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Model_Produto_02 buscarProduto(int idProduto) {
        String sql = "{ CALL SP_BUSCAR_PRODUTO_02(?) }";

        try (CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, idProduto);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Model_Produto_02 p = new Model_Produto_02();

                p.setA02_id_produto(rs.getInt("A02_ID_PRODUTO"));
                p.setA02_nome_produto(rs.getString("A02_NOME_PRODUTO"));
                p.setA02_descricao(rs.getString("A02_DESCRICAO"));
                p.setA02_tipo(rs.getString("A02_TIPO"));
                p.setA02_data_cadastro(rs.getDate("A02_DATA_CADASTRO"));
                p.setA02_data_chegada(rs.getDate("A02_DATA_CHEGADA"));
                p.setA02_valor_unitario(rs.getDouble("A02_VALOR_UNITARIO"));
                p.setA02_id_projeto(rs.getInt("A02_ID_PROJETO"));
                p.setA02_id_fornecedor(rs.getInt("A02_ID_FORNECEDOR"));

                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Model_Produto_02> listarProdutos() {
        ArrayList<Model_Produto_02> lista = new ArrayList<>();

        String sql = "{ CALL SP_LISTAR_PRODUTO_02() }";

        try (CallableStatement cs = conexao.prepareCall(sql)) {

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Model_Produto_02 p = new Model_Produto_02();

                p.setA02_id_produto(rs.getInt("A02_ID_PRODUTO"));
                p.setA02_nome_produto(rs.getString("A02_NOME_PRODUTO"));
                p.setA02_descricao(rs.getString("A02_DESCRICAO"));
                p.setA02_tipo(rs.getString("A02_TIPO"));
                p.setA02_data_cadastro(rs.getDate("A02_DATA_CADASTRO"));
                p.setA02_data_chegada(rs.getDate("A02_DATA_CHEGADA"));
                p.setA02_valor_unitario(rs.getDouble("A02_VALOR_UNITARIO"));
                p.setA02_id_projeto(rs.getInt("A02_ID_PROJETO"));
                p.setA02_id_fornecedor(rs.getInt("A02_ID_FORNECEDOR"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
