package Pck_DAO_LIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO_Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/db_LIMS";
    private static final String USUARIO = "root";
    private static final String SENHA = "011MYSQL";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("driver MYSQL não encontrado");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco de dados");
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        Connection conexao = connect();
        if (conexao != null) {
            System.out.println("🎉 Teste de conexão bem-sucedido!");
            try {
                conexao.close();
                System.out.println("🔒 Conexão encerrada com sucesso.");
            } catch (SQLException e) {
                System.out.println("⚠️ Erro ao fechar a conexão.");
                e.printStackTrace();
            }
        } else {
            System.out.println("🚫 Falha ao conectar ao banco de dados.");
        }
    }
}
