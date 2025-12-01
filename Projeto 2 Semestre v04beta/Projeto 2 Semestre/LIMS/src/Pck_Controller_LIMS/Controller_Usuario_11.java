package Pck_Controller_LIMS;
import java.util.ArrayList;

import Pck_Model_LIMS.Model_Usuario_11;
import Pck_Persistencia_LIMS.Persistencia_Usuario_11;

public class Controller_Usuario_11 {

    private Persistencia_Usuario_11 persistencia = new Persistencia_Usuario_11();

    private String gerarCodigoUsuario(String nome) {

        String[] partes = nome.trim().split(" ");

        String p1 = partes[0].substring(0, 1).toUpperCase();
        String p2 = partes.length > 1 ? partes[1].substring(0, 1).toUpperCase() : "X";

        int numero = (int)(Math.random() * 900) + 100;

        return p1 + p2 + numero;
    }
    public boolean inserir(Model_Usuario_11 u) {

        String codigo = gerarCodigoUsuario(u.getA11_nome());
        u.setA11_codigo_usuario(codigo);

        return persistencia.inserir(u);
    }
    public boolean atualizar(Model_Usuario_11 u) {
        return persistencia.atualizar(u);
    }
    public boolean excluir(int id) {
        return persistencia.excluir(id);
    }
    public Model_Usuario_11 buscar(int id) {
        return persistencia.buscar(id);
    }
    public ArrayList<Model_Usuario_11> listar() {
        return persistencia.listar();
    }
    public Model_Usuario_11 login(String email, String senha) {
        return persistencia.login(email, senha);
    }
}

