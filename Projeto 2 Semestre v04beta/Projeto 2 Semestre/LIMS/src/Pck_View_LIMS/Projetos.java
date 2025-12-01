// Código revisado e otimizado conforme solicitado
// Mantidos todos os nomes originais para não quebrar o .form

package Pck_View_LIMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_DAO_LIMS.DAO_Conexao;

public class Projetos extends JDialog {

    private JPanel contentPane;

    // Campos
    private JTextField txtNomeProjeto;
    private JTextField txtDepartamento;
    private JFormattedTextField textField1dataIni;
    private JFormattedTextField txtDataFinal;
    private JTextField txtOrcamento;

    private JLabel txtDescricao;
    private JLabel dataInicialLabel;
    private JLabel JLabelnomeLabel;
    private JEditorPane editorPane1Descri;

    // Combos
    private JComboBox<String> cmbStatus;
    private JComboBox<String> cmbUsuario;

    // Botões
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;

    // Tabela
    private JTable table1geral;
    private DefaultTableModel tableModel;

    public Projetos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Projetos");

        inicializarTabela();
        carregarStatus();
        carregarUsuarios();
        preencherTabela();
        aplicarMascaraDatas();


        salvarButton.addActionListener(e -> salvarProjeto());
        editarButton.addActionListener(e -> editarProjeto());
        excluirButton.addActionListener(e -> excluirProjeto());
        buscarButton.addActionListener(e -> buscarProjeto());
        sairButton.addActionListener(e -> dispose());

