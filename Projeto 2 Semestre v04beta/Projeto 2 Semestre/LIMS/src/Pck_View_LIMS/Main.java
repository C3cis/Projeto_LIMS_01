package Pck_View_LIMS;

import java.awt.Toolkit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Login login = new Login();
        login.setUndecorated(true);
        login.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
        login.setLocationRelativeTo(null); // Centraliza na tela
        login.setVisible(true);
    }
}