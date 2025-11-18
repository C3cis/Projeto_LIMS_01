package Pck_Controller_LIMS;

import Pck_Model_LIMS.Model_Pedido_03;
import Pck_Persistencia_LIMS.Persistencia_Pedido_03;

import java.sql.Date;
import java.util.ArrayList;

public class Controller_Pedido_03 {

        private Persistencia_Pedido_03 persistencia = new Persistencia_Pedido_03();

        public boolean salvarPedido(String dataPedido, String status, String observacao,
                                    int idUsuario, int idFornecedor) {

            try {
                Model_Pedido_03 p = new Model_Pedido_03();

                p.setA03_data_pedido(Date.valueOf(dataPedido));
                p.setA03_status_pedido(status);
                p.setA03_observacoes(observacao);
                p.setA03_id_usuario(idUsuario);
                p.setA03_id_fornecedor(idFornecedor);

                return persistencia.inserir_pedido(p);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        public boolean editarPedido(int idPedido, String dataPedido, String status,
                                    String observacao, int idUsuario, int idFornecedor) {

            try {
                Model_Pedido_03 p = new Model_Pedido_03();

                p.setA03_id_pedido(idPedido);
                p.setA03_data_pedido(Date.valueOf(dataPedido));
                p.setA03_status_pedido(status);
                p.setA03_observacoes(observacao);
                p.setA03_id_usuario(idUsuario);
                p.setA03_id_fornecedor(idFornecedor);

                return persistencia.atualizar_pedido(p);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        public boolean excluirPedido(int idPedido) {
            return persistencia.deletar_pedido(idPedido);
        }


        public Model_Pedido_03 buscarPedido(int idPedido) {
            return persistencia.buscar_pedido(idPedido);
        }


        public ArrayList<Model_Pedido_03> listarPedidos() {
            return persistencia.listar_pedido();
        }

    }


