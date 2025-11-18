package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Produto_02;
import Pck_Persistencia_LIMS.Persistencia_Produto_02;

import java.sql.Connection;
import java.util.ArrayList;

public class Controller_Produto_02 {

    private Persistencia_Produto_02 persistencia;

    public Controller_Produto_02(Connection conexao) {
        this.persistencia = new Persistencia_Produto_02(conexao);
    }

    // ----------------------------------------------------------------------
    // INSERIR
    // ----------------------------------------------------------------------
    public boolean inserirProduto(
            String nome,
            String descricao,
            String tipo,
            java.util.Date dataCadastro,
            java.util.Date dataChegada,
            double valorUnitario,
            int idProjeto,
            int idFornecedor
    ) {

        try {
            Model_Produto_02 produto = new Model_Produto_02();

            produto.setA02_nome_produto(nome);
            produto.setA02_descricao(descricao);
            produto.setA02_tipo(tipo);
            produto.setA02_data_cadastro(new java.sql.Date(dataCadastro.getTime()));
            produto.setA02_data_chegada(new java.sql.Date(dataChegada.getTime()));
            produto.setA02_valor_unitario(valorUnitario);
            produto.setA02_id_projeto(idProjeto);
            produto.setA02_id_fornecedor(idFornecedor);

            return persistencia.inserirProduto(produto);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------------------------
    // ATUALIZAR
    // ----------------------------------------------------------------------
    public boolean atualizarProduto(
            int idProduto,
            String nome,
            String descricao,
            String tipo,
            java.util.Date dataCadastro,
            java.util.Date dataChegada,
            double valorUnitario,
            int idProjeto,
            int idFornecedor
    ) {
        try {
            Model_Produto_02 produto = new Model_Produto_02();

            produto.setA02_id_produto(idProduto);
            produto.setA02_nome_produto(nome);
            produto.setA02_descricao(descricao);
            produto.setA02_tipo(tipo);
            produto.setA02_data_cadastro(new java.sql.Date(dataCadastro.getTime()));
            produto.setA02_data_chegada(new java.sql.Date(dataChegada.getTime()));
            produto.setA02_valor_unitario(valorUnitario);
            produto.setA02_id_projeto(idProjeto);
            produto.setA02_id_fornecedor(idFornecedor);

            return persistencia.atualizarProduto(produto);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------------------------
    // EXCLUIR
    // ----------------------------------------------------------------------
    public boolean excluirProduto(int idProduto) {
        return persistencia.excluirProduto(idProduto);
    }

    // ----------------------------------------------------------------------
    // BUSCAR
    // ----------------------------------------------------------------------
    public Model_Produto_02 buscarProduto(int idProduto) {
        return persistencia.buscarProduto(idProduto);
    }

    // ----------------------------------------------------------------------
    // LISTAR
    // ----------------------------------------------------------------------
    public ArrayList<Model_Produto_02> listarProdutos() {
        return persistencia.listarProdutos();
    }
}