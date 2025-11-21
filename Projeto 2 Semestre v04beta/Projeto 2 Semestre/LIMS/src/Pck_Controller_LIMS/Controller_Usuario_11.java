package Pck_Controller_LIMS;
import java.util.ArrayList;

import Pck_Model_LIMS.Model_Usuario_11;
import Pck_Persistencia_LIMS.Persistencia_Usuario_11;

public class Controller_Usuario_11 {

    private Persistencia_Usuario_11 persistencia = new Persistencia_Usuario_11();

    // ===========================================================
    // GERAR CÓDIGO
    // ===========================================================
    private String gerarCodigoUsuario(String nome) {

        String[] partes = nome.trim().split(" ");

        String p1 = partes[0].substring(0, 1).toUpperCase();
        String p2 = partes.length > 1 ? partes[1].substring(0, 1).toUpperCase() : "X";

        int numero = (int)(Math.random() * 900) + 100;

        return p1 + p2 + numero;
    }

    // ===========================================================
    // INSERIR
    // ===========================================================
    public boolean inserir(Model_Usuario_11 u) {

        String codigo = gerarCodigoUsuario(u.getA11_nome());
        u.setA11_codigo_usuario(codigo);

        return persistencia.inserir(u);
    }

    // ===========================================================
    // ATUALIZAR
    // ===========================================================
    public boolean atualizar(Model_Usuario_11 u) {
        return persistencia.atualizar(u);
    }

    // ===========================================================
    // EXCLUIR
    // ===========================================================
    public boolean excluir(int id) {
        return persistencia.excluir(id);
    }

    // ===========================================================
    // BUSCAR
    // ===========================================================
    public Model_Usuario_11 buscar(int id) {
        return persistencia.buscar(id);
    }

    // ===========================================================
    // LISTAR
    // ===========================================================
    public ArrayList<Model_Usuario_11> listar() {
        return persistencia.listar();
    }

    // ===========================================================
    // LOGIN
    // ===========================================================
    public Model_Usuario_11 login(String email, String senha) {
        return persistencia.login(email, senha);
    }
}

