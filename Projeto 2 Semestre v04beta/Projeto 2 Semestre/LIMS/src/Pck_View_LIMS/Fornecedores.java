package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_Model_LIMS.Model_Fornecedor_04;

public class Fornecedores extends JDialog {

    private JPanel contentPane;

    private JTextField textField1CNPJ;
    private JTextField textField2Nome;
    private JTextField textField3Email;
    private JTextField textField4Telefone;
    private JTextField textField5Endereco;

    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;

    private JTable table1Geral;
    private DefaultTableModel tableModel;

    private Controller_Fornecedor_04 controller;

    public Fornecedores() {

        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Fornecedores");

        controller = new Controller_Fornecedor_04();

        inicializarTabela();
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

        contentPane.registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "CNPJ", "Nome", "Email", "Telefone", "Endereço"},
                0
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table1Geral.setModel(tableModel);
    }

    private void preencherTabela() {
        try {
            tableModel.setRowCount(0);
            ArrayList<Model_Fornecedor_04> lista = controller.listarFornecedores();

            for (Model_Fornecedor_04 f : lista) {
                tableModel.addRow(new Object[]{
                        f.getA04_id_fornecedor(),
                        f.getA04_cnpj_fornecedor(),
                        f.getA04_nome_fornecedor(),
                        f.getA04_email_fornecedor(),
                        f.getA04_telefone_fornecedor(),
                        f.getA04_endereco_fornecedor()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    private void adicionarEventoCliqueTabela() {
        table1Geral.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table1Geral.getSelectedRow();
                if (row >= 0) {
                    preencherCamposDoClique(row);
                }
            }
        });
    }

    private void preencherCamposDoClique(int row) {
        textField1CNPJ.setText(tableModel.getValueAt(row, 1).toString());
        textField2Nome.setText(tableModel.getValueAt(row, 2).toString());
        textField3Email.setText(tableModel.getValueAt(row, 3).toString());
        textField4Telefone.setText(tableModel.getValueAt(row, 4).toString());
        textField5Endereco.setText(tableModel.getValueAt(row, 5).toString());
    }

    private void onSalvar() {
        try {
            boolean ok = controller.salvarFornecedor(
                    textField2Nome.getText().trim(),
                    textField1CNPJ.getText().trim(),
                    textField4Telefone.getText().trim(),
                    textField3Email.getText().trim(),
                    textField5Endereco.getText().trim()
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

    private void onEditar() {
        try {
            int linha = table1Geral.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(null, "Selecione um registro.");
                return;
            }

            int id = (int) tableModel.getValueAt(linha, 0);

            boolean ok = controller.editarFornecedor(
                    id,
                    textField2Nome.getText().trim(),
                    textField1CNPJ.getText().trim(),
                    textField4Telefone.getText().trim(),
                    textField3Email.getText().trim(),
                    textField5Endereco.getText().trim()
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Editado!");
                preencherTabela();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
        }
    }

    private void onExcluir() {
        try {
            int linha = table1Geral.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(null, "Selecione um registro.");
                return;
            }

            int id = (int) tableModel.getValueAt(linha, 0);

            if (JOptionPane.showConfirmDialog(null, "Excluir fornecedor?", "Confirmar",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                boolean ok = controller.excluirFornecedor(id);

                if (ok) {
                    JOptionPane.showMessageDialog(null, "Excluído!");
                    preencherTabela();
                    limparCampos();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void onBuscar() {
        try {
            String s = JOptionPane.showInputDialog("ID para buscar:");
            if (s == null || s.isEmpty()) return;

            int id = Integer.parseInt(s);

            Model_Fornecedor_04 f = controller.buscarFornecedor(id);

            if (f == null) {
                JOptionPane.showMessageDialog(null, "Não encontrado.");
                return;
            }

            textField1CNPJ.setText(f.getA04_cnpj_fornecedor());
            textField2Nome.setText(f.getA04_nome_fornecedor());
            textField3Email.setText(f.getA04_email_fornecedor());
            textField4Telefone.setText(f.getA04_telefone_fornecedor());
            textField5Endereco.setText(f.getA04_endereco_fornecedor());

            selecionarNaTabela(id);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void selecionarNaTabela(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((int) tableModel.getValueAt(i, 0) == id) {
                table1Geral.setRowSelectionInterval(i, i);
                table1Geral.scrollRectToVisible(table1Geral.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private void limparCampos() {
        textField1CNPJ.setText("");
        textField2Nome.setText("");
        textField3Email.setText("");
        textField4Telefone.setText("");
        textField5Endereco.setText("");
    }
}
