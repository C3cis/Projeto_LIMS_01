package Pck_View_LIMS;

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

    // Campos de texto
    private JTextField txtNomeProjeto;       // textField2
    private JTextField txtDepartamento;      // textField5
    private JTextField textField1dataIni;       // textField1
    private JTextField txtDataFinal;         // textField3
    private JTextField txtOrcamento;         // textField6

    // Área de texto
    private JLabel txtDescricao;
    private JLabel JlabelDataInicial; //editorPane1
    private JEditorPane editorPane1Descri;
    private JLabel nomeLabel;
    // Combobox
    private JComboBox<String> cmbStatus;     // comboBox1
    private JComboBox<String> cmbUsuario;    // comboBox2

    // Botões
    private JButton salvarButton;               // salvarButton
    private JButton editarButton;               // editarButton
    private JButton excluirButton;              // excluirButton
    private JButton buscarButton;               // buscarButton
    private JButton sairButton;                 // sairButton

    // Tabela
    private JTable table1geral;              // table1
    private DefaultTableModel tableModel;

    public Projetos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Projetos");

        inicializarTabela();
        carregarStatus();
        carregarUsuarios();
        preencherTabela();

        salvarButton.addActionListener(e -> salvarProjeto());
        editarButton.addActionListener(e -> editarProjeto());
        excluirButton.addActionListener(e -> excluirProjeto());
        buscarButton.addActionListener(e -> buscarProjeto());
        sairButton.addActionListener(e -> dispose());
    }

    // ===========================================================
    // CARREGAR COMBOBOX STATUS
    // ===========================================================
    private void carregarStatus() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
        cmbStatus.addItem("CONCLUIDO");
        cmbStatus.addItem("CANCELADO");
    }

    // ===========================================================
    // CARREGAR USUÁRIOS
    // ===========================================================
    private void carregarUsuarios() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A11_ID_USUARIO, A11_NOME FROM USUARIO_11");
             ResultSet rs = ps.executeQuery()) {

            cmbUsuario.removeAllItems();

            while (rs.next()) {
                int id = rs.getInt("A11_ID_USUARIO");
                String nome = rs.getString("A11_NOME");
                cmbUsuario.addItem(id + " - " + nome);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // ===========================================================
    // INICIALIZAR TABELA
    // ===========================================================
    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Data Inicial", "Data Final", "Orçamento", "Status", "Departamento", "Usuário"},
                0
        );

        table1geral.setModel(tableModel);
        table1geral.setDefaultEditor(Object.class, null); // tabela somente leitura
    }

    // ===========================================================
    // PREENCHER TABELA
    // ===========================================================
    private void preencherTabela() {
        try {
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            ArrayList<Model_Projeto_01> lista = controller.listar_projeto();

            tableModel.setRowCount(0);

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
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    // ===========================================================
    // SALVAR
    // ===========================================================
    private void salvarProjeto() {
        try {
            Model_Projeto_01 m = montarModelDoFormulario();
            Controller_Projeto_01 controller = new Controller_Projeto_01();

            if (controller.inserir_projeto(m)) {
                JOptionPane.showMessageDialog(null, "Projeto salvo com sucesso!");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar projeto.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
    }

    // ===========================================================
    // EDITAR
    // ===========================================================
    private void editarProjeto() {
        int row = table1geral.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para editar.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Controller_Projeto_01 controller = new Controller_Projeto_01();
        Model_Projeto_01 model = controller.buscar_projeto(id);

        if (model == null) {
            JOptionPane.showMessageDialog(null, "Projeto não encontrado.");
            return;
        }

        preencherFormulario(model);

        if (JOptionPane.showConfirmDialog(null, "Salvar alterações?", "Confirmar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            try {
                Model_Projeto_01 atualizado = montarModelDoFormulario();
                atualizado.setA01_id_projeto(model.getA01_id_projeto());

                if (controller.atualizar_projeto(atualizado)) {
                    JOptionPane.showMessageDialog(null, "Projeto atualizado!");
                    limparCampos();
                    preencherTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar.");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

    // ===========================================================
    // EXCLUIR
    // ===========================================================
    private void excluirProjeto() {
        int row = table1geral.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para excluir.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        if (JOptionPane.showConfirmDialog(null,
                "Excluir projeto " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            Controller_Projeto_01 controller = new Controller_Projeto_01();
            if (controller.deletar_projeto(id)) {
                JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao excluir.");
            }
        }
    }

    // ===========================================================
    // BUSCAR
    // ===========================================================
    private void buscarProjeto() {
        try {
            String s = JOptionPane.showInputDialog("ID do projeto:");
            if (s == null || s.isEmpty()) return;

            int id = Integer.parseInt(s);

            Controller_Projeto_01 controller = new Controller_Projeto_01();
            Model_Projeto_01 p = controller.buscar_projeto(id);

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Projeto não encontrado.");
                return;
            }

            preencherFormulario(p);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    // ===========================================================
    // MÉTODOS AUXILIARES
    // ===========================================================
    private Model_Projeto_01 montarModelDoFormulario() {
        Model_Projeto_01 m = new Model_Projeto_01();

        m.setA01_nome_projeto(txtNomeProjeto.getText().trim());
        m.setA01_descricao(txtDescricao.getText());

        m.setA01_data_inicial(java.sql.Date.valueOf(textField1dataIni.getText().trim()));

        String dataF = txtDataFinal.getText().trim();
        m.setA01_data_final(dataF.isEmpty() ? null : java.sql.Date.valueOf(dataF));

        m.setA01_orcamento(Double.parseDouble(txtOrcamento.getText().trim()));
        m.setA01_status_projeto(cmbStatus.getSelectedItem().toString());
        m.setA01_departamento(txtDepartamento.getText().trim());

        String user = cmbUsuario.getSelectedItem().toString();
        m.setA01_id_usuario(Integer.parseInt(user.split(" - ")[0]));

        return m;
    }

    private void preencherFormulario(Model_Projeto_01 m) {
        txtNomeProjeto.setText(m.getA01_nome_projeto());
        txtDescricao.setText(m.getA01_descricao());
        textField1dataIni.setText(m.getA01_data_inicial() != null ? m.getA01_data_inicial().toString() : "");
        txtDataFinal.setText(m.getA01_data_final() != null ? m.getA01_data_final().toString() : "");
        txtOrcamento.setText(String.valueOf(m.getA01_orcamento()));
        txtDepartamento.setText(m.getA01_departamento());
        cmbStatus.setSelectedItem(m.getA01_status_projeto());

        for (int i = 0; i < cmbUsuario.getItemCount(); i++) {
            if (cmbUsuario.getItemAt(i).startsWith(m.getA01_id_usuario() + " - ")) {
                cmbUsuario.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparCampos() {
        txtNomeProjeto.setText("");
        txtDescricao.setText("");
        textField1dataIni.setText("");
        txtDataFinal.setText("");
        txtOrcamento.setText("");
        txtDepartamento.setText("");
        cmbStatus.setSelectedIndex(0);
        if (cmbUsuario.getItemCount() > 0) cmbUsuario.setSelectedIndex(0);
    }
}