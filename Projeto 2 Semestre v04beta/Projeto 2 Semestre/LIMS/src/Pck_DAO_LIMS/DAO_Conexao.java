package Pck_DAO_LIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO_Conexao {
    /* 
    private static final String URL = "jdbc:mysql://localhost:3306/db_LIMS";
    private static final String USUARIO = "adm";
    private static final String SENHA = "1234";

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
    }*/

}
