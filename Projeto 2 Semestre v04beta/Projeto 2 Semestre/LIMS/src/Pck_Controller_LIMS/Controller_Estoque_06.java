package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Estoque_06;
import Pck_Persistencia_LIMS.Persistencia_Estoque_06;

import javax.swing.*;
import java.util.List;

public class Controller_Estoque_06 {

    private Persistencia_Estoque_06 persistencia;

    public Controller_Estoque_06() {
        this.persistencia = new Persistencia_Estoque_06();
    }

    // inserir - devolve boolean para a View exibir mensagens
    public boolean inserirEstoque(Model_Estoque_06 m) {
        try {
            return persistencia.inserirEstoque(m);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // atualizar
    public boolean atualizarEstoque(Model_Estoque_06 m) {
        try {
            return persistencia.atualizarEstoque(m);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // excluir
    public boolean excluirEstoque(int id) {
        try {
            return persistencia.excluirEstoque(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // buscar por id
    public Model_Estoque_06 buscarEstoque(int id) {
        try {
            return persistencia.buscarEstoque(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // listar todos (usado pela View)
    public List<Model_Estoque_06> listarEstoques() {
        try {
            return persistencia.listarEstoques();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}