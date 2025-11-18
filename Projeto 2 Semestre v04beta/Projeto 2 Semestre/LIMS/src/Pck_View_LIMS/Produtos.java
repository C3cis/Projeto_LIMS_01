package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_DAO_LIMS.DAO_Conexao;

public class Produtos extends JDialog {
    private JPanel contentPane;
    private JButton sairButton;
    private JTextField textField2;       // Nome do produto
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JTextField textField1;       // Data cadastro (AAAA-MM-DD)
    private JComboBox comboBox1;         // ComboBox Projeto (ID - Nome)  <-- carregado de PROJETO_01
    private JTextField textField6;       // Valor unitário
    private JComboBox comboBox2;         // ComboBox Fornecedor (ID - Nome) <-- carregado de FORNECEDOR_04
    private JEditorPane editorPane1;     // Descrição
    private JEditorPane editorPane2;     // (não usado / reserva)
    private JTextField textField3;       // Data chegada (AAAA-MM-DD) - adicionado se faltar no .form

    private DefaultTableModel tableModel;
    private Controller_Produto_02 controller;

    public Produtos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Produtos");

        // garantir componentes não-nulos caso o .form não tenha algum nome (precaução)
        if (comboBox1 == null) comboBox1 = new JComboBox();
        if (comboBox2 == null) comboBox2 = new JComboBox();
        if (table1 == null) table1 = new JTable();

        // inicializar controller com conexão
        try {
            Connection c = DAO_Conexao.connect();
            controller = new Controller_Produto_02(c);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
            controller = null;
        }

        carregarProjetos();
        carregarFornecedores();
        carregarTipos(); // tipos fixos (opcional)
        inicializarTabela();
        preencherTabela();

