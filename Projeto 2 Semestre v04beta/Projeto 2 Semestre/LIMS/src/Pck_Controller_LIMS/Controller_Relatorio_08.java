package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Relatorio_08;
import Pck_Persistencia_LIMS.Persistencia_Relatorio_08;

import java.util.ArrayList;
import java.util.List;

public class Controller_Relatorio_08 {

    private Persistencia_Relatorio_08 persistencia;

        public Controller_Relatorio_08() {
            persistencia = new Persistencia_Relatorio_08();
        }

        public boolean inserir(Model_Relatorio_08 m) {
            try {
                return persistencia.inserir(m);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean atualizar(Model_Relatorio_08 m) {
            try {
                return persistencia.atualizar(m);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean excluir(int id) {
            try {
                return persistencia.excluir(id);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public Model_Relatorio_08 buscar(int id) {
            try {
                return persistencia.buscar(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public List<Model_Relatorio_08> listar() {
            try {
                return persistencia.listar();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }
