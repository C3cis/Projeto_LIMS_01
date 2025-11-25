package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Tabela_Usuario_00;
import Pck_Model_LIMS.Model_Tabela_Usuario_00;

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

    private Controller_Tabela_Usuario_00 controller = new Controller_Tabela_Usuario_00();



    public Visualizar_Dados() {
        setContentPane(contentPane);
        setTitle("Consulta Geral - Usuário Comum");
        setSize(900, 550);
        setModal(true);
        setLocationRelativeTo(null);

        configurarTabela();
        registrarEventos();
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

    private void registrarEventos() {

        buscarButton.addActionListener(e -> carregarTabela(textField1Buscar.getText()));
        sairButton.addActionListener(e -> dispose());
        verDetlhesButton.addActionListener(e -> abrirDetalhes());

        Controller_Tabela_Usuario_00 controller = new Controller_Tabela_Usuario_00();
        controller.atualizarTabelaAutomatica();  // <-- ATUALIZA A TABELA ANTES DE EXIBIR
        carregarTabela("");
    }

    private void buscar() {
        String filtro = textField1Buscar.getText().trim();
        carregarTabela(filtro);
    }

    private void carregarTabela(String filtro) {

        tableModel.setRowCount(0);

        ArrayList<Model_Tabela_Usuario_00> lista = controller.listar(filtro);

        for (Model_Tabela_Usuario_00 m : lista) {

            Icon icone = getIconeStatus(m.getA00_status_produto());

            tableModel.addRow(new Object[]{
                    m.getA00_id_produto(),
                    m.getA00_nome_projeto(),
                    m.getA00_nome_produto(),
                    m.getA00_tipo_produto(),
                    m.getA00_nome_fornecedor(),
                    icone,
                    m.getA00_data_chegada(),
            });
        }
    }

    private Icon getIconeStatus(String status) {

        if (status == null) return null;

        if (status.equalsIgnoreCase("OK"))
            return new ImageIcon(getClass().getResource("/icons/verde.png"));

        if (status.equalsIgnoreCase("Manutenção"))
            return new ImageIcon(getClass().getResource("/icons/amarelo.png"));

        return new ImageIcon(getClass().getResource("/icons/vermelho.png"));
    }

    private void abrirDetalhes() {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) table1.getValueAt(row, 0);

        Detalhes_Usuario tela = new Detalhes_Usuario(id);
        tela.setVisible(true);
    }
}
