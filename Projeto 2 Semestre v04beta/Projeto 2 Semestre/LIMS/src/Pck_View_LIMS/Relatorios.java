package Pck_View_LIMS;
import Pck_Controller_LIMS.Controller_Relatorio_08;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Controller_LIMS.Controller_Usuario_11;

import Pck_Model_LIMS.Model_Relatorio_08;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Usuario_11;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;

public class Relatorios extends JDialog {

    private JPanel contentPane;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;

    private JTable table1Geral;
    private JTextField textField2Titulo;
    private JEditorPane editorPane1Conteudo;
    private JTextField textField6DataGeracao;
    private JComboBox comboBox1Id_Usuario;
    private JComboBox comboBox3NomeProjeto;
    private JTextField textField2IdRelatorio;

    private DefaultTableModel tableModel;

    private Controller_Relatorio_08 controller;
    private Controller_Usuario_11 controllerUsuario;
    private Controller_Projeto_01 controllerProjeto;

    private int relatorioSelecionado = -1;

    public Relatorios() {

        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Relatórios");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        controller = new Controller_Relatorio_08();
        controllerUsuario = new Controller_Usuario_11();
        controllerProjeto = new Controller_Projeto_01();

        configurarTabela();
        carregarCombos();
        carregarTabela();

        salvarButton.addActionListener(e -> salvarRelatorio());
        editarButton.addActionListener(e -> editarRelatorio());
        excluirButton.addActionListener(e -> excluirRelatorio());
        buscarButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textField2IdRelatorio.getText());

                Model_Relatorio_08 m = controller.buscar(id);

