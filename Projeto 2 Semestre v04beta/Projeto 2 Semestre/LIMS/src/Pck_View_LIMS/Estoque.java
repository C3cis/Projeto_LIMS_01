package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Estoque_06;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Estoque_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Estoque extends JDialog {

    private JPanel contentPane;
    private JFormattedTextField textField2DATA_ENTRADA;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;
    private JTable table1;
    private JComboBox<String> comboBox1ID_Produto;
    private JComboBox<String> comboBox2LOCAL;
    private JSpinner spinner1QUANTIDADE;

    private Controller_Estoque_06 controller = new Controller_Estoque_06();

    private final SimpleDateFormat sdfTela  = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd");

    public Estoque() {

        setContentPane(contentPane);
        setModal(true);
        setTitle("Controle de Estoque");
        setSize(900,600);
        setLocationRelativeTo(null);

        configurarTabela();
        aplicarMascaraDatas();
        carregarCombos();
        preencherTabela();

        salvarButton.addActionListener(e -> onSalvar());
        editarButton.addActionListener(e -> onEditar());
        excluirButton.addActionListener(e -> onExcluir());
        buscarButton.addActionListener(e -> onBuscar());
        sairButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // ✅ Evento correto para pegar dados da tabela
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                carregarLinhaSelecionada();
            }
        });
    }

    // ===========================================================
    // CONFIGURAÇÃO DA TABELA
    // ===========================================================
    private void configurarTabela(){
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Quantidade", "Data Entrada", "Produto", "Local"}
        ));
    }

    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            mf.install(textField2DATA_ENTRADA);
            textField2DATA_ENTRADA.setFocusLostBehavior(JFormattedTextField.COMMIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================
    // COMBOS
    // ===========================================================
    private void carregarCombos() {

        // PRODUTOS
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT A02_ID_PRODUTO, A02_NOME_PRODUTO FROM PRODUTO_02 ORDER BY A02_NOME_PRODUTO");
             ResultSet rs = ps.executeQuery()) {

            comboBox1ID_Produto.removeAllItems();

            while (rs.next()){
                comboBox1ID_Produto.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro combo produtos: " + e.getMessage());
        }

        // LOCAIS
        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT A07_ID_LOCALIZACAO, A07_IDENTIFICACAO FROM LOCALIZACAO_07 ORDER BY A07_IDENTIFICACAO");
             ResultSet rs = ps.executeQuery()) {

            comboBox2LOCAL.removeAllItems();

            while (rs.next()){
                comboBox2LOCAL.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro combo locais: " + e.getMessage());
        }
    }

    private int pegarIdCombo(JComboBox<String> combo){
        if(combo.getSelectedItem() == null) return 0;
        String valor = combo.getSelectedItem().toString();
        return Integer.parseInt(valor.split(" - ")[0].trim());
    }

    private void selecionarCombo(JComboBox<String> combo, int id){
        for(int i=0;i<combo.getItemCount();i++){
            if(combo.getItemAt(i).startsWith(id + " -")){
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    // ===========================================================
    // DATA
    // ===========================================================
    private Date pegarData() throws Exception {

        String dataTexto = textField2DATA_ENTRADA.getText().trim();

        if (dataTexto.isEmpty() || dataTexto.contains("_")) return null;

        Date data = sdfTela.parse(dataTexto);
        String dataBanco = sdfBanco.format(data);

        return java.sql.Date.valueOf(dataBanco);
    }

    // ===========================================================
    // BOTÕES
    // ===========================================================
    private void onSalvar() {

        try {
            Model_Estoque_06 m = new Model_Estoque_06();

            m.setA06_quantidade((Integer) spinner1QUANTIDADE.getValue());
            m.setA06_data_entrada(pegarData());
            m.setA06_id_produto(pegarIdCombo(comboBox1ID_Produto));
            m.setA06_id_localizacao(pegarIdCombo(comboBox2LOCAL));

            if (controller.inserirEstoque(m)) {
                JOptionPane.showMessageDialog(this,"✅ Registro salvo!");
                limparCampos();
                preencherTabela();
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(this,"Erro ao salvar: " + e.getMessage());
        }
    }
    private void onEditar() {

        int linha = table1.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela!");
            return;
        }

        try {
            int idEstoque = Integer.parseInt(table1.getValueAt(linha, 0).toString()); // coluna ID

            Model_Estoque_06 m = new Model_Estoque_06();
            m.setA06_id_estoque(idEstoque);

            Number valor = (Number) spinner1QUANTIDADE.getValue();
            m.setA06_quantidade(valor.intValue());

            m.setA06_data_entrada(pegarData());
            m.setA06_id_produto(pegarIdCombo(comboBox1ID_Produto));
            m.setA06_id_localizacao(pegarIdCombo(comboBox2LOCAL));

            if (controller.atualizarEstoque(m)) {
                JOptionPane.showMessageDialog(this, "✅ Atualizado!");
                limparCampos();
                preencherTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }
    private void onExcluir() {

        int linha = table1.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela!");
            return;
        }

        try {
            int id = Integer.parseInt(table1.getValueAt(linha, 0).toString());

            int resp = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este registro?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (resp == JOptionPane.YES_OPTION) {
                if (controller.excluirEstoque(id)) {
                    JOptionPane.showMessageDialog(this, "✅ Excluído!");
                    limparCampos();
                    preencherTabela();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
    private void onBuscar() {
        try {
            String s = JOptionPane.showInputDialog(this, "ID do estoque:");

            // usuário cancelou ou deixou vazio -> nada a fazer
            if (s == null) return;
            s = s.trim();
            if (s.isEmpty()) return;

            int id = Integer.parseInt(s);

            Model_Estoque_06 m = controller.buscarEstoque(id);

            if (m == null) {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
                return;
            }

            // limpa e mostra só o registro encontrado
            DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
            modelo.setRowCount(0);
            inserirLinhaTabela(m);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido (somente números).");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }

    // ===========================================================
    // TABELA
    // ===========================================================
    private void preencherTabela() {

        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0);

        try {
            List<Model_Estoque_06> lista = controller.listarEstoques();

            for (Model_Estoque_06 m : lista) {
                inserirLinhaTabela(m);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tabela: " + e.getMessage()
            );
        }
    }

    private void inserirLinhaTabela(Model_Estoque_06 m){

        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();

        String data = "";
        if (m.getA06_data_entrada() != null) {
            data = sdfTela.format(m.getA06_data_entrada());
        }

        modelo.addRow(new Object[]{
                m.getA06_id_estoque(),
                m.getA06_quantidade(),
                data,
                buscarNomeProduto(m.getA06_id_produto()),
                buscarNomeLocal(m.getA06_id_localizacao())
        });
    }

    private void carregarLinhaSelecionada() {

        int linha = table1.getSelectedRow();
        if (linha == -1) return;

        spinner1QUANTIDADE.setValue(
                Integer.parseInt(table1.getValueAt(linha, 1).toString())
        );

        textField2DATA_ENTRADA.setText(
                table1.getValueAt(linha, 2).toString()
        );

        String prod = table1.getValueAt(linha, 3).toString();
        String local = table1.getValueAt(linha, 4).toString();

        selecionarCombo(comboBox1ID_Produto, Integer.parseInt(prod.split(" - ")[0]));
        selecionarCombo(comboBox2LOCAL, Integer.parseInt(local.split(" - ")[0]));
    }
    // ===========================================================
    // BUSCAS AUXILIARES
    // ===========================================================
    private String buscarNomeProduto(int id) {

        String sql = "SELECT A02_NOME_PRODUTO FROM PRODUTO_02 WHERE A02_ID_PRODUTO = ?";

        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return id + " - " + rs.getString("A02_NOME_PRODUTO");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(id);
    }

    private String buscarNomeLocal(int id) {

        String sql = "SELECT A07_IDENTIFICACAO FROM LOCALIZACAO_07 WHERE A07_ID_LOCALIZACAO = ?";

        try (Connection c = DAO_Conexao.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return id + " - " + rs.getString("A07_IDENTIFICACAO");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(id);
    }

    // ===========================================================
    // LIMPEZA
    // ===========================================================
    private void limparCampos() {

        textField2DATA_ENTRADA.setText("");
        spinner1QUANTIDADE.setValue(0);

        if (comboBox1ID_Produto.getItemCount() > 0)
            comboBox1ID_Produto.setSelectedIndex(0);

        if (comboBox2LOCAL.getItemCount() > 0)
            comboBox2LOCAL.setSelectedIndex(0);

        table1.clearSelection();
    }
}
