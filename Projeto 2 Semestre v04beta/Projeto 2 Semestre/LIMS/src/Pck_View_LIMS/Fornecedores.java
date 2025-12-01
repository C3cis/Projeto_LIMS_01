package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_Model_LIMS.Model_Fornecedor_04;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class Fornecedores extends JDialog {

    private JPanel contentPane;

    private JTextField textField1CNPJ;     // CNPJ
    private JTextField textField2Nome;     // Nome
    private JTextField textField3Email;    // Email
    private JTextField textField4Telefone; // Telefone
    private JTextField textField5Endereco; // Endereço

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
        setSize(800, 500);
        setLocationRelativeTo(null);

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

    }

    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CNPJ", "Telefone", "Email", "Endereço"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        if (table1Geral == null) {
            table1Geral = new JTable();
        }
        table1Geral.setModel(tableModel);
        table1Geral.setAutoCreateRowSorter(true);
    }

    private void preencherTabela() {
        try {
            tableModel.setRowCount(0);
            ArrayList<Model_Fornecedor_04> lista = controller.listarFornecedores();

            if (lista != null) {
                for (Model_Fornecedor_04 f : lista) {
                    tableModel.addRow(new Object[]{
                            f.getA04_id_fornecedor(),
                            f.getA04_nome(),
                            f.getA04_cnpj(),
                            f.getA04_telefone(),
                            f.getA04_email(),
                            f.getA04_endereco()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao preencher tabela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarEventoCliqueTabela() {
        if (table1Geral == null) return;
        table1Geral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1Geral.getSelectedRow();
                if (row >= 0) {
                    preencherCamposDoClique(row);
                }
            }
        });
    }

    private void preencherCamposDoClique(int row) {
        Object nome = tableModel.getValueAt(row, 1);
        Object cnpj = tableModel.getValueAt(row, 2);
        Object telefone = tableModel.getValueAt(row, 3);
        Object email = tableModel.getValueAt(row, 4);
        Object endereco = tableModel.getValueAt(row, 5);

        textField2Nome.setText(nome != null ? nome.toString() : "");
        textField1CNPJ.setText(cnpj != null ? cnpj.toString() : "");
        textField4Telefone.setText(telefone != null ? telefone.toString() : "");
        textField3Email.setText(email != null ? email.toString() : "");
        textField5Endereco.setText(endereco != null ? endereco.toString() : "");
    }

    private void onSalvar() {
        try {
            String nome = textField2Nome.getText().trim();
            String cnpj = textField1CNPJ.getText().trim();
            String telefone = textField4Telefone.getText().trim();
            String email = textField3Email.getText().trim();
            String endereco = textField5Endereco.getText().trim();

            if (nome.isEmpty() || cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CNPJ são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Model_Fornecedor_04 f = new Model_Fornecedor_04();
            f.setA04_nome(nome);
            f.setA04_cnpj(cnpj);
            f.setA04_telefone(telefone);
            f.setA04_email(email);
            f.setA04_endereco(endereco);

            String resultado = controller.salvarFornecedor(f);
            JOptionPane.showMessageDialog(this, resultado);

            preencherTabela();
            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEditar() {
        try {
            int linha = table1Geral.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um registro para editar.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // pega o ID da tabela (coluna 0)
            Object idObj = tableModel.getValueAt(linha, 0);
            int id = Integer.parseInt(idObj.toString());

            String nome = textField2Nome.getText().trim();
            String cnpj = textField1CNPJ.getText().trim();
            String telefone = textField4Telefone.getText().trim();
            String email = textField3Email.getText().trim();
            String endereco = textField5Endereco.getText().trim();

            if (nome.isEmpty() || cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CNPJ são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Model_Fornecedor_04 f = new Model_Fornecedor_04();
            f.setA04_id_fornecedor(id);
            f.setA04_nome(nome);
            f.setA04_cnpj(cnpj);
            f.setA04_telefone(telefone);
            f.setA04_email(email);
            f.setA04_endereco(endereco);

            String resultado = controller.atualizarFornecedor(f);
            JOptionPane.showMessageDialog(this, resultado);

            preencherTabela();
            limparCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onExcluir() {
        try {
            int linha = table1Geral.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um registro para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object idObj = tableModel.getValueAt(linha, 0);
            int id = Integer.parseInt(idObj.toString());

            int resp = JOptionPane.showConfirmDialog(this, "Excluir fornecedor?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp != JOptionPane.YES_OPTION) return;

            String resultado = controller.excluirFornecedor(id);
            JOptionPane.showMessageDialog(this, resultado);

            preencherTabela();
            limparCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBuscar() {
        try {
            String s = JOptionPane.showInputDialog(this, "ID para buscar:");
            if (s == null || s.trim().isEmpty()) return;

            int id = Integer.parseInt(s.trim());

            Model_Fornecedor_04 f = controller.buscarFornecedorPorID(id);
            if (f == null || f.getA04_id_fornecedor() == 0) {
                JOptionPane.showMessageDialog(this, "Não encontrado.");
                return;
            }
            textField2Nome.setText(f.getA04_nome());
            textField1CNPJ.setText(f.getA04_cnpj());
            textField4Telefone.setText(f.getA04_telefone());
            textField3Email.setText(f.getA04_email());
            textField5Endereco.setText(f.getA04_endereco());

            selecionarNaTabela(id);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarNaTabela(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object idObj = tableModel.getValueAt(i, 0);
            if (idObj != null) {
                try {
                    int idTabela = Integer.parseInt(idObj.toString());
                    if (idTabela == id) {
                        table1Geral.setRowSelectionInterval(i, i);
                        table1Geral.scrollRectToVisible(table1Geral.getCellRect(i, 0, true));
                        break;
                    }
                } catch (NumberFormatException ignored) {}
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
