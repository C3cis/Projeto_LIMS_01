package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import Pck_Controller_LIMS.Controller_Pedido_03;
import Pck_Model_LIMS.Model_Pedido_03;

public class Pedidos extends JDialog {
    private JPanel contentPane;
    private JTextField textField2;       // Data Pedido
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JComboBox comboBox1;         // ID Usuario
    private JComboBox comboBox2;         // Status Pedido
    private JEditorPane editorPane1;     // Observacao

    private JComboBox comboBoxFornecedor;   // (ADICIONADO) ID Fornecedor

    private Controller_Pedido_03 controller = new Controller_Pedido_03();

    public Pedidos() {
        setContentPane(contentPane);
        setModal(true);

        carregarTabela();

        salvarButton.addActionListener(e -> salvar());
        editarButton.addActionListener(e -> editar());
        excluirButton.addActionListener(e -> excluir());
        buscarButton.addActionListener(e -> buscar());
        sairButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // -------------------- SALVAR -----------------------
    private void salvar() {
        boolean ok = controller.salvarPedido(
                textField2.getText(),
                comboBox2.getSelectedItem().toString(),
                editorPane1.getText(),
                Integer.parseInt(comboBox1.getSelectedItem().toString()),
                Integer.parseInt(comboBoxFornecedor.getSelectedItem().toString())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }

    // -------------------- EDITAR -----------------------
    private void editar() {
        int linha = table1.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para editar.");
            return;
        }

        int id = Integer.parseInt(table1.getValueAt(linha, 0).toString());

        boolean ok = controller.editarPedido(
                id,
                textField2.getText(),
                comboBox2.getSelectedItem().toString(),
                editorPane1.getText(),
                Integer.parseInt(comboBox1.getSelectedItem().toString()),
                Integer.parseInt(comboBoxFornecedor.getSelectedItem().toString())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido atualizado!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar.");
        }
    }

    // -------------------- EXCLUIR -----------------------
    private void excluir() {
        int linha = table1.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela.");
            return;
        }

        int id = Integer.parseInt(table1.getValueAt(linha, 0).toString());

        if (controller.excluirPedido(id)) {
            JOptionPane.showMessageDialog(this, "Pedido excluído.");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir.");
        }
    }

    // -------------------- BUSCAR -----------------------
    private void buscar() {
        String idStr = JOptionPane.showInputDialog("Informe o ID do pedido:");
        if (idStr == null) return;

        int id = Integer.parseInt(idStr);
        Model_Pedido_03 p = controller.buscarPedido(id);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Pedido não encontrado.");
            return;
        }

        textField2.setText(String.valueOf(p.getA03_data_pedido()));
        comboBox2.setSelectedItem(p.getA03_status_pedido());
        editorPane1.setText(p.getA03_observacoes());
        comboBox1.setSelectedItem(String.valueOf(p.getA03_id_usuario()));
        comboBoxFornecedor.setSelectedItem(String.valueOf(p.getA03_id_fornecedor()));
    }

    // -------------------- TABELA ------------------------
    private void carregarTabela() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Data");
        model.addColumn("Status");
        model.addColumn("Observação");
        model.addColumn("ID Usuário");
        model.addColumn("ID Fornecedor");

        for (Model_Pedido_03 p : controller.listarPedidos()) {
            model.addRow(new Object[]{
                    p.getA03_id_pedido(),
                    p.getA03_data_pedido(),
                    p.getA03_status_pedido(),
                    p.getA03_observacoes(),
                    p.getA03_id_usuario(),
                    p.getA03_id_fornecedor()
            });
        }

        table1.setModel(model);
    }
}