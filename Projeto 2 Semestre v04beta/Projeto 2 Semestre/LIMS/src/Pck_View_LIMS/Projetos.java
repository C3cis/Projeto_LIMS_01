package Pck_View_LIMS;

import javax.swing.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_DAO_LIMS.DAO_Conexao;

public class Projetos extends JDialog {
    private JPanel contentPane;
    private JTextField textField2; // Nome do projeto
    private JTextField textField5; // Departamento
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JLabel descricaoLabel;
    private JTextField textField1; // Data Inicial (AAAA-MM-DD)
    private JLabel dataInicialLabel;
    private JLabel nomeLabel;
    private JTextField textField6; // Orçamento
    private JLabel orçamentoLabel;
    private JComboBox comboBox1; // Status do projeto
    private JComboBox comboBox2; // ID Usuario
    private JEditorPane editorPane1; // Descricao
    private JTextField textField3; // Data Final (adicionado)
    // modelo da tabela
    private DefaultTableModel tableModel;

    public Projetos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Projetos");

        // inicializa componentes faltantes (se necessário)
        if (comboBox1 == null) comboBox1 = new JComboBox();
        if (comboBox2 == null) comboBox2 = new JComboBox();
        if (table1 == null) table1 = new JTable();

        carregarStatus();
        carregarUsuarios();
        inicializarTabela();
        preencherTabela(); // carrega dados ao abrir

