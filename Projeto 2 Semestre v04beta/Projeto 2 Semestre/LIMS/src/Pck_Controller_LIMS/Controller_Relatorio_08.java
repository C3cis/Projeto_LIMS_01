package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Relatorio_08;
import Pck_Persistencia_LIMS.Persistencia_Relatorio_08;

import java.util.List;

public class Controller_Relatorio_08 {

    private Persistencia_Relatorio_08 persistencia;

    public Controller_Relatorio_08() {
        persistencia = new Persistencia_Relatorio_08();
    }

    public boolean inserir(Model_Relatorio_08 m) throws Exception {
        return persistencia.inserir(m);
    }

    public boolean atualizar(Model_Relatorio_08 m) throws Exception {
        return persistencia.atualizar(m);
    }

    public boolean excluir(int id) throws Exception {
        return persistencia.excluir(id);
    }

    public Model_Relatorio_08 buscar(int id) throws Exception {
        return persistencia.buscar(id);
    }

    public List<Model_Relatorio_08> listar() throws Exception {
        return persistencia.listar();
    }
}