                if (m != null) {

                    tableModel.setRowCount(0); // limpa tabela

                    tableModel.addRow(new Object[]{
                            m.getA08_id_relatorio(),
                            m.getA08_titulo(),
                            m.getA08_data_geracao(),
                            m.getA08_id_usuario(),
                            m.getA08_id_projeto()
                    });

                } else {
                    JOptionPane.showMessageDialog(null, "ID não encontrado.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido.");
            }
        });        sairButton.addActionListener(e -> dispose());

        table1Geral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    carregarCamposDaLinha();
                }
            }
        });
    }

    // -------------------------------------
    // CONFIGURAÇÃO DA TABELA
    // -------------------------------------
    private void configurarTabela() {

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Título", "Data", "Usuário", "Projeto"}, // <-- sem conteúdo
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1Geral.setModel(tableModel);

        // Ajustar largura das colunas
        table1Geral.getColumnModel().getColumn(0).setPreferredWidth(40);
        table1Geral.getColumnModel().getColumn(1).setPreferredWidth(200);
        table1Geral.getColumnModel().getColumn(2).setPreferredWidth(80);
        table1Geral.getColumnModel().getColumn(3).setPreferredWidth(80);
        table1Geral.getColumnModel().getColumn(4).setPreferredWidth(80);
    }
    // -------------------------------------
    // CARREGAR COMBOS
    // -------------------------------------
    private void carregarCombos() {

        // Usuários
        comboBox1Id_Usuario.removeAllItems();
        for (Model_Usuario_11 u : controllerUsuario.listar()) {
            comboBox1Id_Usuario.addItem(u.getA11_id_usuario() + " - " + u.getA11_nome());
        }

        // Projetos
        comboBox3NomeProjeto.removeAllItems();
        for (Model_Projeto_01 p : controllerProjeto.listar_projeto()) {
            comboBox3NomeProjeto.addItem(p.getA01_id_projeto() + " - " + p.getA01_nome_projeto());
        }
    }

    // -------------------------------------
    // CARREGAR TABELA
    // -------------------------------------
    private void carregarTabela() {
        tableModel.setRowCount(0); // limpa a tabela

        List<Model_Relatorio_08> lista = controller.listar();

        for (Model_Relatorio_08 m : lista) {

            // Buscar usuário
            Model_Usuario_11 user = controllerUsuario.buscar(m.getA08_id_usuario());
            String usuarioTexto = m.getA08_id_usuario() + " - " + user.getA11_nome();

            // Buscar projeto
            Model_Projeto_01 proj = controllerProjeto.buscar_projeto(m.getA08_id_projeto());
            String projetoTexto = m.getA08_id_projeto() + " - " + proj.getA01_nome_projeto();

            tableModel.addRow(new Object[]{
                    m.getA08_id_relatorio(),
                    m.getA08_titulo(),
                    m.getA08_data_geracao(),
                    usuarioTexto,
                    projetoTexto
            });
        }
    }
    // -------------------------------------
    // SALVAR RELATÓRIO
    // -------------------------------------
    private void salvarRelatorio() {
        try {
            Model_Relatorio_08 m = new Model_Relatorio_08();

            m.setA08_titulo(textField2Titulo.getText());
            m.setA08_conteudo(editorPane1Conteudo.getText());
            m.setA08_data_geracao(Date.valueOf(textField6DataGeracao.getText()));

            int idUsuario = Integer.parseInt(comboBox1Id_Usuario.getSelectedItem().toString().split(" - ")[0]);
            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);

            m.setA08_id_usuario(idUsuario);
            m.setA08_id_projeto(idProjeto);

            boolean ok = controller.inserir(m);

            if (ok) {
                JOptionPane.showMessageDialog(null, "Relatório cadastrado!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // -------------------------------------
    // EDITAR RELATÓRIO
    // -------------------------------------
    private void editarRelatorio() {

        if (relatorioSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um relatório.");
            return;
        }

        try {
            Model_Relatorio_08 m = new Model_Relatorio_08();

            m.setA08_id_relatorio(relatorioSelecionado);
            m.setA08_titulo(textField2Titulo.getText());
            m.setA08_conteudo(editorPane1Conteudo.getText());
            m.setA08_data_geracao(Date.valueOf(textField6DataGeracao.getText()));

            int idUsuario = Integer.parseInt(comboBox1Id_Usuario.getSelectedItem().toString().split(" - ")[0]);
            int idProjeto = Integer.parseInt(comboBox3NomeProjeto.getSelectedItem().toString().split(" - ")[0]);

            m.setA08_id_usuario(idUsuario);
            m.setA08_id_projeto(idProjeto);

            boolean ok = controller.atualizar(m);

            if (ok) {
                JOptionPane.showMessageDialog(null, "Relatório atualizado!");
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // -------------------------------------
    // EXCLUIR RELATÓRIO
    // -------------------------------------
    private void excluirRelatorio() {

        if (relatorioSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um relatório.");
            return;
        }

        if (controller.excluir(relatorioSelecionado)) {
            JOptionPane.showMessageDialog(null, "Relatório excluído.");
            carregarTabela();
        }
    }

    // -------------------------------------
    // CARREGAR CAMPOS QUANDO CLICAR NA LINHA
    // -------------------------------------
    private void carregarCamposDaLinha() {

        int row = table1Geral.getSelectedRow();
        if (row == -1) return;

        // ID selecionado
        relatorioSelecionado = (int) tableModel.getValueAt(row, 0);

        try {
            // Buscar o relatório completo no banco (para pegar o conteúdo)
            Model_Relatorio_08 m = controller.buscar(relatorioSelecionado);

            textField2Titulo.setText(m.getA08_titulo());
            editorPane1Conteudo.setText(m.getA08_conteudo());
            textField6DataGeracao.setText(String.valueOf(m.getA08_data_geracao()));

            // Selecionar usuário no combo
            for (int i = 0; i < comboBox1Id_Usuario.getItemCount(); i++) {
                if (comboBox1Id_Usuario.getItemAt(i).toString()
                        .startsWith(m.getA08_id_usuario() + " - ")) {
                    comboBox1Id_Usuario.setSelectedIndex(i);
                    break;
                }
            }

            // Selecionar projeto no combo
            for (int i = 0; i < comboBox3NomeProjeto.getItemCount(); i++) {
                if (comboBox3NomeProjeto.getItemAt(i).toString()
                        .startsWith(m.getA08_id_projeto() + " - ")) {
                    comboBox3NomeProjeto.setSelectedIndex(i);
                    break;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar dados para edição: " + e.getMessage());
        }
    }
}
