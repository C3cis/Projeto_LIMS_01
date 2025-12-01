package Pck_Persistencia_LIMS;

import Pck_Model_LIMS.Model_Fornecedor_04;
import Pck_DAO_LIMS.DAO_Conexao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Persistencia_Fornecedor_04 {

    public boolean salvarFornecedor(Model_Fornecedor_04 fornecedor) {
        try {
            Connection conn = DAO_Conexao.connect();

            CallableStatement stmt = conn.prepareCall("{CALL SP_INSERIR_FORNECEDOR_04(?, ?, ?, ?, ?)}");
            stmt.setString(1, fornecedor.getA04_nome());
            stmt.setString(2, fornecedor.getA04_cnpj());
            stmt.setString(3, fornecedor.getA04_telefone());
            stmt.setString(4, fornecedor.getA04_email());
            stmt.setString(5, fornecedor.getA04_endereco());

            stmt.execute();
            stmt.close();
            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("Erro salvar fornecedor: " + e.getMessage());
            return false;
        }
    }
    public boolean atualizarFornecedor(Model_Fornecedor_04 fornecedor) {
        try {
            Connection conn = DAO_Conexao.connect();

            CallableStatement stmt = conn.prepareCall("{CALL SP_ATUALIZAR_FORNECEDOR_04(?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, fornecedor.getA04_id_fornecedor());
            stmt.setString(2, fornecedor.getA04_nome());
            stmt.setString(3, fornecedor.getA04_cnpj());
            stmt.setString(4, fornecedor.getA04_telefone());
            stmt.setString(5, fornecedor.getA04_email());
            stmt.setString(6, fornecedor.getA04_endereco());

            stmt.execute();
            stmt.close();
            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("Erro atualizar fornecedor: " + e.getMessage());
            return false;
        }
    }
    public boolean excluirFornecedor(int id) {
        try {
            Connection conn = DAO_Conexao.connect();

            CallableStatement stmt = conn.prepareCall("{CALL SP_EXCLUIR_FORNECEDOR_04(?)}");
            stmt.setInt(1, id);

            stmt.execute();
            stmt.close();
            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("Erro excluir fornecedor: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Model_Fornecedor_04> listarFornecedores() {
        ArrayList<Model_Fornecedor_04> lista = new ArrayList<>();

        try {
            Connection conn = DAO_Conexao.connect();

            CallableStatement stmt = conn.prepareCall("{CALL SP_LISTAR_FORNECEDOR_04()}");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Model_Fornecedor_04 f = new Model_Fornecedor_04();

                f.setA04_id_fornecedor(rs.getInt("a04_id_fornecedor"));
                f.setA04_nome(rs.getString("a04_nome"));
                f.setA04_cnpj(rs.getString("a04_cnpj"));
                f.setA04_telefone(rs.getString("a04_telefone"));
                f.setA04_email(rs.getString("a04_email"));
                f.setA04_endereco(rs.getString("a04_endereco"));

                lista.add(f);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro listar fornecedores: " + e.getMessage());
        }

        return lista;
    }
    public Model_Fornecedor_04 buscarFornecedorPorID(int id) {
        Model_Fornecedor_04 f = new Model_Fornecedor_04();

        try {
            Connection conn = DAO_Conexao.connect();

            CallableStatement stmt = conn.prepareCall("{CALL SP_BUSCAR_FORNECEDOR_04(?)}");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                f.setA04_id_fornecedor(rs.getInt("a04_id_fornecedor"));
                f.setA04_nome(rs.getString("a04_nome"));
                f.setA04_cnpj(rs.getString("a04_cnpj"));
                f.setA04_telefone(rs.getString("a04_telefone"));
                f.setA04_email(rs.getString("a04_email"));
                f.setA04_endereco(rs.getString("a04_endereco"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro buscar fornecedor por ID: " + e.getMessage());
        }

        return f;
    }
}