        // BOTÃO SALVAR
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // BOTÃO EDITAR (atualizar registro selecionado)
        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEditar();
            }
        });

        // BOTÃO EXCLUIR
        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExcluir();
            }
        });

        // BOTÃO BUSCAR (por ID)
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBuscar();
            }
        });

        // BOTÃO SAIR
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

    // ================================================
    // CARREGAR COMBOBOX DE USUÁRIOS
    // ================================================
    private void carregarUsuarios() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A11_ID_USUARIO, A11_NOME FROM USUARIO_11");
             ResultSet rs = ps.executeQuery()) {

            comboBox2.removeAllItems();

            while (rs.next()) {
                int id = rs.getInt("A11_ID_USUARIO");
                String nome = rs.getString("A11_NOME");

                comboBox2.addItem(id + " - " + nome);  // exemplo: "3 - João"
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // ================================================
    // CARREGAR STATUS (comboBox1)
    // ================================================
    private void carregarStatus() {
        comboBox1.removeAllItems();
        // manter os status que batem com a função FN_VALIDAR_STATUS_PROJETO
        comboBox1.addItem("ATIVO");
        comboBox1.addItem("CONCLUIDO");
        comboBox1.addItem("CANCELADO");
        // você pode adicionar outros status se quiser, mas FN_VALIDAR_STATUS_PROJETO só aceita os acima
    }

    // ================================================
    // INICIALIZA TABELA
    // ================================================
    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Data Inicial", "Data Final", "Orçamento", "Status", "Departamento", "ID Usuario"},
                0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false; // tabela apenas leitura
            }
        };
        table1.setModel(tableModel);
        table1.getColumnModel().getColumn(0).setPreferredWidth(50);
        // ajuste colunas conforme precisar
    }

    // ================================================
    // PREENCHER TABELA
    // ================================================
    private void preencherTabela() {
        try {
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            ArrayList<Model_Projeto_01> lista = controller.listar_projeto();

            tableModel.setRowCount(0); // limpar
            for (Model_Projeto_01 p : lista) {
                tableModel.addRow(new Object[]{
                        p.getA01_id_projeto(),
                        p.getA01_nome_projeto(),
                        p.getA01_data_inicial(),
                        p.getA01_data_final(),
                        p.getA01_orcamento(),
                        p.getA01_status_projeto(),
                        p.getA01_departamento(),
                        p.getA01_id_usuario()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    // ================================================
    // BOTÃO SALVAR – INTEGRAÇÃO COMPLETA COM A PERSISTÊNCIA
    // ================================================
    private void onOK() {
        try {
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            Model_Projeto_01 model = new Model_Projeto_01();

            // VALIDAÇÕES BÁSICAS
            String nome = textField2.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o nome do projeto.");
                return;
            }
            model.setA01_nome_projeto(nome);

            String descricao = editorPane1.getText();
            model.setA01_descricao(descricao);

            // DATA INICIAL
            String dataInicialStr = textField1.getText().trim();
            if (dataInicialStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha a data inicial (AAAA-MM-DD).");
                return;
            }
            java.sql.Date dataInicial;
            try {
                dataInicial = java.sql.Date.valueOf(dataInicialStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Data inicial inválida. Use o formato AAAA-MM-DD.");
                return;
            }
            model.setA01_data_inicial(dataInicial);

            // DATA FINAL (opcional)
            java.sql.Date dataFinal = null;
            String dataFinalStr = (textField3 != null) ? textField3.getText().trim() : "";
            if (!dataFinalStr.isEmpty()) {
                try {
                    dataFinal = java.sql.Date.valueOf(dataFinalStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data final inválida. Use o formato AAAA-MM-DD.");
                    return;
                }
            }
            model.setA01_data_final(dataFinal);

            // ORCAMENTO
            String orcStr = textField6.getText().trim();
            double orc = 0.0;
            if (!orcStr.isEmpty()) {
                try {
                    orc = Double.parseDouble(orcStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Orçamento inválido.");
                    return;
                }
            }
            model.setA01_orcamento(orc);

            // STATUS
            if (comboBox1.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione um status.");
                return;
            }
            String status = comboBox1.getSelectedItem().toString();
            model.setA01_status_projeto(status);

            // DEPARTAMENTO
            model.setA01_departamento(textField5.getText().trim());

            // USUÁRIO (FK)
            if (comboBox2.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione um usuário.");
                return;
            }
            String item = comboBox2.getSelectedItem().toString();
            int idUsuario = Integer.parseInt(item.split(" - ")[0]);
            model.setA01_id_usuario(idUsuario);

            // Chama o Controller (insere)
            boolean ok = controller.inserir_projeto(model);
            if (ok) {
                JOptionPane.showMessageDialog(null, "Projeto salvo com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar projeto. Verifique o log.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao salvar projeto: " + e.getMessage());
        }
    }

    // ================================================
    // EDITAR registro selecionado (preenche e salva)
    // ================================================
    private void onEditar() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Selecione um projeto na tabela para editar.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);

            // buscar model com dados atuais
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            Model_Projeto_01 model = controller.buscar_projeto(id);
            if (model == null) {
                JOptionPane.showMessageDialog(null, "Projeto não encontrado.");
                return;
            }

            // Preencher campos com dados para edição
            textField2.setText(model.getA01_nome_projeto());
            editorPane1.setText(model.getA01_descricao());
            textField1.setText((model.getA01_data_inicial() != null) ? model.getA01_data_inicial().toString() : "");
            if (textField3 != null)
                textField3.setText((model.getA01_data_final() != null) ? model.getA01_data_final().toString() : "");
            textField6.setText(String.valueOf(model.getA01_orcamento()));
            comboBox1.setSelectedItem(model.getA01_status_projeto());
            textField5.setText(model.getA01_departamento());

            // seleciona usuario no combobox
            for (int i = 0; i < comboBox2.getItemCount(); i++) {
                String it = comboBox2.getItemAt(i).toString();
                if (it.startsWith(model.getA01_id_usuario() + " - ")) {
                    comboBox2.setSelectedIndex(i);
                    break;
                }
            }

            // Ao confirmar edição, reaproveito o botão salvar para atualizar:
            int resp = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                // monta model atualizado
                Model_Projeto_01 updated = new Model_Projeto_01();
                updated.setA01_id_projeto(model.getA01_id_projeto());
                updated.setA01_nome_projeto(textField2.getText().trim());
                updated.setA01_descricao(editorPane1.getText());
                try {
                    updated.setA01_data_inicial(java.sql.Date.valueOf(textField1.getText().trim()));
                } catch (Exception ex) { updated.setA01_data_inicial(null); }
                try {
                    updated.setA01_data_final((textField3 != null && !textField3.getText().trim().isEmpty())
                            ? java.sql.Date.valueOf(textField3.getText().trim()) : null);
                } catch (Exception ex) { updated.setA01_data_final(null); }
                try {
                    updated.setA01_orcamento(Double.parseDouble(textField6.getText().trim()));
                } catch (Exception ex) { updated.setA01_orcamento(0.0); }
                updated.setA01_status_projeto(comboBox1.getSelectedItem().toString());
                updated.setA01_departamento(textField5.getText().trim());
                String sel = comboBox2.getSelectedItem().toString();
                updated.setA01_id_usuario(Integer.parseInt(sel.split(" - ")[0]));

                boolean ok = controller.atualizar_projeto(updated);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Projeto atualizado com sucesso!");
                    limparCampos();
                    preencherTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar projeto.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
        }
    }

    // ================================================
    // EXCLUIR registro selecionado
    // ================================================
    private void onExcluir() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Selecione um projeto na tabela para excluir.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            int resp = JOptionPane.showConfirmDialog(null, "Confirma exclusão do projeto ID " + id + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                Controller_Projeto_01 controller = new Controller_Projeto_01();
                boolean ok = controller.deletar_projeto(id);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Projeto excluído com sucesso!");
                    preencherTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir projeto.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
        }
    }

    // ================================================
    // BUSCAR por ID (dialog) e selecionar na tabela
    // ================================================
    private void onBuscar() {
        try {
            String s = JOptionPane.showInputDialog(null, "Informe o ID do projeto:");
            if (s == null || s.trim().isEmpty()) return;
            int id = Integer.parseInt(s.trim());
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            Model_Projeto_01 p = controller.buscar_projeto(id);
            if (p == null) {
                JOptionPane.showMessageDialog(null, "Projeto não encontrado.");
                return;
            }
            // selecionar linha na tabela
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (((int) tableModel.getValueAt(i, 0)) == id) {
                    table1.setRowSelectionInterval(i, i);
                    table1.scrollRectToVisible(table1.getCellRect(i, 0, true));
                    break;
                }
            }
            // também preenche os campos
            textField2.setText(p.getA01_nome_projeto());
            editorPane1.setText(p.getA01_descricao());
            textField1.setText((p.getA01_data_inicial() != null) ? p.getA01_data_inicial().toString() : "");
            if (textField3 != null) textField3.setText((p.getA01_data_final() != null) ? p.getA01_data_final().toString() : "");
            textField6.setText(String.valueOf(p.getA01_orcamento()));
            comboBox1.setSelectedItem(p.getA01_status_projeto());
            textField5.setText(p.getA01_departamento());
            // selecionar usuario
            for (int i = 0; i < comboBox2.getItemCount(); i++) {
                String it = comboBox2.getItemAt(i).toString();
                if (it.startsWith(p.getA01_id_usuario() + " - ")) {
                    comboBox2.setSelectedIndex(i);
                    break;
                }
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        textField2.setText("");
        editorPane1.setText("");
        textField1.setText("");
        if (textField3 != null) textField3.setText("");
        textField6.setText("");
        comboBox1.setSelectedIndex(0);
        textField5.setText("");
        if (comboBox2.getItemCount() > 0) comboBox2.setSelectedIndex(0);
    }

    private void onCancel() {
        dispose();
    }
}