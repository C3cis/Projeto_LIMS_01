package Pck_Controller_LIMS;

import Pck_Persistencia_LIMS.Persistencia_Projeto_01;


import java.util.ArrayList;

import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Persistencia_LIMS.Persistencia_Projeto_01;

/**
 * Controller responsável por intermediar a View e a Persistência.
 * Mantém apenas regras básicas e delega todas as operações ao pacote de persistência.
 */
public class Controller_Projeto_01 {
    private Persistencia_Projeto_01 persistencia = new Persistencia_Projeto_01();

    public Controller_Projeto_01() {
        persistencia = new Persistencia_Projeto_01();
    }

    // ===========================================================
    // INSERIR
    // ===========================================================
    public boolean inserir_projeto(Model_Projeto_01 projeto) {
        return persistencia.inserir_projeto(projeto);
    }

    // ===========================================================
    // ATUALIZAR
    // ===========================================================
    public boolean atualizar_projeto(Model_Projeto_01 projeto) {
        return persistencia.atualizar_projeto(projeto);
    }

    // ===========================================================
    // DELETAR
    // ===========================================================
    public boolean deletar_projeto(int idProjeto) {
        return persistencia.deletar_projeto(idProjeto);
    }

    // ===========================================================
    // LISTAR TODOS
    // ===========================================================
    public ArrayList<Model_Projeto_01> listar_projeto() {
        return persistencia.listar_projeto();
    }

    // ===========================================================
    // BUSCAR POR ID
    // ===========================================================
    public Model_Projeto_01 buscar_projeto(int idProjeto) {
        return persistencia.buscar_projeto(idProjeto);
    }
}