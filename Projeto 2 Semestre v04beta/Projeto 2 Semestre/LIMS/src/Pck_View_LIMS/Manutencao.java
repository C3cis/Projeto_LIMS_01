package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import Pck_Controller_LIMS.Controller_Manutencao_05;
import Pck_Model_LIMS.Model_Manutencao_05;

public class Manutencao extends JDialog {
    private JPanel contentPane;
    private JLabel nomeLabel;
    private JTextField textField2;
    private JLabel descricaoLabel;
    private JEditorPane editorPane1;
    private JLabel dataInicialLabel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField5;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JEditorPane editorPane2;
    private JComboBox comboBox4;
    private JComboBox comboBox5;

    private Controller_Manutencao_05 controller = new Controller_Manutencao_05();

    public Manutencao() {
        setContentPane(contentPane);
        setModal(true);

        // inicializa tabela com as 10 colunas (mesma ordem do Model)
        table1.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "ID",
                        "Data Manutenção",
                        "Tipo Manutenção",
                        "Descrição",
                        "Status Resultado",
                        "Relatório",
                        "ID Projeto",
                        "ID Usuário",
                        "ID Localização",
                        "ID Produto"
                }
        ));

        // carregar combos (usuário/projeto/localizacao/produto) - opcional: implemente carregamento real
        // ex: carregarComboProdutos();

        salvarButton.addActionListener(e -> salvar());
        editarButton.addActionListener(e -> editar());
        excluirButton.addActionListener(e -> excluir());
        buscarButton.addActionListener(e -> buscar());
        sairButton.addActionListener(e -> dispose());

        // preenche a tabela inicialmente
        preencherTabela();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // ---------------- salvar
    private void salvar() {
        try {
            Model_Manutencao_05 m = new Model_Manutencao_05();

            // id só se informado
            if (!textField1.getText().trim().isEmpty()) {
                m.setA05_id_manutencao(Integer.parseInt(textField1.getText().trim()));
            }

            // Para datas recomendo parse/validar; aqui mantive como java.sql.Date esperado no model
            // Se o modelo usa java.sql.Date, converta; se usa String, ajuste.
            // Exemplo simples (se for string no model, adapte):
            // m.setA05_data_manutencao(java.sql.Date.valueOf(textField2.getText()));
            // Se o Model usa java.sql.Date: converter. Aqui supondo String em model, mas ajuste conforme seu Model.
            try {
                // tenta converter para java.sql.Date caso seu model use Date
                java.sql.Date dt = textField2.getText().trim().isEmpty() ? null :
                        java.sql.Date.valueOf(textField2.getText().trim());
                m.setA05_data_manutencao(dt);
            } catch (Exception ex) {
                m.setA05_data_manutencao(null);
            }

            m.setA05_tipo_manutencao(textField5.getText());
            m.setA05_descricao(editorPane1.getText());
            m.setA05_status_resultado(comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString() : "");
            m.setA05_relatorio(editorPane2.getText());

            // campos FK: se não tiver comboboxes para todos, você pode setar 0 ou manter null
            m.setA05_id_projeto(0); // se não tiver campo no view
            m.setA05_id_usuario(0);
            m.setA05_id_localizacao(0);
            m.setA05_id_produto(comboBox2.getSelectedItem() != null ? Integer.parseInt(comboBox2.getSelectedItem().toString()) : 0);

            boolean ok = controller.inserir_manutencao(m);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso!" : "Erro ao salvar!");
            if (ok) preencherTabela();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // ---------------- editar
    private void editar() {
        try {
            if (textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID para editar.");
                return;
            }

            Model_Manutencao_05 m = new Model_Manutencao_05();
            m.setA05_id_manutencao(Integer.parseInt(textField1.getText().trim()));

            try {
                java.sql.Date dt = textField2.getText().trim().isEmpty() ? null :
                        java.sql.Date.valueOf(textField2.getText().trim());
                m.setA05_data_manutencao(dt);
            } catch (Exception ex) {
                m.setA05_data_manutencao(null);
            }

            m.setA05_tipo_manutencao(textField5.getText());
            m.setA05_descricao(editorPane1.getText());
            m.setA05_status_resultado(comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString() : "");
            m.setA05_relatorio(editorPane2.getText());
            m.setA05_id_produto(comboBox2.getSelectedItem() != null ? Integer.parseInt(comboBox2.getSelectedItem().toString()) : 0);

            boolean ok = controller.atualizar_manutencao(m);
            JOptionPane.showMessageDialog(this, ok ? "Atualizado!" : "Erro ao atualizar!");
            if (ok) preencherTabela();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // ---------------- excluir
    private void excluir() {
        try {
            if (textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID para excluir.");
                return;
            }
            int id = Integer.parseInt(textField1.getText().trim());
            boolean ok = controller.deletar_manutencao(id);
            JOptionPane.showMessageDialog(this, ok ? "Excluído!" : "Erro ao excluir!");
            if (ok) preencherTabela();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // ---------------- buscar
    private void buscar() {
        try {
            String s = JOptionPane.showInputDialog(this, "Informe o ID da manutenção:");
            if (s == null || s.trim().isEmpty()) return;
            int id = Integer.parseInt(s.trim());

            Model_Manutencao_05 m = controller.buscar_manutencao(id);
            if (m == null) {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
                return;
            }

            textField1.setText(String.valueOf(m.getA05_id_manutencao()));
            if (m.getA05_data_manutencao() != null) textField2.setText(String.valueOf(m.getA05_data_manutencao()));
            textField5.setText(m.getA05_tipo_manutencao());
            editorPane1.setText(m.getA05_descricao());
            comboBox1.setSelectedItem(m.getA05_status_resultado());
            editorPane2.setText(m.getA05_relatorio());
            comboBox2.setSelectedItem(String.valueOf(m.getA05_id_produto()));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // ---------------- preencher tabela com todos os campos
    private void preencherTabela() {
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Model_Manutencao_05> lista = controller.listar_manutencao();
            for (Model_Manutencao_05 m : lista) {
                modelo.addRow(new Object[] {
                        m.getA05_id_manutencao(),
                        m.getA05_data_manutencao(),
                        m.getA05_tipo_manutencao(),
                        m.getA05_descricao(),
                        m.getA05_status_resultado(),
                        m.getA05_relatorio(),
                        m.getA05_id_projeto(),
                        m.getA05_id_usuario(),
                        m.getA05_id_localizacao(),
                        m.getA05_id_produto()
                });
            }
            modelo.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage());
        }
    }
}
