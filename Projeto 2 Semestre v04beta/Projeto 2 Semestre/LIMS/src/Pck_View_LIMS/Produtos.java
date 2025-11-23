package Pck_View_LIMS;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_DAO_LIMS.DAO_Conexao;

public class Produtos extends JDialog {

    private JPanel contentPane;
    private JButton sairButton;
    private JTextField textField2nomeProduto;   // Nome
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;

    private JTextField textField1DataCadastro;   // Data cadastro
    private JTextField textField3ValorProd;  // Data chegada
    private JTextField textField6DataChegada;   // Valor

    private JComboBox comboBox1Fornecedor;     // Fornecedor
    private JComboBox comboBox2Status;// Status/ tipo
    private JComboBox comboBox3NomeProjeto; //colocar o nome do projeto vinculado

    private JEditorPane editorPane1;  // Descrição
    private JEditorPane editorPane2;  // Reserva

    private DefaultTableModel tableModel;
    private Controller_Produto_02 controller;

    // ============================================================
    public Produtos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Produtos");

        try {
            Connection c = DAO_Conexao.connect();
            controller = new Controller_Produto_02(c);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
        }

        inicializarTabela();
        carregarProjetos();
        carregarFornecedores();
        carregarStatusProduto();
        preencherTabela();
        adicionarEventoCliqueTabela();

