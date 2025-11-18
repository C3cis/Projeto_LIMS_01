package Pck_Controller_LIMS;
import Pck_Model_LIMS.Model_Fornecedor_04;
import Pck_Persistencia_LIMS.Persistencia_Fornecedor_04;

import java.util.ArrayList;

public class Controller_Fornecedor_04 {
    private Persistencia_Fornecedor_04 persistencia;

    public Controller_Fornecedor_04() {
        persistencia = new Persistencia_Fornecedor_04();
    }

    // ------------------ SALVAR ------------------
    public boolean salvarFornecedor(String nome, String cnpj, String telefone, String email, String endereco) {
        Model_Fornecedor_04 f = new Model_Fornecedor_04();

        f.setA04_nome_fornecedor(nome);
        f.setA04_cnpj_fornecedor(cnpj);
        f.setA04_telefone_fornecedor(telefone);
        f.setA04_email_fornecedor(email);
        f.setA04_endereco_fornecedor(endereco);

        return persistencia.inserir_fornecedor(f);
    }

    // ------------------ EDITAR ------------------
    public boolean editarFornecedor(int id, String nome, String cnpj, String telefone, String email, String endereco) {
        Model_Fornecedor_04 f = new Model_Fornecedor_04();

        f.setA04_id_fornecedor(id);
        f.setA04_nome_fornecedor(nome);
        f.setA04_cnpj_fornecedor(cnpj);
        f.setA04_telefone_fornecedor(telefone);
        f.setA04_email_fornecedor(email);
        f.setA04_endereco_fornecedor(endereco);

        return persistencia.atualizar_fornecedor(f);
    }

    // ------------------ BUSCAR ------------------
    public Model_Fornecedor_04 buscarFornecedor(int id) {
        return persistencia.buscar_fornecedor(id);
    }

    // ------------------ LISTAR ------------------
    public ArrayList<Model_Fornecedor_04> listarFornecedores() {
        return persistencia.listar_fornecedor();
    }

    // ------------------ EXCLUIR ------------------
    public boolean excluirFornecedor(int id) {
        return persistencia.deletar_fornecedor(id);
    }
}
