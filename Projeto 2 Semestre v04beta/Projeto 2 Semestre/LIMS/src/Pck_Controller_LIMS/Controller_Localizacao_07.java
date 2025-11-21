package Pck_Controller_LIMS;



import Pck_Model_LIMS.Model_Localizacao_07;
import Pck_Persistencia_LIMS.Persistencia_Localizacao_07;

import java.util.List;

/**
 * Controller alinhado aos nomes e métodos da Persistencia_Localizacao_07
 */
public class Controller_Localizacao_07 {

    private Persistencia_Localizacao_07 persistencia;

    public Controller_Localizacao_07() {
        this.persistencia = new Persistencia_Localizacao_07();
    }

    public boolean inserir(Model_Localizacao_07 m) {
        try {
            return persistencia.inserirLocalizacao(m);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Model_Localizacao_07 m) {
        try {
            return persistencia.atualizarLocalizacao(m);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        try {
            return persistencia.excluirLocalizacao(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Model_Localizacao_07 buscar(int id) {
        try {
            return persistencia.buscarLocalizacao(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Model_Localizacao_07> listar() {
        try {
            return persistencia.listarLocalizacoes();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}