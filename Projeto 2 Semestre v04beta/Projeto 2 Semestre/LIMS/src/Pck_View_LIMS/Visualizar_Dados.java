package Pck_View_LIMS;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Visualizar_Dados extends JDialog {
    private JPanel contentPane;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JTextField textField1Buscar;
    private JButton verDetlhesButton;

    private DefaultTableModel tableModel;


    public Visualizar_Dados() {
        setContentPane(contentPane);
        setTitle("Consulta Geral - Usuário Comum");
        setSize(900, 550);
        setModal(true);
        setLocationRelativeTo(null);

        configurarTabela();
    }

    private void configurarTabela() {

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID", "Projeto", "Produto", "Tipo", "Fornecedor",
                         "Data Chegada", "Setor"
                }, 0
        );

        table1.setModel(tableModel);
        table1.setRowHeight(28);
    }

}
