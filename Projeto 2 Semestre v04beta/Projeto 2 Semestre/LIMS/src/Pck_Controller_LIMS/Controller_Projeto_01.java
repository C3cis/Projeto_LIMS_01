package Pck_Controller_LIMS;

import java.util.ArrayList;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Persistencia_LIMS.Persistencia_Projeto_01;

public class Controller_Projeto_01 {

    private Persistencia_Projeto_01 persistencia = new Persistencia_Projeto_01();

    public boolean inserir_projeto(Model_Projeto_01 p) {
        return persistencia.inserir_projeto(p);
    }

    public boolean atualizar_projeto(Model_Projeto_01 p) {
        return persistencia.atualizar_projeto(p);
    }

    public boolean deletar_projeto(int id) {
        return persistencia.deletar_projeto(id);
    }

    public ArrayList<Model_Projeto_01> listar_projeto() {
        return persistencia.listar_projeto();
    }

    public Model_Projeto_01 buscar_projeto(int id) {
        return persistencia.buscar_projeto(id);
    }
}

