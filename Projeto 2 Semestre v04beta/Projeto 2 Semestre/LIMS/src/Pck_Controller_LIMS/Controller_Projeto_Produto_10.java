package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Projeto_Produto_10;
import Pck_Persistencia_LIMS.Persistencia_Projeto_Produto_10;

import java.util.ArrayList;

public class Controller_Projeto_Produto_10 {

    private Persistencia_Projeto_Produto_10 persistencia = new Persistencia_Projeto_Produto_10();

    public boolean inserir_projeto_produto(Model_Projeto_Produto_10 m) {
        return persistencia.inserir_projeto_produto(m);
    }

    public boolean excluir_projeto_produto(int idProduto, int idProjeto) {
        return persistencia.excluir_projeto_produto(idProduto, idProjeto);
    }

    public ArrayList<Model_Projeto_Produto_10> listar_por_projeto(int idProjeto) {
        return persistencia.listar_por_projeto(idProjeto);
    }
}