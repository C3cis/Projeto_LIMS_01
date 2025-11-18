package Pck_Controller_LIMS;
import java.util.ArrayList;
import Pck_Model_LIMS.Model_Manutencao_05;
import Pck_Persistencia_LIMS.Persistencia_Manutencao_05;


public class Controller_Manutencao_05 {
    private Persistencia_Manutencao_05 persistencia = new Persistencia_Manutencao_05();

    public boolean inserir_manutencao(Model_Manutencao_05 m) {
        return persistencia.inserir_manutencao(m);
    }

    public boolean atualizar_manutencao(Model_Manutencao_05 m) {
        return persistencia.atualizar_manutencao(m);
    }

    public boolean deletar_manutencao(int id) {
        return persistencia.deletar_manutencao(id);
    }

    public Model_Manutencao_05 buscar_manutencao(int id) {
        return persistencia.buscar_manutencao(id);
    }

    public ArrayList<Model_Manutencao_05> listar_manutencao() {
        return persistencia.listar_manutencao();
    }
}


