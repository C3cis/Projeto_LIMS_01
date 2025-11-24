package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Fornecedor_04;
import Pck_Persistencia_LIMS.Persistencia_Fornecedor_04;

import java.util.ArrayList;

public class Controller_Fornecedor_04 {

    Persistencia_Fornecedor_04 persistencia = new Persistencia_Fornecedor_04();

    // --------------------------- SALVAR ---------------------------
    public String salvarFornecedor(Model_Fornecedor_04 fornecedor) {

        if (fornecedor.getA04_nome().isEmpty() ||
                fornecedor.getA04_cnpj().isEmpty() ||
                fornecedor.getA04_telefone().isEmpty() ||
                fornecedor.getA04_email().isEmpty() ||
                fornecedor.getA04_endereco().isEmpty()) {

            return "Preencha todos os campos obrigatórios!";
        }

        boolean sucesso = persistencia.salvarFornecedor(fornecedor);
        return sucesso ? "Fornecedor salvo com sucesso!" : "Erro ao salvar fornecedor!";
    }

    // --------------------------- ATUALIZAR ---------------------------
    public String atualizarFornecedor(Model_Fornecedor_04 fornecedor) {

        if (fornecedor.getA04_id_fornecedor() <= 0) {
            return "ID inválido!";
        }

        boolean sucesso = persistencia.atualizarFornecedor(fornecedor);
        return sucesso ? "Fornecedor atualizado com sucesso!" : "Erro ao atualizar fornecedor!";
    }

    // --------------------------- EXCLUIR ---------------------------
    public String excluirFornecedor(int id) {

        if (id <= 0) {
            return "ID inválido!";
        }

        boolean sucesso = persistencia.excluirFornecedor(id);
        return sucesso ? "Fornecedor excluído com sucesso!" : "Erro ao excluir fornecedor!";
    }

    // --------------------------- LISTAR ---------------------------
    public ArrayList<Model_Fornecedor_04> listarFornecedores() {
        return persistencia.listarFornecedores();
    }

    // --------------------------- BUSCAR POR ID ---------------------------
    public Model_Fornecedor_04 buscarFornecedorPorID(int id) {

        if (id <= 0) {
            return null;
        }

        return persistencia.buscarFornecedorPorID(id);
    }
}