        // Ações dos botões
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSalvar();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEditar();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExcluir();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBuscar();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // Fechar janela no X
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // ESC fecha a janela
        contentPane.registerKeyboardAction(new ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   onCancel();
                                               }
                                           }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // ======== carregar combo de projetos (A01_ID_PROJETO, A01_NOME_PROJETO)
    private void carregarProjetos() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A01_ID_PROJETO, A01_NOME_PROJETO FROM PROJETO_01");
             ResultSet rs = ps.executeQuery()) {

            comboBox1.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("A01_ID_PROJETO");
                String nome = rs.getString("A01_NOME_PROJETO");
                comboBox1.addItem(id + " - " + nome);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar projetos: " + e.getMessage());
        }
    }

    // ======== carregar combo de fornecedores (A04_ID_FORNECEDOR, A04_NOME)
    private void carregarFornecedores() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A04_ID_FORNECEDOR, A04_NOME FROM FORNECEDOR_04");
             ResultSet rs = ps.executeQuery()) {

            comboBox2.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("A04_ID_FORNECEDOR");
                String nome = rs.getString("A04_NOME");
                comboBox2.addItem(id + " - " + nome);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    // ======== tipos de produtos (opcional; manter coerência com FN_VALIDAR_TIPO_PRODUTO)
    private void carregarTipos() {
        comboBox1.setEditable(false); // se estiver usando comboBox1 como projeto,
        // este método é inócuo — mantive por compatibilidade.
        // Se preferir ter um combo de tipos separado, adapte o form e troque aqui.
    }

    // ======== inicializa JTable
    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Tipo", "Data Cadastro", "Data Chegada", "Valor Unit.", "ID Projeto", "ID Fornecedor"},
                0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
        // ajustes de coluna se necessário
    }

    // ======== preencher tabela com listarProdutos()
    private void preencherTabela() {
        if (controller == null) return;
        try {
            ArrayList<Model_Produto_02> lista = controller.listarProdutos();
            tableModel.setRowCount(0);
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    // ======== BOTÃO SALVAR
    private void onSalvar() {
        if (controller == null) {
            JOptionPane.showMessageDialog(null, "Controller não inicializado.");
            return;
        }

        try {
            String nome = (textField2 != null) ? textField2.getText().trim() : "";
            if (nome.isEmpty()) { JOptionPane.showMessageDialog(null, "Preencha o nome do produto."); return; }

            String descricao = (editorPane1 != null) ? editorPane1.getText() : "";
            String tipo = ""; // tipo pode vir de outro controle; por agora deixo vazio se não existir
            // se você tiver um combo de tipos, pegue dele

            // DATA CADASTRO
            if (textField1 == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha a data de cadastro (AAAA-MM-DD).");
                return;
            }
            java.sql.Date dataCadastro;
            try {
                dataCadastro = java.sql.Date.valueOf(textField1.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Data de cadastro inválida. Use AAAA-MM-DD.");
                return;
            }

            // DATA CHEGADA (opcional)
            java.sql.Date dataChegada = null;
            if (textField3 != null && !textField3.getText().trim().isEmpty()) {
                try {
                    dataChegada = java.sql.Date.valueOf(textField3.getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data de chegada inválida. Use AAAA-MM-DD.");
                    return;
                }
            }

            // VALOR
            double valor = 0.0;
            if (textField6 != null && !textField6.getText().trim().isEmpty()) {
                try {
                    valor = Double.parseDouble(textField6.getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Valor unitário inválido.");
                    return;
                }
            }

            // PROJETO (comboBox1) -> se o seu comboBox1 é projeto
            if (comboBox1.getSelectedItem() == null) { JOptionPane.showMessageDialog(null, "Selecione um projeto."); return; }
            String selProj = comboBox1.getSelectedItem().toString();
            int idProjeto = Integer.parseInt(selProj.split(" - ")[0]);

            // FORNECEDOR (comboBox2)
            if (comboBox2.getSelectedItem() == null) { JOptionPane.showMessageDialog(null, "Selecione um fornecedor."); return; }
            String selForn = comboBox2.getSelectedItem().toString();
            int idFornecedor = Integer.parseInt(selForn.split(" - ")[0]);

            boolean ok = controller.inserirProduto(nome, descricao, tipo,
                    new java.util.Date(dataCadastro.getTime()),
                    (dataChegada != null) ? new java.util.Date(dataChegada.getTime()) : new java.util.Date(dataCadastro.getTime()),
                    valor, idProjeto, idFornecedor);

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar produto. Verifique o log.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
    }

    // ======== BOTÃO EDITAR
    private void onEditar() {
        if (controller == null) {
            JOptionPane.showMessageDialog(null, "Controller não inicializado.");
            return;
        }

        try {
            int row = table1.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para editar."); return; }

            int idProduto = (int) tableModel.getValueAt(row, 0);

            Model_Produto_02 p = controller.buscarProduto(idProduto);
            if (p == null) { JOptionPane.showMessageDialog(null, "Produto não encontrado."); return; }

            // preencher campos
            textField2.setText(p.getA02_nome_produto());
            editorPane1.setText(p.getA02_descricao());
            textField1.setText((p.getA02_data_cadastro() != null) ? p.getA02_data_cadastro().toString() : "");
            if (textField3 != null) textField3.setText((p.getA02_data_chegada() != null) ? p.getA02_data_chegada().toString() : "");
            textField6.setText(String.valueOf(p.getA02_valor_unitario()));

            // selecionar projeto no comboBox1
            for (int i = 0; i < comboBox1.getItemCount(); i++) {
                String it = comboBox1.getItemAt(i).toString();
                if (it.startsWith(p.getA02_id_projeto() + " - ")) { comboBox1.setSelectedIndex(i); break; }
            }
            // fornecedor
            for (int i = 0; i < comboBox2.getItemCount(); i++) {
                String it = comboBox2.getItemAt(i).toString();
                if (it.startsWith(p.getA02_id_fornecedor() + " - ")) { comboBox2.setSelectedIndex(i); break; }
            }

            // confirmar alteração
            int resp = JOptionPane.showConfirmDialog(null, "Salvar alterações?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                // monta dados atualizados
                String nome = textField2.getText().trim();
                String descricao = editorPane1.getText();
                String tipo = ""; // se tiver campo de tipo, capture aqui

                java.sql.Date dataCadastro = null;
                try { dataCadastro = java.sql.Date.valueOf(textField1.getText().trim()); } catch (Exception ex) { dataCadastro = null; }

                java.sql.Date dataChegada = null;
                if (textField3 != null && !textField3.getText().trim().isEmpty()) {
                    try { dataChegada = java.sql.Date.valueOf(textField3.getText().trim()); } catch (Exception ex) { dataChegada = null; }
                }

                double valor = 0.0;
                try { valor = Double.parseDouble(textField6.getText().trim()); } catch (Exception ex) {}

                String selProj = comboBox1.getSelectedItem().toString();
                int idProjeto = Integer.parseInt(selProj.split(" - ")[0]);
                String selForn = comboBox2.getSelectedItem().toString();
                int idFornecedor = Integer.parseInt(selForn.split(" - ")[0]);

                boolean ok = controller.atualizarProduto(
                        idProduto,
                        nome,
                        descricao,
                        tipo,
                        (dataCadastro != null) ? new java.util.Date(dataCadastro.getTime()) : new java.util.Date(),
                        (dataChegada != null) ? new java.util.Date(dataChegada.getTime()) : new java.util.Date(),
                        valor,
                        idProjeto,
                        idFornecedor
                );

                if (ok) {
                    JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                    limparCampos();
                    preencherTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar produto.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
        }
    }

    // ======== BOTÃO EXCLUIR
    private void onExcluir() {
        if (controller == null) {
            JOptionPane.showMessageDialog(null, "Controller não inicializado.");
            return;
        }

        try {
            int row = table1.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para excluir."); return; }

            int idProduto = (int) tableModel.getValueAt(row, 0);
            int resp = JOptionPane.showConfirmDialog(null, "Confirma exclusão do produto ID " + idProduto + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                boolean ok = controller.excluirProduto(idProduto);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
                    preencherTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir produto.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
        }
    }

    // ======== BOTÃO BUSCAR por ID
    private void onBuscar() {
        if (controller == null) {
            JOptionPane.showMessageDialog(null, "Controller não inicializado.");
            return;
        }
        try {
            String s = JOptionPane.showInputDialog(null, "Informe o ID do produto:");
            if (s == null || s.trim().isEmpty()) return;
            int id = Integer.parseInt(s.trim());
            Model_Produto_02 p = controller.buscarProduto(id);
            if (p == null) { JOptionPane.showMessageDialog(null, "Produto não encontrado."); return; }

            // selecionar na tabela se existir
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (((int) tableModel.getValueAt(i, 0)) == id) {
                    table1.setRowSelectionInterval(i, i);
                    table1.scrollRectToVisible(table1.getCellRect(i, 0, true));
                    break;
                }
            }

            // preencher campos
            textField2.setText(p.getA02_nome_produto());
            editorPane1.setText(p.getA02_descricao());
            textField1.setText((p.getA02_data_cadastro() != null) ? p.getA02_data_cadastro().toString() : "");
            if (textField3 != null) textField3.setText((p.getA02_data_chegada() != null) ? p.getA02_data_chegada().toString() : "");
            textField6.setText(String.valueOf(p.getA02_valor_unitario()));

            // selects
            for (int i = 0; i < comboBox1.getItemCount(); i++) {
                String it = comboBox1.getItemAt(i).toString();
                if (it.startsWith(p.getA02_id_projeto() + " - ")) { comboBox1.setSelectedIndex(i); break; }
            }
            for (int i = 0; i < comboBox2.getItemCount(); i++) {
                String it = comboBox2.getItemAt(i).toString();
                if (it.startsWith(p.getA02_id_fornecedor() + " - ")) { comboBox2.setSelectedIndex(i); break; }
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        if (textField2 != null) textField2.setText("");
        if (editorPane1 != null) editorPane1.setText("");
        if (textField1 != null) textField1.setText("");
        if (textField3 != null) textField3.setText("");
        if (textField6 != null) textField6.setText("");
        if (comboBox1 != null && comboBox1.getItemCount() > 0) comboBox1.setSelectedIndex(0);
        if (comboBox2 != null && comboBox2.getItemCount() > 0) comboBox2.setSelectedIndex(0);
    }

    private void onCancel() {
        dispose();
    }
}
