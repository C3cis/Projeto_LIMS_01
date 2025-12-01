package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Manutencao_05;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Controller_LIMS.Controller_Localizacao_07;
import Pck_Controller_LIMS.Controller_Produto_02;

import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Manutencao_05;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Usuario_11;
import Pck_Model_LIMS.Model_Localizacao_07;
import Pck_Model_LIMS.Model_Produto_02;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Manutencao extends JDialog {
    private JPanel contentPane;
    private JLabel nomeLabel;
    private JLabel descricaoLabel;
    private JEditorPane editorPane1Descricao;
    private JLabel dataInicialLabel;
    private JFormattedTextField textField1Data_Geracao;
    private JComboBox comboBox1; // status_resultado
    private JComboBox comboBox2ID_Projeto;
    private JTextField textField5TipoManutencao;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JEditorPane editorPane2Relatorio;
    private JComboBox comboBox4ID_Usuario;
    private JComboBox comboBox5ID_Localizacao;
    private JComboBox comboBox3ID_Produto;

    private DefaultTableModel tableModel;

    private Controller_Manutencao_05 controller;
    private Controller_Projeto_01 controllerProjeto;
    private Controller_Usuario_11 controllerUsuario;
    private Controller_Localizacao_07 controllerLocalizacao;
    private Controller_Produto_02 controllerProduto;

    public Manutencao() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Manutenção");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        controller = new Controller_Manutencao_05();
        controllerProjeto = new Controller_Projeto_01();
        controllerUsuario = new Controller_Usuario_11();
        controllerLocalizacao = new Controller_Localizacao_07();
        controllerProduto = new Controller_Produto_02(DAO_Conexao.connect());

        configurarTabela();
        carregarCombos();
        carregarTabela();
        aplicarMascaraDatas();

        salvarButton.addActionListener(e -> salvarManutencao());
        editarButton.addActionListener(e -> editarManutencao());
        excluirButton.addActionListener(e -> excluirManutencao());
        buscarButton.addActionListener(e -> buscarManutencao());
        sairButton.addActionListener(e -> dispose());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    carregarDadosParaEdicao();
                }
            }
        });
    }

    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Data", "Tipo", "Descrição", "Status", "Projeto", "Usuário", "Localização", "Produto"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1.setModel(tableModel);

        // Ajuste de larguras (opcional)
        if (table1.getColumnModel().getColumnCount() >= 9) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
            table1.getColumnModel().getColumn(1).setPreferredWidth(100);  // Data
            table1.getColumnModel().getColumn(2).setPreferredWidth(130);  // Tipo
            table1.getColumnModel().getColumn(3).setPreferredWidth(220);  // Descrição
            table1.getColumnModel().getColumn(4).setPreferredWidth(120);  // Status
            table1.getColumnModel().getColumn(5).setPreferredWidth(150);  // Projeto
            table1.getColumnModel().getColumn(6).setPreferredWidth(150);  // Usuário
            table1.getColumnModel().getColumn(7).setPreferredWidth(150);  // Localização
            table1.getColumnModel().getColumn(8).setPreferredWidth(150);  // Produto
        }
    }
    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            mf.install(textField1Data_Geracao);  // Campo da data de manutenção
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Date converterParaDateSQL(String dataStr) {
        try {
            if (dataStr == null) return null;
            dataStr = dataStr.trim();
            if (dataStr.isEmpty() || dataStr.contains("_")) return null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date d = sdf.parse(dataStr);
            return new Date(d.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private String converterDataParaTela(Date dataSQL) {
        if (dataSQL == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(dataSQL);
    }
    private void carregarCombos() {
        comboBox2ID_Projeto.removeAllItems();
        comboBox4ID_Usuario.removeAllItems();
        comboBox5ID_Localizacao.removeAllItems();
        comboBox3ID_Produto.removeAllItems();

        // Projetos -> "ID - nome"
        ArrayList<Model_Projeto_01> projetos = controllerProjeto.listar_projeto();
        if (projetos != null) {
            for (Model_Projeto_01 pr : projetos) {
                comboBox2ID_Projeto.addItem(pr.getA01_id_projeto() + " - " + pr.getA01_nome_projeto());
            }
        }

        // Usuários -> "ID - nome"
        ArrayList<Model_Usuario_11> usuarios = controllerUsuario.listar();
        if (usuarios != null) {
            for (Model_Usuario_11 u : usuarios) {
                comboBox4ID_Usuario.addItem(u.getA11_id_usuario() + " - " + u.getA11_nome());
            }
        }

        // Localizações -> "ID - descricao" (ajuste getter se necessário)
        List<Model_Localizacao_07> locais = controllerLocalizacao.listar();
        if (locais != null) {
            for (Model_Localizacao_07 loc : locais) {
                comboBox5ID_Localizacao.addItem(loc.getA07_id_localizacao() + " - " + loc.getA07_setor());
            }
        }

        // Produtos -> "ID - nome"
        ArrayList<Model_Produto_02> produtos = controllerProduto.listarProdutos();
        if (produtos != null) {
            for (Model_Produto_02 prod : produtos) {
                comboBox3ID_Produto.addItem(prod.getA02_id_produto() + " - " + prod.getA02_nome_produto());
            }
        }

        // Status (comboBox1) - resultado da manutenção
        comboBox1.removeAllItems();
        comboBox1.addItem("PENDENTE");
        comboBox1.addItem("EM ANDAMENTO");
        comboBox1.addItem("CONCLUÍDO");
        comboBox1.addItem("CANCELADO");
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);

        ArrayList<Model_Manutencao_05> lista = controller.listar_manutencao();
        if (lista == null) return;

        for (Model_Manutencao_05 m : lista) {
            // Projeto
            String projetoTexto = String.valueOf(m.getA05_id_projeto());
            try {
                Model_Projeto_01 pr = controllerProjeto.buscar_projeto(m.getA05_id_projeto());
                if (pr != null) projetoTexto = m.getA05_id_projeto() + " - " + pr.getA01_nome_projeto();
            } catch (Exception ex) {
                projetoTexto = String.valueOf(m.getA05_id_projeto());
            }

            // Usuario
            String usuarioTexto = String.valueOf(m.getA05_id_usuario());
            try {
                Model_Usuario_11 u = controllerUsuario.buscar(m.getA05_id_usuario());
                if (u != null) usuarioTexto = m.getA05_id_usuario() + " - " + u.getA11_nome();
            } catch (Exception ex) {
                usuarioTexto = String.valueOf(m.getA05_id_usuario());
            }

            // Localizacao
            String localTexto = String.valueOf(m.getA05_id_localizacao());
            try {
                Model_Localizacao_07 loc = controllerLocalizacao.buscar(m.getA05_id_localizacao());
                if (loc != null) localTexto = m.getA05_id_localizacao() + " - " + loc.getA07_setor();
            } catch (Exception ex) {
                localTexto = String.valueOf(m.getA05_id_localizacao());
            }

            // Produto
            String produtoTexto = String.valueOf(m.getA05_id_produto());
            try {
                Model_Produto_02 prod = controllerProduto.buscarProduto(m.getA05_id_produto());
                if (prod != null) produtoTexto = m.getA05_id_produto() + " - " + prod.getA02_nome_produto();
            } catch (Exception ex) {
                produtoTexto = String.valueOf(m.getA05_id_produto());
            }

            tableModel.addRow(new Object[]{
                    m.getA05_id_manutencao(),
                    converterDataParaTela(m.getA05_data_manutencao()),  // <- formato dd/MM/yyyy
                    m.getA05_tipo_manutencao(),
                    m.getA05_descricao(),
                    m.getA05_status_resultado(),
                    projetoTexto,
                    usuarioTexto,
                    localTexto,
                    produtoTexto
            });
        }
    }

    private void salvarManutencao() {
        try {
            Model_Manutencao_05 m = new Model_Manutencao_05();

            // Data (valide formato no UI: yyyy-MM-dd)
            if (textField1Data_Geracao.getText() != null && !textField1Data_Geracao.getText().trim().isEmpty()) {
                Date dataMan = converterParaDateSQL(textField1Data_Geracao.getText());
                if (dataMan == null) {
                    JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy.");
                    return;
                }
                m.setA05_data_manutencao(dataMan);            } else {
                m.setA05_data_manutencao(null);
            }

            // Tipo -> agora vindo do textField5TipoManutencao
            m.setA05_tipo_manutencao((textField5TipoManutencao.getText() != null) ? textField5TipoManutencao.getText().trim() : "");

            // Descrição
            m.setA05_descricao(editorPane1Descricao.getText());

            // Status (comboBox1)
            Object statusObj = comboBox1.getSelectedItem();
            m.setA05_status_resultado((statusObj != null) ? statusObj.toString() : "");

            // Relatório (texto separado, não aparece na tabela)
            m.setA05_relatorio(editorPane2Relatorio.getText());

            // Projeto
            Object projSel = comboBox2ID_Projeto.getSelectedItem();
            if (projSel != null && projSel.toString().contains(" - ")) {
                m.setA05_id_projeto(Integer.parseInt(projSel.toString().split(" - ")[0]));
            }

            // Usuario
            Object userSel = comboBox4ID_Usuario.getSelectedItem();
            if (userSel != null && userSel.toString().contains(" - ")) {
                m.setA05_id_usuario(Integer.parseInt(userSel.toString().split(" - ")[0]));
            }

            // Localizacao
            Object locSel = comboBox5ID_Localizacao.getSelectedItem();
            if (locSel != null && locSel.toString().contains(" - ")) {
                m.setA05_id_localizacao(Integer.parseInt(locSel.toString().split(" - ")[0]));
            }

            // Produto
            Object prodSel = comboBox3ID_Produto.getSelectedItem();
            if (prodSel != null && prodSel.toString().contains(" - ")) {
                m.setA05_id_produto(Integer.parseInt(prodSel.toString().split(" - ")[0]));
            }

            boolean sucesso = controller.inserir_manutencao(m);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Manutenção salva com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar manutenção.");
            }
        } catch (IllegalArgumentException ie) {
            // Date.valueOf lança IllegalArgumentException se formato inválido
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void editarManutencao() {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma manutenção para editar.");
            return;
        }

        try {
            Model_Manutencao_05 m = new Model_Manutencao_05();

            m.setA05_id_manutencao((int) tableModel.getValueAt(row, 0));

            if (textField1Data_Geracao.getText() != null && !textField1Data_Geracao.getText().trim().isEmpty()) {
                Date dataMan = converterParaDateSQL(textField1Data_Geracao.getText());
                if (dataMan == null) {
                    JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy.");
                    return;
                }
                m.setA05_data_manutencao(dataMan);            } else {
                m.setA05_data_manutencao(null);
            }

            m.setA05_tipo_manutencao((textField5TipoManutencao.getText() != null) ? textField5TipoManutencao.getText().trim() : "");
            m.setA05_descricao(editorPane1Descricao.getText());

            Object statusObj = comboBox1.getSelectedItem();
            m.setA05_status_resultado((statusObj != null) ? statusObj.toString() : "");

            m.setA05_relatorio(editorPane2Relatorio.getText());

            Object projSel = comboBox2ID_Projeto.getSelectedItem();
            if (projSel != null && projSel.toString().contains(" - ")) {
                m.setA05_id_projeto(Integer.parseInt(projSel.toString().split(" - ")[0]));
            }

            Object userSel = comboBox4ID_Usuario.getSelectedItem();
            if (userSel != null && userSel.toString().contains(" - ")) {
                m.setA05_id_usuario(Integer.parseInt(userSel.toString().split(" - ")[0]));
            }

            Object locSel = comboBox5ID_Localizacao.getSelectedItem();
            if (locSel != null && locSel.toString().contains(" - ")) {
                m.setA05_id_localizacao(Integer.parseInt(locSel.toString().split(" - ")[0]));
            }

            Object prodSel = comboBox3ID_Produto.getSelectedItem();
            if (prodSel != null && prodSel.toString().contains(" - ")) {
                m.setA05_id_produto(Integer.parseInt(prodSel.toString().split(" - ")[0]));
            }

            boolean sucesso = controller.atualizar_manutencao(m);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Manutenção atualizada!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar manutenção.");
            }
        } catch (IllegalArgumentException ie) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void excluirManutencao() {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma manutenção para excluir.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        boolean sucesso = controller.deletar_manutencao(id);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Manutenção excluída!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir manutenção.");
        }
    }

    private void buscarManutencao() {
        String input = JOptionPane.showInputDialog("Digite o ID da manutenção:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input.trim());
            Model_Manutencao_05 m = controller.buscar_manutencao(id);

            if (m == null) {
                JOptionPane.showMessageDialog(null, "Manutenção não encontrada.");
                return;
            }

            // popular campos
            if (m.getA05_data_manutencao() != null) textField1Data_Geracao.setText(m.getA05_data_manutencao().toString()); else textField1Data_Geracao.setText("");
            textField5TipoManutencao.setText((m.getA05_tipo_manutencao() != null) ? m.getA05_tipo_manutencao() : "");
            editorPane1Descricao.setText((m.getA05_descricao() != null) ? m.getA05_descricao() : "");
            editorPane2Relatorio.setText((m.getA05_relatorio() != null) ? m.getA05_relatorio() : "");

            // selecionar projeto no combo
            for (int i = 0; i < comboBox2ID_Projeto.getItemCount(); i++) {
                if (comboBox2ID_Projeto.getItemAt(i).toString().startsWith(m.getA05_id_projeto() + " -")) {
                    comboBox2ID_Projeto.setSelectedIndex(i);
                    break;
                }
            }

            // usuário
            for (int i = 0; i < comboBox4ID_Usuario.getItemCount(); i++) {
                if (comboBox4ID_Usuario.getItemAt(i).toString().startsWith(m.getA05_id_usuario() + " -")) {
                    comboBox4ID_Usuario.setSelectedIndex(i);
                    break;
                }
            }

            // localizacao
            for (int i = 0; i < comboBox5ID_Localizacao.getItemCount(); i++) {
                if (comboBox5ID_Localizacao.getItemAt(i).toString().startsWith(m.getA05_id_localizacao() + " -")) {
                    comboBox5ID_Localizacao.setSelectedIndex(i);
                    break;
                }
            }

            // produto
            for (int i = 0; i < comboBox3ID_Produto.getItemCount(); i++) {
                if (comboBox3ID_Produto.getItemAt(i).toString().startsWith(m.getA05_id_produto() + " -")) {
                    comboBox3ID_Produto.setSelectedIndex(i);
                    break;
                }
            }

            // status (comboBox1)
            comboBox1.setSelectedItem((m.getA05_status_resultado() != null) ? m.getA05_status_resultado() : null);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void carregarDadosParaEdicao() {
        int row = table1.getSelectedRow();
        if (row == -1) return;

        int id = (int) tableModel.getValueAt(row, 0);
        Model_Manutencao_05 m = controller.buscar_manutencao(id);
        if (m == null) return;

        // preencher campos
        if (m.getA05_data_manutencao() != null)
            textField1Data_Geracao.setText(converterDataParaTela(m.getA05_data_manutencao()));
        else
            textField1Data_Geracao.setText("");

        // relatório (não aparece na tabela)
        editorPane2Relatorio.setText((m.getA05_relatorio() != null) ? m.getA05_relatorio() : "");

        // selecionar combos
        for (int i = 0; i < comboBox2ID_Projeto.getItemCount(); i++) {
            if (comboBox2ID_Projeto.getItemAt(i).toString().startsWith(m.getA05_id_projeto() + " -")) {
                comboBox2ID_Projeto.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboBox4ID_Usuario.getItemCount(); i++) {
            if (comboBox4ID_Usuario.getItemAt(i).toString().startsWith(m.getA05_id_usuario() + " -")) {
                comboBox4ID_Usuario.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboBox5ID_Localizacao.getItemCount(); i++) {
            if (comboBox5ID_Localizacao.getItemAt(i).toString().startsWith(m.getA05_id_localizacao() + " -")) {
                comboBox5ID_Localizacao.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboBox3ID_Produto.getItemCount(); i++) {
            if (comboBox3ID_Produto.getItemAt(i).toString().startsWith(m.getA05_id_produto() + " -")) {
                comboBox3ID_Produto.setSelectedIndex(i);
                break;
            }
        }

        // status
        comboBox1.setSelectedItem((m.getA05_status_resultado() != null) ? m.getA05_status_resultado() : null);
    }
}