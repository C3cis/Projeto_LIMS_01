package Pck_View_LIMS;
import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Fornecedor_04;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

        configurarTabela();
        aplicarMascaraDatas();
        carregarCombos();
        carregarTabela();

        salvarButton.addActionListener(e -> salvarProduto());
        editarButton.addActionListener(e -> editarProduto());
        excluirButton.addActionListener(e -> excluirProduto());
        buscarButton.addActionListener(e -> buscarProdutoPorID());
        sairButton.addActionListener(e -> dispose());

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    carregarCamposDaLinha();
                }
            }
        });
    }
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
    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Nome", "Tipo",
                        "Data Cadastro", "Data Chegada",
                        "Valor", "Projeto", "Fornecedor"
                }, 0
        );
        table1.setModel(tableModel);
    }
    private void carregarCombos() {
        comboBox2Status.removeAllItems();
        comboBox2Status.addItem("CONSUMO");
        comboBox2Status.addItem("PATRIMONIO");
        comboBox2Status.addItem("INSUMO");

        comboBox1Fornecedor.removeAllItems();
        List<Model_Fornecedor_04> listaF = controllerFornecedor.listarFornecedores();
        if (listaF != null) {
            for (Model_Fornecedor_04 f : listaF) {
                comboBox1Fornecedor.addItem(f.getA04_id_fornecedor() + " - " + f.getA04_nome());
            }
        }

        comboBox3NomeProjeto.removeAllItems();
        List<Model_Projeto_01> listaP = controllerProjeto.listar_projeto();
        if (listaP != null) {
            for (Model_Projeto_01 p : listaP) {
                comboBox3NomeProjeto.addItem(p.getA01_id_projeto() + " - " + p.getA01_nome_projeto());
            }
        }
    }
    private void carregarTabela() {
        tableModel.setRowCount(0);

        List<Model_Projeto_01> projetos = controllerProjeto.listar_projeto();
        List<Model_Fornecedor_04> fornecedores = controllerFornecedor.listarFornecedores();

        for (Model_Produto_02 p : controller.listarProdutos()) {
            String projetoFormatado = formatarIdNomeProjeto(projetos, p.getA02_id_projeto());
            String fornecedorFormatado = formatarIdNomeFornecedor(fornecedores, p.getA02_id_fornecedor());

            tableModel.addRow(new Object[]{
                    p.getA02_id_produto(),
                    p.getA02_nome_produto(),
                    p.getA02_tipo(),
                    safeDataParaTela(p.getA02_data_cadastro()),
                    safeDataParaTela(p.getA02_data_chegada()),
                    formatarValorBR(p.getA02_valor_unitario()),
                    projetoFormatado,
                    fornecedorFormatado
            });
        }
    }

    private String formatarIdNomeProjeto(List<Model_Projeto_01> projetos, int idProjeto) {
        if (projetos != null) {
            for (Model_Projeto_01 pr : projetos) {
                if (pr.getA01_id_projeto() == idProjeto) {
                    return pr.getA01_id_projeto() + " - " + pr.getA01_nome_projeto();
                }
            }
        }
        return idProjeto + " - ?";
    }

    private String formatarIdNomeFornecedor(List<Model_Fornecedor_04> fornecedores, int idFornecedor) {
        if (fornecedores != null) {
            for (Model_Fornecedor_04 f : fornecedores) {
                if (f.getA04_id_fornecedor() == idFornecedor) {
                    return f.getA04_id_fornecedor() + " - " + f.getA04_nome();
                }
            }
        }
        return idFornecedor + " - ?";
    }

    private String safeDataParaTela(Date d) {
        if (d == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }
    private void salvarProduto() {
        try {
            String nome = textField2nomeProduto.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o nome do produto.");
                return;
            }

            String descricao = editorPane1Descricao.getText();
            String tipo = comboBox2Status.getSelectedItem() == null ? "" : comboBox2Status.getSelectedItem().toString();

            Date dataCadastro = converterParaDateSQL(textField1DataCadastro.getText());
            Date dataChegada = converterParaDateSQL(textField6DataChegada.getText());
            double valor = converterValor(textField3ValorProd.getText());

            int idProjeto = extrairId(comboBox3NomeProjeto.getSelectedItem().toString());
            int idFornecedor = extrairId(comboBox1Fornecedor.getSelectedItem().toString());

            boolean ok = controller.inserirProduto(
                    nome, descricao, tipo, dataCadastro, dataChegada, valor, idProjeto, idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
        }
    }
    private void editarProduto() {
        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        try {
            String nome = textField2nomeProduto.getText().trim();
            String descricao = editorPane1Descricao.getText();
            String tipo = comboBox2Status.getSelectedItem().toString();
            Date dataCadastro = converterParaDateSQL(textField1DataCadastro.getText());
            Date dataChegada = converterParaDateSQL(textField6DataChegada.getText());
            double valor = converterValor(textField3ValorProd.getText());
            int idProjeto = extrairId(comboBox3NomeProjeto.getSelectedItem().toString());
            int idFornecedor = extrairId(comboBox1Fornecedor.getSelectedItem().toString());

            boolean ok = controller.atualizarProduto(
                    produtoSelecionado, nome, descricao, tipo, dataCadastro, dataChegada, valor, idProjeto, idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto atualizado!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar produto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage());
        }
    }
    private void excluirProduto() {
        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (controller.excluirProduto(produtoSelecionado)) {
            JOptionPane.showMessageDialog(null, "Produto excluído.");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir produto.");
        }
    }
    private void carregarCamposDaLinha() {
        int row = table1.getSelectedRow();
        if (row == -1) return;

        produtoSelecionado = (int) tableModel.getValueAt(row, 0);

        textField2nomeProduto.setText(tableModel.getValueAt(row, 1).toString());
        comboBox2Status.setSelectedItem(tableModel.getValueAt(row, 2).toString());
        textField1DataCadastro.setText(tableModel.getValueAt(row, 3).toString());
        textField6DataChegada.setText(tableModel.getValueAt(row, 4).toString());

        String valorStr = tableModel.getValueAt(row, 5).toString().replace("R$", "").trim();
        textField3ValorProd.setText(valorStr);

        selecionarItemCombo(comboBox3NomeProjeto, tableModel.getValueAt(row, 6).toString());
        selecionarItemCombo(comboBox1Fornecedor, tableModel.getValueAt(row, 7).toString());

        // Busca a descrição do produto no controller e preenche
        Model_Produto_02 produto = controller.buscarProduto(produtoSelecionado);
        if (produto != null) {
            editorPane1Descricao.setText(produto.getA02_descricao());
        }
    }
    private void buscarProdutoPorID() {
        String input = JOptionPane.showInputDialog(null, "Digite o ID do produto:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input.trim());
            boolean encontrado = false;

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int idTabela = (int) tableModel.getValueAt(i, 0);
                if (idTabela == id) {
                    table1.setRowSelectionInterval(i, i);
                    table1.scrollRectToVisible(table1.getCellRect(i, 0, true));
                    carregarCamposDaLinha();
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite um ID válido.");
        }
    }
    private Date converterParaDateSQL(String dataStr) {
        try {
            if (dataStr == null) return null;
            dataStr = dataStr.trim();
            if (dataStr.isEmpty() || dataStr.contains("_")) return null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d = sdf.parse(dataStr);
            return new Date(d.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private int extrairId(String item) {
        try {
            return Integer.parseInt(item.split(" - ")[0].trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private double converterValor(String texto) {
        if (texto == null) return -1;
        String t = texto.replace("R$", "").replace(" ", "").trim().replace(".", "").replace(",", ".");
        try {
            return Double.parseDouble(t);
        } catch (Exception e) {
            return -1;
        }
    }

    private String formatarValorBR(double valor) {
        Locale localeBR = new Locale("pt", "BR");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(localeBR);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + df.format(valor);
    }

    private void selecionarItemCombo(JComboBox<String> combo, String valor) {
        if (valor == null) return;
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equals(valor)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}
