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
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Produtos extends JDialog {

    private JPanel contentPane;
    private JButton sairButton, salvarButton, editarButton, excluirButton, buscarButton;

    private JTextField textField2nomeProduto;
    private JTextField textField3ValorProd;

    private JFormattedTextField textField1DataCadastro;
    private JFormattedTextField textField6DataChegada;


    private JComboBox<String> comboBox1Fornecedor;
    private JComboBox<String> comboBox2Status;
    private JComboBox<String> comboBox3NomeProjeto;

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

        aplicarMascaraDatas();
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

    // ------------------------------------------------------------
    // MÁSCARA DE DATA: dd/MM/yyyy
    // ------------------------------------------------------------
    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf1 = new MaskFormatter("##/##/####");
            mf1.setPlaceholderCharacter('_');
            mf1.install(textField1DataCadastro);
            textField1DataCadastro.setFocusLostBehavior(JFormattedTextField.COMMIT);

            MaskFormatter mf2 = new MaskFormatter("##/##/####");
            mf2.setPlaceholderCharacter('_');
            mf2.install(textField6DataChegada);
            textField6DataChegada.setFocusLostBehavior(JFormattedTextField.COMMIT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------
    // CONVERTE dd/MM/yyyy -> java.sql.Date
    // ------------------------------------------------------------
    private Date converterData(String dataTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataUtil = sdf.parse(dataTexto);
            return new java.sql.Date(dataUtil.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy");
            return null;
        }
    }

    // ------------------------------------------------------------
    // CONVERTE sqlDate -> dd/MM/yyyy
    // ------------------------------------------------------------
    private String converterDataParaTela(Date dataSQL) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataSQL);
    }
    private Date converterParaDateSQL(String dataStr) throws Exception {
        if (dataStr == null || dataStr.contains("_")) {
            throw new Exception("A data está incompleta. Preencha no formato dd/MM/yyyy.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        java.util.Date dataUtil = sdf.parse(dataStr);

        return new java.sql.Date(dataUtil.getTime());
    }

    // ------------------------------------------------------------
    // CONFIGURA TABELA
    // ------------------------------------------------------------
    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Nome", "Descrição", "Tipo", "Data Cadastro",
                        "Data Chegada", "Valor", "ID Projeto", "ID Fornecedor"
                }, 0
        );
        table1.setModel(tableModel);
    }

    // ------------------------------------------------------------
    // CARREGA COMBOS
    // ------------------------------------------------------------
    private void carregarCombos() {

        comboBox2Status.removeAllItems();
        comboBox2Status.addItem("CONSUMO");
        comboBox2Status.addItem("PATRIMONIO");
        comboBox2Status.addItem("INSUMO");

        comboBox1Fornecedor.removeAllItems();
        for (Model_Fornecedor_04 f : controllerFornecedor.listarFornecedores()) {
            comboBox1Fornecedor.addItem(f.getA04_id_fornecedor() + " - " + f.getA04_nome());
        }

        comboBox3NomeProjeto.removeAllItems();
        for (Model_Projeto_01 p : controllerProjeto.listar_projeto()) {
            comboBox3NomeProjeto.addItem(p.getA01_id_projeto() + " - " + p.getA01_nome_projeto());
        }
    }

    // ------------------------------------------------------------
    // CARREGA TABELA
    // ------------------------------------------------------------
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

    // ------------------------------------------------------------
    // SALVAR PRODUTO
    // ------------------------------------------------------------
    private void salvarProduto() {
        System.out.println("Cadastro: [" + textField1DataCadastro.getText() + "]");
        System.out.println("Chegada:  [" + textField6DataChegada.getText() + "]");
        try {
            Date dataCadastro = converterParaDateSQL(textField1DataCadastro.getText());
            Date dataChegada = converterParaDateSQL(textField6DataChegada.getText());
            if (dataCadastro == null || dataChegada == null) return;

            boolean ok = controller.inserirProduto(
                    textField2nomeProduto.getText(),
                    editorPane1Descricao.getText(),
                    comboBox2Status.getSelectedItem().toString(),
                    dataCadastro,
                    dataChegada,
                    Double.parseDouble(textField3ValorProd.getText()),
                    Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]),
                    Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0])
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------
    // EDITAR PRODUTO
    // ------------------------------------------------------------
    private void editarProduto() {
        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        try {
            Date dataCadastro = converterData(textField1DataCadastro.getText());
            Date dataChegada = converterData(textField6DataChegada.getText());
            if (dataCadastro == null || dataChegada == null) return;

            boolean ok = controller.atualizarProduto(
                    produtoSelecionado,
                    textField2nomeProduto.getText(),
                    editorPane1Descricao.getText(),
                    comboBox2Status.getSelectedItem().toString(),
                    dataCadastro,
                    dataChegada,
                    Double.parseDouble(textField3ValorProd.getText()),
                    Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]),
                    Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0])
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto atualizado!");
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------
    // EXCLUIR PRODUTO
    // ------------------------------------------------------------
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

    // ------------------------------------------------------------
    // CARREGAR CAMPOS AO CLICAR NA TABELA
    // ------------------------------------------------------------
    private void carregarCamposDaLinha() {
        int row = table1.getSelectedRow();
        if (row == -1) return;

        produtoSelecionado = (int) tableModel.getValueAt(row, 0);

        textField2nomeProduto.setText(tableModel.getValueAt(row, 1).toString());
        editorPane1Descricao.setText(tableModel.getValueAt(row, 2).toString());
        comboBox2Status.setSelectedItem(tableModel.getValueAt(row, 3).toString());

        textField1DataCadastro.setText(converterDataParaTela((Date) tableModel.getValueAt(row, 4)));
        textField6DataChegada.setText(converterDataParaTela((Date) tableModel.getValueAt(row, 5)));

        textField3ValorProd.setText(tableModel.getValueAt(row, 6).toString());

        selecionarItemCombo(comboBox3NomeProjeto, tableModel.getValueAt(row, 7).toString());
        selecionarItemCombo(comboBox1Fornecedor, tableModel.getValueAt(row, 8).toString());
    }

    // ------------------------------------------------------------
    // SELECIONA ITEM CORRETO DO COMBO (ID)
    // ------------------------------------------------------------
    private void selecionarItemCombo(JComboBox<String> combo, String id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(id + " -")) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}
