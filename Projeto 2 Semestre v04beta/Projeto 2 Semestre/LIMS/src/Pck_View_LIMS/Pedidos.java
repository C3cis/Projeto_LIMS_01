package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_Controller_LIMS.Controller_Pedido_03;
import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Model_LIMS.Model_Fornecedor_04;
import Pck_Model_LIMS.Model_Pedido_03;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Usuario_11;

public class Pedidos extends JDialog {
    private JPanel contentPane;
    private JFormattedTextField textField2Data_pedido;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1Geral;
    private JButton sairButton;
    private JComboBox comboBox1Status_pedido;
    private JComboBox comboBox2ID_USUARIO;
    private JEditorPane editorPane1;
    private JComboBox comboBox3ID_Fornedor;
    private Controller_Pedido_03 controller;
    private Controller_Usuario_11 controllerUsuario;
    private Controller_Fornecedor_04 controllerFornecedor;

    private DefaultTableModel tableModel;
    public Pedidos() {

        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Pedidos");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        controller = new Controller_Pedido_03();
        controllerUsuario = new Controller_Usuario_11();
        controllerFornecedor = new Controller_Fornecedor_04();

        configurarTabela();
        carregarCombos();
        carregarTabela();
        aplicarMascaraDatas();

        salvarButton.addActionListener(e -> salvarPedido());
        editarButton.addActionListener(e -> editarPedido());
        excluirButton.addActionListener(e -> excluirPedido());
        buscarButton.addActionListener(e -> buscarPedido());
        sairButton.addActionListener(e -> dispose());

        table1Geral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    carregarDadosParaEdicao();
                }
            }
        });
    }
    private void configurarTabela() {

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Data", "Status", "Usuário", "Fornecedor"},
                0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1Geral.setModel(tableModel);

        table1Geral.getColumnModel().getColumn(0).setPreferredWidth(40);
        table1Geral.getColumnModel().getColumn(1).setPreferredWidth(100);
        table1Geral.getColumnModel().getColumn(2).setPreferredWidth(120);
        table1Geral.getColumnModel().getColumn(3).setPreferredWidth(150);
        table1Geral.getColumnModel().getColumn(4).setPreferredWidth(150);
    }
    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            mf.install(textField2Data_pedido);  // Campo de data do pedido
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Date converterParaDateSQL(String dataStr) {
        try {
            if (dataStr == null) return null;
            dataStr = dataStr.trim();
            if (dataStr.isEmpty() || dataStr.contains("_")) return null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date d = sdf.parse(dataStr);
            return new Date(d.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private String converterDataParaTela(Date dataSQL) {
        if (dataSQL == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(dataSQL);
    }
    private void carregarCombos() {

        comboBox2ID_USUARIO.removeAllItems();
        comboBox3ID_Fornedor.removeAllItems();

        // Usuários
        for (Model_Usuario_11 u : controllerUsuario.listar()) {
            comboBox2ID_USUARIO.addItem(u.getA11_id_usuario() + " - " + u.getA11_nome());
        }

        // Fornecedores
        for (Model_Fornecedor_04 f : controllerFornecedor.listarFornecedores()) {
            comboBox3ID_Fornedor.addItem(f.getA04_id_fornecedor() + " - " + f.getA04_nome());
        }

        // Status
        comboBox1Status_pedido.removeAllItems();
        comboBox1Status_pedido.addItem("PENDENTE");
        comboBox1Status_pedido.addItem("EM PROCESSAMENTO");
        comboBox1Status_pedido.addItem("CONCLUÍDO");
        comboBox1Status_pedido.addItem("CANCELADO");
    }
    private void carregarTabela() {

        tableModel.setRowCount(0);

        for (Model_Pedido_03 p : controller.listarPedidos()) {

            // Buscar usuário
            Model_Usuario_11 user = controllerUsuario.buscar(p.getA03_id_usuario());
            String usuarioTexto = p.getA03_id_usuario() + " - " + user.getA11_nome();

            // Buscar fornecedor
            Model_Fornecedor_04 forn = controllerFornecedor.buscarFornecedorPorID(p.getA03_id_fornecedor());
            String fornecedorTexto = p.getA03_id_fornecedor() + " - " + forn.getA04_nome();

            tableModel.addRow(new Object[]{
                    p.getA03_id_pedido(),
                    converterDataParaTela(p.getA03_data_pedido()), // <- dd/MM/yyyy
                    p.getA03_status_pedido(),
                    usuarioTexto,
                    fornecedorTexto
            });
        }
    }
    private void salvarPedido() {

        try {
            Model_Pedido_03 p = new Model_Pedido_03();

            Date dataPedido = converterParaDateSQL(textField2Data_pedido.getText());
            if (dataPedido == null) {
                JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy.");
                return;
            }
            p.setA03_data_pedido(dataPedido);            p.setA03_status_pedido(comboBox1Status_pedido.getSelectedItem().toString());
            p.setA03_observacoes(editorPane1.getText());

            String usuarioSelect = comboBox2ID_USUARIO.getSelectedItem().toString();
            p.setA03_id_usuario(Integer.parseInt(usuarioSelect.split(" - ")[0]));

            String fornecedorSelect = comboBox3ID_Fornedor.getSelectedItem().toString();
            p.setA03_id_fornecedor(Integer.parseInt(fornecedorSelect.split(" - ")[0]));

            boolean sucesso = controller.salvarPedido(
                    p.getA03_data_pedido(),
                    p.getA03_status_pedido(),
                    p.getA03_observacoes(),
                    p.getA03_id_usuario(),
                    p.getA03_id_fornecedor()
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Pedido salvo com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
    private void editarPedido() {

        int row = table1Geral.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido para editar.");
            return;
        }

        try {
            Model_Pedido_03 p = new Model_Pedido_03();

            p.setA03_id_pedido((int) tableModel.getValueAt(row, 0));
            p.setA03_data_pedido(Date.valueOf(textField2Data_pedido.getText()));
            p.setA03_status_pedido(comboBox1Status_pedido.getSelectedItem().toString());
            p.setA03_observacoes(editorPane1.getText());

            String usuarioSelect = comboBox2ID_USUARIO.getSelectedItem().toString();
            p.setA03_id_usuario(Integer.parseInt(usuarioSelect.split(" - ")[0]));

            String fornecedorSelect = comboBox3ID_Fornedor.getSelectedItem().toString();
            p.setA03_id_fornecedor(Integer.parseInt(fornecedorSelect.split(" - ")[0]));

            boolean sucesso = controller.editarPedido(
                    p.getA03_id_pedido(),
                    String.valueOf(p.getA03_data_pedido()),
                    p.getA03_status_pedido(),
                    p.getA03_observacoes(),
                    p.getA03_id_usuario(),
                    p.getA03_id_fornecedor()
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Pedido atualizado!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void excluirPedido() {

        int row = table1Geral.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido para excluir.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        if (controller.excluirPedido(id)) {
            JOptionPane.showMessageDialog(null, "Pedido excluído!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir.");
        }
    }
    private void buscarPedido() {

        String input = JOptionPane.showInputDialog("Digite o ID do pedido:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input);
            Model_Pedido_03 p = controller.buscarPedido(id);

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Pedido não encontrado.");
                return;
            }

            textField2Data_pedido.setText(converterDataParaTela(p.getA03_data_pedido()));
            comboBox1Status_pedido.setSelectedItem(p.getA03_status_pedido());
            editorPane1.setText(p.getA03_observacoes());

            // Seleciona usuário
            for (int i = 0; i < comboBox2ID_USUARIO.getItemCount(); i++) {
                if (comboBox2ID_USUARIO.getItemAt(i).toString().startsWith(p.getA03_id_usuario() + " -")) {
                    comboBox2ID_USUARIO.setSelectedIndex(i);
                    break;
                }
            }

            // Seleciona fornecedor
            for (int i = 0; i < comboBox3ID_Fornedor.getItemCount(); i++) {
                if (comboBox3ID_Fornedor.getItemAt(i).toString().startsWith(p.getA03_id_fornecedor() + " -")) {
                    comboBox3ID_Fornedor.setSelectedIndex(i);
                    break;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
    private void carregarDadosParaEdicao() {

        int row = table1Geral.getSelectedRow();
        if (row == -1) return;

        int id = (int) tableModel.getValueAt(row, 0);
        Model_Pedido_03 p = controller.buscarPedido(id);

        if (p == null) return;

        textField2Data_pedido.setText(converterDataParaTela(p.getA03_data_pedido()));
        comboBox1Status_pedido.setSelectedItem(p.getA03_status_pedido());
        editorPane1.setText(p.getA03_observacoes());

        // Usuário
        for (int i = 0; i < comboBox2ID_USUARIO.getItemCount(); i++) {
            if (comboBox2ID_USUARIO.getItemAt(i).toString().startsWith(p.getA03_id_usuario() + " -")) {
                comboBox2ID_USUARIO.setSelectedIndex(i);
                break;
            }
        }

        // Fornecedor
        for (int i = 0; i < comboBox3ID_Fornedor.getItemCount(); i++) {
            if (comboBox3ID_Fornedor.getItemAt(i).toString().startsWith(p.getA03_id_fornecedor() + " -")) {
                comboBox3ID_Fornedor.setSelectedIndex(i);
                break;
            }
        }
    }
    }