        // Clique na tabela
        table1geral.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                carregarCamposDaTabela();
            }
        });
    }

    // ==========================
    // CARREGAR COMBO STATUS
    // ==========================
    private void carregarStatus() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
        cmbStatus.addItem("CONCLUIDO");
        cmbStatus.addItem("CANCELADO");
    }

    // ==========================
    // CARREGAR USUARIOS
    // ==========================
    private void carregarUsuarios() {
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement("SELECT A11_ID_USUARIO, A11_NOME FROM USUARIO_11");
             ResultSet rs = ps.executeQuery()) {

            cmbUsuario.removeAllItems();

            while (rs.next()) {
                cmbUsuario.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // ==========================
    // INICIALIZAR TABELA
    // ==========================
    private void inicializarTabela() {

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Data Inicial", "Data Final", "Orçamento", "Status", "Departamento", "Usuário"},
                0
        );

        table1geral.setModel(tableModel);
        table1geral.setDefaultEditor(Object.class, null); // somente leitura
        table1geral.setAutoCreateRowSorter(true);
    }

    // ==========================
    // PREENCHER TABELA
    // ==========================
    private void preencherTabela() {

        try {
            Controller_Projeto_01 controller = new Controller_Projeto_01();
            ArrayList<Model_Projeto_01> lista = controller.listar_projeto();

            tableModel.setRowCount(0);

            for (Model_Projeto_01 p : lista) {

                String dataInicialFormatada = converterDataParaTela(p.getA01_data_inicial());
                String dataFinalFormatada   = converterDataParaTela(p.getA01_data_final());
                String orcamentoFormatado   = formatarValorBR(p.getA01_orcamento());

                tableModel.addRow(new Object[]{
                        p.getA01_id_projeto(),
                        p.getA01_nome_projeto(),
                        dataInicialFormatada,
                        dataFinalFormatada,
                        orcamentoFormatado,
                        p.getA01_status_projeto(),
                        p.getA01_departamento(),
                        p.getA01_id_usuario()
                });
            }

            tableModel.fireTableDataChanged();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
        }
    }
    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf1 = new MaskFormatter("##/##/####");
            mf1.setPlaceholderCharacter('_');
            mf1.install(textField1dataIni);  // ✅ sem cast

            MaskFormatter mf2 = new MaskFormatter("##/##/####");
            mf2.setPlaceholderCharacter('_');
            mf2.install(txtDataFinal);        // ✅ sem cast

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

    private double converterValor(String texto) {
        if (texto == null) return -1;

        String t = texto.replace("R$", "")
                .replace(" ", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();
        try {
            return Double.parseDouble(t);
        } catch (Exception e) {
            return -1;
        }
    }

    private String formatarValorBR(double valor) {
        Locale localeBR = Locale.of("pt", "BR");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(localeBR);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + df.format(valor);
    }
    // ==========================
    // SALVAR PROJETO
    // ==========================
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

    // ==========================
    // EDITAR PROJETO
    // ==========================
    private void editarProjeto() {

        int linha = table1geral.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto.");
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);

        Controller_Projeto_01 controller = new Controller_Projeto_01();
        Model_Projeto_01 atual = controller.buscar_projeto(id);

        if (atual == null) {
            JOptionPane.showMessageDialog(null, "Projeto não encontrado.");
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Salvar alterações?", "Confirmar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            Model_Projeto_01 novo = montarModelDoFormulario();
            novo.setA01_id_projeto(id);

            if (controller.atualizar_projeto(novo)) {
                JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                preencherTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar.");
            }
        }
    }

    // ==========================
    // EXCLUIR
    // ==========================
    private void excluirProjeto() {
        int linha = table1geral.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto.");
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);

        if (JOptionPane.showConfirmDialog(null, "Excluir projeto?", "Confirmar", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            Controller_Projeto_01 controller = new Controller_Projeto_01();

            if (controller.deletar_projeto(id)) {
                JOptionPane.showMessageDialog(null, "Excluído!");
                preencherTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao excluir.");
            }
        }
    }

    // ==========================
    // BUSCAR — ID VIA INPUT
    // ==========================
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

    // ==========================
    // Montar Model do Formulário
    // ==========================
    private Model_Projeto_01 montarModelDoFormulario() {
        Model_Projeto_01 m = new Model_Projeto_01();

        m.setA01_nome_projeto(txtNomeProjeto.getText().trim());
        m.setA01_descricao(editorPane1Descri.getText());

        try {
            Date dataIni = converterParaDateSQL(textField1dataIni.getText());
            if (dataIni == null) {
                JOptionPane.showMessageDialog(null, "Data inicial inválida!");
                return null;
            }
            m.setA01_data_inicial(dataIni);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data inicial inválida! Use AAAA-MM-DD.");
            return null;
        }

        String dataF = txtDataFinal.getText().trim();
        try {
            Date dataFim = converterParaDateSQL(txtDataFinal.getText());
            m.setA01_data_final(dataFim);        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data final inválida! Use AAAA-MM-DD.");
            return null;
        }

        try {
            double valor = converterValor(txtOrcamento.getText());
            if (valor < 0) {
                JOptionPane.showMessageDialog(null, "Orçamento inválido!");
                return null;
            }
            m.setA01_orcamento(valor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Orçamento inválido!");
            return null;
        }

        m.setA01_status_projeto(cmbStatus.getSelectedItem().toString());
        m.setA01_departamento(txtDepartamento.getText().trim());

        String user = cmbUsuario.getSelectedItem().toString();
        m.setA01_id_usuario(Integer.parseInt(user.split(" - ")[0]));

        return m;
    }

    // ==========================
    // Preencher Formulário
    // ==========================
    private void preencherFormulario(Model_Projeto_01 m) {

        textField1dataIni.setText(converterDataParaTela(m.getA01_data_inicial()));
        txtDataFinal.setText(converterDataParaTela(m.getA01_data_final()));

        txtOrcamento.setText(formatarValorBR(m.getA01_orcamento()));
        txtDepartamento.setText(m.getA01_departamento());
        cmbStatus.setSelectedItem(m.getA01_status_projeto());

        // Selecionar usuário
        for (int i = 0; i < cmbUsuario.getItemCount(); i++) {
            if (cmbUsuario.getItemAt(i).startsWith(m.getA01_id_usuario() + " - ")) {
                cmbUsuario.setSelectedIndex(i);
                break;
            }
        }
    }

    // ==========================
    // Clique na tabela → preencher
    // ==========================
    private void carregarCamposDaTabela() {

        int linha = table1geral.getSelectedRow();
        if (linha < 0) return;

        txtNomeProjeto.setText(tableModel.getValueAt(linha, 1).toString());
        textField1dataIni.setText(tableModel.getValueAt(linha, 2).toString());
        txtDataFinal.setText(tableModel.getValueAt(linha, 3) != null ? tableModel.getValueAt(linha, 3).toString() : "");
        txtOrcamento.setText(tableModel.getValueAt(linha, 4).toString());
        cmbStatus.setSelectedItem(tableModel.getValueAt(linha, 5).toString());
        txtDepartamento.setText(tableModel.getValueAt(linha, 6).toString());

        int userId = (int) tableModel.getValueAt(linha, 7);

        for (int i = 0; i < cmbUsuario.getItemCount(); i++) {
            if (cmbUsuario.getItemAt(i).startsWith(userId + " - ")) {
                cmbUsuario.setSelectedIndex(i);
                break;
            }
        }

        // Buscar descrição do banco (não está na tabela)
        Controller_Projeto_01 controller = new Controller_Projeto_01();
        Model_Projeto_01 p = controller.buscar_projeto((int) tableModel.getValueAt(linha, 0));
        if (p != null) editorPane1Descri.setText(p.getA01_descricao());
    }

    // ==========================
    // Limpar Campos
    // ==========================
    private void limparCampos() {
        txtNomeProjeto.setText("");
        editorPane1Descri.setText("");
        textField1dataIni.setText("");
        txtDataFinal.setText("");
        txtOrcamento.setText("");
        txtDepartamento.setText("");
        cmbStatus.setSelectedIndex(0);
        if (cmbUsuario.getItemCount() > 0) cmbUsuario.setSelectedIndex(0);
        table1geral.clearSelection();
    }
}
