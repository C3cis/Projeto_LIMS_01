package Pck_Controller_LIMS;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Tabela_Usuario_00;
import Pck_Persistencia_LIMS.Persistencia_Tabela_Usuario_00;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;

public class Controller_Tabela_Usuario_00 {

    Persistencia_Tabela_Usuario_00 persist = new Persistencia_Tabela_Usuario_00();

    public ArrayList<Model_Tabela_Usuario_00> listar(String filtro) {
        return persist.listar_tabela_usuario_00(filtro);
    }

    public Model_Tabela_Usuario_00 buscar_detalhes(int id) {
        return persist.buscar_detalhes(id);
    }

    public void atualizarTabelaAutomatica() {
        try {
            Connection con = DAO_Conexao.connect();
            CallableStatement cs = con.prepareCall("{CALL SP_POPULAR_TABELA_USUARIO_00()}");
            cs.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