        salvarButton.addActionListener(e -> onSalvar());
        editarButton.addActionListener(e -> onEditar());
        excluirButton.addActionListener(e -> onExcluir());
        buscarButton.addActionListener(e -> onBuscar());
        sairButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }

    // ============================================================
    // COMBOS
    // ============================================================
    private void carregarProjetos() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT A01_ID_PROJETO, A01_NOME_PROJETO FROM PROJETO_01");
             ResultSet rs = ps.executeQuery()) {

            comboBox1Fornecedor.removeAllItems();
            while (rs.next()) {
                comboBox1Fornecedor.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar projetos: " + e.getMessage());
        }
    }

    private void carregarFornecedores() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT A04_ID_FORNECEDOR, A04_NOME FROM FORNECEDOR_04");
             ResultSet rs = ps.executeQuery()) {

            comboBox1Fornecedor.removeAllItems();
            while (rs.next()) {
                comboBox1Fornecedor.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    private void carregarStatusProduto() {
        comboBox2Status.removeAllItems();
        comboBox2Status.addItem("ATIVO");
        comboBox2Status.addItem("EM PRODUÇÃO");
        comboBox2Status.addItem("EM ESTOQUE");
        comboBox2Status.addItem("DESCONTINUADO");
    }

    // ============================================================
    // TABELA
    // ============================================================
    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Status", "Cadastro", "Chegada", "Valor", "Projeto", "Fornecedor"},
                0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table1.setModel(tableModel);
    }

    private void preencherTabela() {
        try {
            tableModel.setRowCount(0);
            ArrayList<Model_Produto_02> lista = controller.listarProdutos();

            for (Model_Produto_02 p : lista) {
                tableModel.addRow(new Object[]{
                        p.getA02_id_produto(),
                        p.getA02_nome_produto(),
                        p.getA02_tipo(),
                        p.getA02_data_cadastro(),
                        p.getA02_data_chegada(),
                        p.getA02_valor_unitario(),
                        p.getA02_id_projeto(),
                        p.getA02_id_fornecedor()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    private void adicionarEventoCliqueTabela() {
        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                if (row < 0) return;

                try {
                    int id = (int) tableModel.getValueAt(row, 0);
                    Model_Produto_02 p = controller.buscarProduto(id);
                    preencherCampos(p);
                } catch (Exception ignored) { }
            }
        });
    }

    // ============================================================
    // SALVAR
    // ============================================================
    private void onSalvar() {
        try {
            String nome = textField2nomeProduto.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome obrigatório.");
                return;
            }

            // Datas corretas
            java.sql.Date dataCad = java.sql.Date.valueOf(textField1DataCadastro.getText().trim());

            String chegadaTxt = textField6DataChegada.getText().trim();
            java.sql.Date dataChegada = chegadaTxt.isEmpty()
                    ? dataCad
                    : java.sql.Date.valueOf(chegadaTxt);

            // Valor correto (textField3ValorProd)
            double valor = Double.parseDouble(textField3ValorProd.getText().trim());

            // ComboBoxes corrigidos
            int idFornecedor = Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0]);
            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);
            String status = comboBox2Status.getSelectedItem().toString();

            boolean ok = controller.inserirProduto(
                    nome,
                    editorPane1.getText(),
                    status,
                    new java.util.Date(dataCad.getTime()),
                    new java.util.Date(dataChegada.getTime()),
                    valor,
                    idProjeto,
                    idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                preencherTabela();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
    }
    private void onExcluir() {
        int linha = table1.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);

        if (JOptionPane.showConfirmDialog(null, "Excluir produto?", "Confirmar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            Connection con = DAO_Conexao.connect();
            Controller_Produto_02 controller = new Controller_Produto_02(con);

            if (controller.excluirProduto(id)) {
                JOptionPane.showMessageDialog(null, "Excluído!");
                preencherTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao excluir.");
            }
        }
    }
    // ============================================================
    // EDITAR
    // ============================================================
    private void onEditar() {
        try {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para editar.");
                return;
            }

            int id = Integer.parseInt(table1.getValueAt(row, 0).toString());

            String nome = textField2nomeProduto.getText().trim();
            java.sql.Date dataCad = java.sql.Date.valueOf(textField1DataCadastro.getText().trim());

            String chegadaTxt = textField6DataChegada.getText().trim();
            java.sql.Date dataChegada = chegadaTxt.isEmpty() ? dataCad : java.sql.Date.valueOf(chegadaTxt);

            double valor = Double.parseDouble(textField3ValorProd.getText().trim());

            int idFornecedor = Integer.parseInt(comboBox1Fornecedor.getSelectedItem().toString().split(" - ")[0]);
            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);
            String status = comboBox2Status.getSelectedItem().toString();

            boolean ok = controller.atualizarProduto(
                    id,
                    nome,
                    editorPane1.getText(),
                    status,
                    new java.util.Date(dataCad.getTime()),
                    new java.util.Date(dataChegada.getTime()),
                    valor,
                    idProjeto,
                    idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Editado com sucesso!");
                preencherTabela();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
        }
    }
    // ============================================================
    // BUSCAR
    // ============================================================
    private void onBuscar() {
        try {
            String s = JOptionPane.showInputDialog("ID do produto:");
            if (s == null || s.trim().isEmpty()) return;

            int id = Integer.parseInt(s);
            Model_Produto_02 p = controller.buscarProduto(id);

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Não encontrado.");
                return;
            }

            preencherCampos(p);
            selecionarNaTabela(id);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void selecionarNaTabela(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((int) tableModel.getValueAt(i, 0) == id) {
                table1.setRowSelectionInterval(i, i);
                table1.scrollRectToVisible(table1.getCellRect(i, 0, true));
                break;
            }
        }
    }

    // ============================================================
    // PREENCHER CAMPOS
    // ============================================================
    private void preencherCampos(Model_Produto_02 p) {
        textField2nomeProduto.setText(p.getA02_nome_produto());
        editorPane1.setText(p.getA02_descricao());

        textField1DataCadastro.setText(String.valueOf(p.getA02_data_cadastro()));
        textField3ValorProd.setText(String.valueOf(p.getA02_data_chegada()));
        textField6DataChegada.setText(String.valueOf(p.getA02_valor_unitario()));

        selecionarItem(comboBox3NomeProjeto, p.getA02_id_projeto());
        selecionarItem(comboBox1Fornecedor, p.getA02_id_fornecedor());

        comboBox2Status.setSelectedItem(p.getA02_tipo());
    }

    private void selecionarItem(JComboBox combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i).toString();
            if (item.startsWith(id + " -")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    // ============================================================
    private void limparCampos() {
        textField2nomeProduto.setText("");
        textField1DataCadastro.setText("");
        textField3ValorProd.setText("");
        textField6DataChegada.setText("");
        editorPane1.setText("");
    }
}