package Pck_View_LIMS;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Pck_Controller_LIMS.Controller_Estoque_06;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Estoque_06;

public class Estoque extends JDialog {
    private JPanel contentPane;
    private JTextField textField2;      // data de entrada (string yyyy-MM-dd)
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;
    private JTable table1;
    private JTextField textField1;      // id estoque
    private JComboBox comboBox1;        // local
    private JComboBox comboBox2;        // produto
    private JSpinner spinner1;          // quantidade
    private JButton buttonOK;
    private JButton buttonCancel;

    private Controller_Estoque_06 controller;

    public Estoque() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Estoque");

        controller = new Controller_Estoque_06();

        // inicializa componentes mínimos (caso criado manualmente sem GUI builder)
        if (spinner1 == null) spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        if (comboBox1 == null) comboBox1 = new JComboBox();
        if (comboBox2 == null) comboBox2 = new JComboBox();
        if (table1 == null) table1 = new JTable();

        // tabela: colunas na mesma ordem do Model/SQL
        table1.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID Estoque",
                        "Quantidade",
                        "Data Entrada",
                        "ID Produto",
                        "ID Local"
                }
        ));

        // carregar combos e tabela
        carregarCombos();
        preencherTabela();

        // BOTÕES
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
                onSair();
            }
        });

        // fechar no X
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onSair(); }
        });

        // ESC fecha
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onSair(); }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // ----------------------------
    // AÇÕES
    // ----------------------------
    private void onSalvar() {
        try {
            Model_Estoque_06 m = new Model_Estoque_06();

            // quantidade
            m.setA06_quantidade((Integer) spinner1.getValue());

            // data (aceita vazio -> null)
            String sData = (textField2 != null) ? textField2.getText().trim() : "";
            if (!sData.isEmpty()) {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(sData); // expects yyyy-MM-dd
                    m.setA06_data_entrada(new java.util.Date(sqlDate.getTime()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-MM-dd.");
                    return;
                }
            } else {
                m.setA06_data_entrada(null);
            }

            // produto: comboBox2 item formatado "id - nome" ou só id
            int idProduto = extrairIdCombo(comboBox2);
            m.setA06_id_produto(idProduto);

            // local
            int idLocal = extrairIdCombo(comboBox1);
            m.setA06_id_localizacao(idLocal);

            boolean ok = controller.inserirEstoque(m);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Estoque inserido com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir estoque.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void onEditar() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID para editar.");
                return;
            }

            Model_Estoque_06 m = new Model_Estoque_06();
            m.setA06_id_estoque(Integer.parseInt(textField1.getText().trim()));
            m.setA06_quantidade((Integer) spinner1.getValue());

            String sData = (textField2 != null) ? textField2.getText().trim() : "";
            if (!sData.isEmpty()) {
                try {
                    java.sql.Date sqlDate = java.sql.Date.valueOf(sData);
                    m.setA06_data_entrada(new java.util.Date(sqlDate.getTime()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-MM-dd.");
                    return;
                }
            } else {
                m.setA06_data_entrada(null);
            }

            m.setA06_id_produto(extrairIdCombo(comboBox2));
            m.setA06_id_localizacao(extrairIdCombo(comboBox1));

            boolean ok = controller.atualizarEstoque(m);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void onExcluir() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(textField1.getText().trim());
            boolean ok = controller.excluirEstoque(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void onBuscar() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID para buscar.");
                return;
            }
            int id = Integer.parseInt(textField1.getText().trim());
            Model_Estoque_06 m = controller.buscarEstoque(id);
            if (m == null) {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
                return;
            }

            // preencher campos com model
            textField1.setText(String.valueOf(m.getA06_id_estoque()));
            spinner1.setValue(m.getA06_quantidade());
            if (m.getA06_data_entrada() != null) {
                // converte java.util.Date para yyyy-MM-dd
                java.sql.Date sqlDate = new java.sql.Date(m.getA06_data_entrada().getTime());
                textField2.setText(sqlDate.toString());
            } else {
                textField2.setText("");
            }

            selecionarComboPorId(comboBox2, m.getA06_id_produto());
            selecionarComboPorId(comboBox1, m.getA06_id_localizacao());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void onSair() {
        dispose();
    }

    // ----------------------------
    // UTILITÁRIOS
    // ----------------------------
    private int extrairIdCombo(JComboBox cb) {
        try {
            if (cb == null || cb.getSelectedItem() == null) return 0;
            Object item = cb.getSelectedItem();
            String s = item.toString();
            if (s.contains(" - ")) {
                return Integer.parseInt(s.split(" - ")[0].trim());
            } else {
                // tenta parse direto
                return Integer.parseInt(s.trim());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private void selecionarComboPorId(JComboBox cb, int id) {
        try {
            for (int i = 0; i < cb.getItemCount(); i++) {
                String s = cb.getItemAt(i).toString();
                if (s.startsWith(id + " -") || s.equals(String.valueOf(id))) {
                    cb.setSelectedIndex(i);
                    return;
                }
            }
        } catch (Exception ignored) {}
    }

    private void limparCampos() {
        textField1.setText("");
        textField2.setText("");
        spinner1.setValue(0);
        if (comboBox1.getItemCount() > 0) comboBox1.setSelectedIndex(0);
        if (comboBox2.getItemCount() > 0) comboBox2.setSelectedIndex(0);
    }

    // ----------------------------
    // CARREGAR COMBOS (produtos/local)
    // ----------------------------
    private void carregarCombos() {
        // produto
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A02_ID_PRODUTO, A02_NOME_PRODUTO FROM PRODUTO_02 ORDER BY A02_NOME_PRODUTO");
             ResultSet rs = ps.executeQuery()) {

            comboBox2.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("A02_ID_PRODUTO");
                String nome = rs.getString("A02_NOME_PRODUTO");
                comboBox2.addItem(id + " - " + nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }

        // localizacoes
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A07_ID_LOCALIZACAO, A07_IDENTIFICACAO FROM LOCALIZACAO_07 ORDER BY A07_IDENTIFICACAO");
             ResultSet rs = ps.executeQuery()) {

            comboBox1.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("A07_ID_LOCALIZACAO");
                String nome = rs.getString("A07_IDENTIFICACAO");
                comboBox1.addItem(id + " - " + nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar locais: " + e.getMessage());
        }
    }

    // ----------------------------
    // PREENCHER TABELA
    // ----------------------------
    private void preencherTabela() {
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0);

        try {
            List<Model_Estoque_06> lista = controller.listarEstoques();
            for (Model_Estoque_06 m : lista) {
                String dataStr = (m.getA06_data_entrada() == null) ? "" :
                        new java.sql.Date(m.getA06_data_entrada().getTime()).toString();

                modelo.addRow(new Object[]{
                        m.getA06_id_estoque(),
                        m.getA06_quantidade(),
                        dataStr,
                        m.getA06_id_produto(),
                        m.getA06_id_localizacao()
                });
            }
            modelo.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage());
        }
    }
}