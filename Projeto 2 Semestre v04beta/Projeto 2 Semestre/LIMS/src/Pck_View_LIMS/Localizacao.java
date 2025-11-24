package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Localizacao_07;
import Pck_Model_LIMS.Model_Localizacao_07;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 * View (JDialog) com componentes mantidos (textField1, textField2, textField3, table1, etc.)
 * Integração direta com Controller_Localizacao_07
 */
public class Localizacao extends JDialog {
    private JPanel contentPane;
    private JTextField textField2Setor; // Setor
    private JTextField textField3Identificacao; // Identificação
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;
    private JTable table1Geral;

    private Controller_Localizacao_07 controller;

    public Localizacao() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Localizações");

        controller = new Controller_Localizacao_07();

        inicializarTabela();
        carregarTabela();

        salvarButton.addActionListener(e -> salvar());
        editarButton.addActionListener(e -> editar());
        excluirButton.addActionListener(e -> excluir());
        buscarButton.addActionListener(e -> buscar());
        sairButton.addActionListener(e -> dispose());

        // fechar no X
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        // ESC fecha
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void inicializarTabela() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Identificação", "Setor", "ID Usuário"}, 0);
        table1.setModel(model);
    }

    private void carregarTabela() {
        try {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            List<Model_Localizacao_07> lista = controller.listar();
            for (Model_Localizacao_07 m : lista) {
                model.addRow(new Object[]{
                        m.getA07_id_localizacao(),
                        m.getA07_identificacao(),
                        m.getA07_setor(),
                        m.getA07_id_usuario()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage());
        }
    }

    private void salvar() {
        try {
            Model_Localizacao_07 m = new Model_Localizacao_07();
            m.setA07_identificacao(textField3.getText());
            m.setA07_setor(textField2.getText());
            // solicita ID usuário (pode ser alterado para combo se preferir)
            String sId = JOptionPane.showInputDialog(this, "ID Usuário:");
            if (sId == null) return;
            m.setA07_id_usuario(Integer.parseInt(sId.trim()));

            boolean ok = controller.inserir(m);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Localização inserida.");
                carregarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao inserir.");
            }
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(this, "ID Usuário inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void editar() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe ID para editar.");
                return;
            }
            Model_Localizacao_07 m = new Model_Localizacao_07();
            m.setA07_id_localizacao(Integer.parseInt(textField1.getText().trim()));
            m.setA07_identificacao(textField3.getText());
            m.setA07_setor(textField2.getText());
            String sId = JOptionPane.showInputDialog(this, "ID Usuário:");
            if (sId == null) return;
            m.setA07_id_usuario(Integer.parseInt(sId.trim()));

            boolean ok = controller.atualizar(m);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
                carregarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar.");
            }
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }

    private void excluir() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe ID para excluir.");
                return;
            }
            int id = Integer.parseInt(textField1.getText().trim());
            boolean ok = controller.excluir(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Excluído com sucesso.");
                carregarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir.");
            }
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            if (textField1.getText() == null || textField1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe ID para buscar.");
                return;
            }
            int id = Integer.parseInt(textField1.getText().trim());
            Model_Localizacao_07 m = controller.buscar(id);
            if (m != null) {
                textField3.setText(m.getA07_identificacao());
                textField2.setText(m.getA07_setor());
            } else {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
            }
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }
}