package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Fornecedor_04;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Produtos extends JDialog {

    private JPanel contentPane;
    private JButton sairButton;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;

    private JTextField textField2nomeProduto;
    private JTextField textField1DataCadastro;
    private JTextField textField6DataChegada;
    private JTextField textField3ValorProd;

    private JComboBox comboBox1Fornecedor;
    private JComboBox comboBox2Status; // TIPO DO PRODUTO
    private JComboBox comboBox3NomeProjeto;

    private JEditorPane editorPane1Descricao;
    private JTable table1;
    private DefaultTableModel tableModel;

    private Controller_Produto_02 controller;
    private Controller_Projeto_01 controllerProjeto;
    private Controller_Fornecedor_04 controllerFornecedor;

    private int produtoSelecionado = -1;

    public Produtos() {

        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Produtos");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        controller = new Controller_Produto_02(DAO_Conexao.connect());
        controllerFornecedor = new Controller_Fornecedor_04();
        controllerProjeto = new Controller_Projeto_01();

        configurarTabela();
        carregarCombos();
        carregarTabela();

        salvarButton.addActionListener(e -> salvarProduto());
        editarButton.addActionListener(e -> editarProduto());
        excluirButton.addActionListener(e -> excluirProduto());
        buscarButton.addActionListener(e -> carregarTabela());
        sairButton.addActionListener(e -> dispose());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarCamposDaLinha();
            }
        });
    }

    // ---------------------------
    // CONFIGURAÇÃO DA TABELA
    // ---------------------------
    private void configurarTabela() {

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Nome", "Descrição", "Tipo",
                        "Data Cadastro", "Data Chegada",
                        "Valor", "ID Projeto", "ID Fornecedor"
                }, 0
        );

        table1.setModel(tableModel);
    }

    // ---------------------------
    // CARREGAR COMBOS
    // ---------------------------
    private void carregarCombos() {

        // tipo do produto
        comboBox2Status.removeAllItems();
        comboBox2Status.addItem("CONSUMO");
        comboBox2Status.addItem("PATRIMONIO");
        comboBox2Status.addItem("INSUMO");

        // Fornecedor
        comboBox1Fornecedor.removeAllItems();
        for (Model_Fornecedor_04 f : controllerFornecedor.listarFornecedores()) {
            comboBox1Fornecedor.addItem(f.getA04_id_fornecedor() + " - " + f.getA04_nome());
        }

        // Projeto
        comboBox3NomeProjeto.removeAllItems();
        for (Model_Projeto_01 p : controllerProjeto.listar_projeto()) {
            comboBox3NomeProjeto.addItem(p.getA01_id_projeto() + " - " + p.getA01_nome_projeto());
        }
    }

    // ---------------------------
    // CARREGAR TABELA
    // ---------------------------
    private void carregarTabela() {

        tableModel.setRowCount(0);

        for (Model_Produto_02 p : controller.listarProdutos()) {
            tableModel.addRow(new Object[]{
                    p.getA02_id_produto(),
                    p.getA02_nome_produto(),
                    p.getA02_descricao(),
                    p.getA02_tipo(),
                    p.getA02_data_cadastro(),
                    p.getA02_data_chegada(),
                    p.getA02_valor_unitario(),
                    p.getA02_id_projeto(),
                    p.getA02_id_fornecedor()
            });
        }
    }

    // ---------------------------
    // SALVAR PRODUTO
    // ---------------------------
    private void salvarProduto() {
        try {

            String nome = textField2nomeProduto.getText();
            String descricao = editorPane1Descricao.getText();
            String tipo = comboBox2Status.getSelectedItem().toString();

            Date dataCadastro = Date.valueOf(textField1DataCadastro.getText());
            Date dataChegada = Date.valueOf(textField6DataChegada.getText());

            double valor = Double.parseDouble(textField3ValorProd.getText());

            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);
            int idFornecedor = Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0]);

            boolean ok = controller.inserirProduto(
                    nome, descricao, tipo, dataCadastro, dataChegada, valor, idProjeto, idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // ---------------------------
    // EDITAR PRODUTO
    // ---------------------------
    private void editarProduto() {

        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela.");
            return;
        }

        try {
            String nome = textField2nomeProduto.getText();
            String descricao = editorPane1Descricao.getText();
            String tipo = comboBox2Status.getSelectedItem().toString();

            Date dataCadastro = Date.valueOf(textField1DataCadastro.getText());
            Date dataChegada = Date.valueOf(textField6DataChegada.getText());

            double valor = Double.parseDouble(textField3ValorProd.getText());

            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);
            int idFornecedor = Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0]);

            boolean ok = controller.atualizarProduto(
                    produtoSelecionado, nome, descricao, tipo,
                    dataCadastro, dataChegada, valor,
                    idProjeto, idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto atualizado!");
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // ---------------------------
    // EXCLUIR PRODUTO
    // ---------------------------
    private void excluirProduto() {

        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        if (controller.excluirProduto(produtoSelecionado)) {
            JOptionPane.showMessageDialog(null, "Produto excluído.");
            carregarTabela();
        }
    }

    // ---------------------------
    // CARREGAR CAMPOS AO CLICAR NA TABELA
    // ---------------------------
    private void carregarCamposDaLinha() {

        int row = table1.getSelectedRow();
        if (row == -1) return;

        produtoSelecionado = (int) tableModel.getValueAt(row, 0);

        textField2nomeProduto.setText(tableModel.getValueAt(row, 1).toString());
        editorPane1Descricao.setText(tableModel.getValueAt(row, 2).toString());
        comboBox2Status.setSelectedItem(tableModel.getValueAt(row, 3).toString());

        textField1DataCadastro.setText(tableModel.getValueAt(row, 4).toString());
        textField6DataChegada.setText(tableModel.getValueAt(row, 5).toString());

        textField3ValorProd.setText(tableModel.getValueAt(row, 6).toString());

        comboBox3NomeProjeto.setSelectedItem(
                tableModel.getValueAt(row, 7).toString() + " - ?"
        );

        comboBox1Fornecedor.setSelectedItem(
                tableModel.getValueAt(row, 8).toString() + " - ?"
        );
    }
}
