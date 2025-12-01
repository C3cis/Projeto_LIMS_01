package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_Controller_LIMS.Controller_Fornecedor_04;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Fornecedor_04;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Produtos extends JDialog {

    private JPanel contentPane;
    private JButton sairButton, salvarButton, editarButton, excluirButton, buscarButton;

    private JTextField textField2nomeProduto;
    private JTextField textField3ValorProd;

    private JFormattedTextField textField1DataCadastro;
    private JFormattedTextField textField6DataChegada;


    private JComboBox<String> comboBox1Fornecedor;
    private JComboBox<String> comboBox2Status;
    private JComboBox<String> comboBox3NomeProjeto;

    private JEditorPane editorPane1Descricao;
    private JTable table1;
    private DefaultTableModel tableModel;

    private Controller_Produto_02 controller;
    private Controller_Projeto_01 controllerProjeto;
    private Controller_Fornecedor_04 controllerFornecedor;

    private int produtoSelecionado = -1;

    // ---------- Construtor (ajuste dos controllers) ----------
    public Produtos() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Produtos");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // controllers: manter o padrão que você já usava
        controller = new Controller_Produto_02(DAO_Conexao.connect());
        controllerFornecedor = new Controller_Fornecedor_04();
        controllerProjeto = new Controller_Projeto_01();

        configurarTabela();
        aplicarMascaraDatas();
        carregarCombos();
        carregarTabela();

        salvarButton.addActionListener(e -> salvarProduto());
        editarButton.addActionListener(e -> editarProduto());
        excluirButton.addActionListener(e -> excluirProduto());
        buscarButton.addActionListener(e -> carregarTabela());
        sairButton.addActionListener(e -> dispose());

        // carregamento ao selecionar linha (mais robusto que mouseClicked)
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    carregarCamposDaLinha();
                }
            }
        });
    }

    // ------------------------------------------------------------
// MÁSCARA DE DATA: dd/MM/yyyy (mantive seu comportamento)
// ------------------------------------------------------------
    private void aplicarMascaraDatas() {
        try {
            MaskFormatter mf1 = new MaskFormatter("##/##/####");
            mf1.setPlaceholderCharacter('_');
            mf1.install(textField1DataCadastro);
            textField1DataCadastro.setFocusLostBehavior(JFormattedTextField.COMMIT);

            MaskFormatter mf2 = new MaskFormatter("##/##/####");
            mf2.setPlaceholderCharacter('_');
            mf2.install(textField6DataChegada);
            textField6DataChegada.setFocusLostBehavior(JFormattedTextField.COMMIT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------
// CONFIGURA TABELA (remove descrição, exibe Projeto/Fornecedor como "ID - Nome")
// ------------------------------------------------------------
    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new String[]{
                        "ID", "Nome", "Tipo",
                        "Data Cadastro", "Data Chegada",
                        "Valor", "Projeto", "Fornecedor"
                }, 0
        );
        table1.setModel(tableModel);
    }

    // ------------------------------------------------------------
// CARREGA COMBOS (usa os métodos que você já tinha)
// ------------------------------------------------------------
    private void carregarCombos() {

        comboBox2Status.removeAllItems();
        comboBox2Status.addItem("CONSUMO");
        comboBox2Status.addItem("PATRIMONIO");
        comboBox2Status.addItem("INSUMO");

        comboBox1Fornecedor.removeAllItems();
        List<Model_Fornecedor_04> listaF = controllerFornecedor.listarFornecedores();
        if (listaF != null) {
            for (Model_Fornecedor_04 f : listaF) {
                comboBox1Fornecedor.addItem(f.getA04_id_fornecedor() + " - " + f.getA04_nome());
            }
        }

        comboBox3NomeProjeto.removeAllItems();
        List<Model_Projeto_01> listaP = controllerProjeto.listar_projeto(); // conforme seu código original
        if (listaP != null) {
            for (Model_Projeto_01 p : listaP) {
                comboBox3NomeProjeto.addItem(p.getA01_id_projeto() + " - " + p.getA01_nome_projeto());
            }
        }
    }

    // ------------------------------------------------------------
// CARREGA TABELA (formata datas e valor; monta "ID - Nome" procurando no cache local)
// ------------------------------------------------------------
    private void carregarTabela() {

        tableModel.setRowCount(0);

        // pegar listas de projetos/fornecedores para montar nome sem chamar buscar por id
        List<Model_Projeto_01> projetos = controllerProjeto.listar_projeto();
        List<Model_Fornecedor_04> fornecedores = controllerFornecedor.listarFornecedores();

        for (Model_Produto_02 p : controller.listarProdutos()) {

            String projetoFormatado = formatarIdNomeProjeto(projetos, p.getA02_id_projeto());
            String fornecedorFormatado = formatarIdNomeFornecedor(fornecedores, p.getA02_id_fornecedor());

            tableModel.addRow(new Object[]{
                    p.getA02_id_produto(),
                    p.getA02_nome_produto(),
                    p.getA02_tipo(),
                    safeDataParaTela(p.getA02_data_cadastro()),
                    safeDataParaTela(p.getA02_data_chegada()),
                    formatarValorBR(p.getA02_valor_unitario()),
                    projetoFormatado,
                    fornecedorFormatado
            });
        }
    }

    private String formatarIdNomeProjeto(List<Model_Projeto_01> projetos, int idProjeto) {
        if (projetos != null) {
            for (Model_Projeto_01 pr : projetos) {
                if (pr.getA01_id_projeto() == idProjeto) {
                    return pr.getA01_id_projeto() + " - " + pr.getA01_nome_projeto();
                }
            }
        }
        return idProjeto + " - ?";
    }

    private String formatarIdNomeFornecedor(List<Model_Fornecedor_04> fornecedores, int idFornecedor) {
        if (fornecedores != null) {
            for (Model_Fornecedor_04 f : fornecedores) {
                if (f.getA04_id_fornecedor() == idFornecedor) {
                    return f.getA04_id_fornecedor() + " - " + f.getA04_nome();
                }
            }
        }
        return idFornecedor + " - ?";
    }

    private String safeDataParaTela(Date d) {
        if (d == null) return "";
        return converterDataParaTela(d);
    }

    // ------------------------------------------------------------
// SALVAR PRODUTO (validações e conversões mais robustas)
// ------------------------------------------------------------
    private void salvarProduto() {
        try {
            String nome = textField2nomeProduto.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o nome do produto.");
                return;
            }

            String descricao = editorPane1Descricao.getText();
            if (comboBox2Status.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione o tipo/status.");
                return;
            }
            String tipo = comboBox2Status.getSelectedItem().toString();

            Date dataCadastro = converterParaDateSQL(textField1DataCadastro.getText());
            if (dataCadastro == null) {
                JOptionPane.showMessageDialog(null, "Data de cadastro inválida.");
                return;
            }
            Date dataChegada = converterParaDateSQL(textField6DataChegada.getText());
            if (dataChegada == null) {
                JOptionPane.showMessageDialog(null, "Data de chegada inválida.");
                return;
            }

            double valor = converterValor(textField3ValorProd.getText());
            if (valor < 0) {
                JOptionPane.showMessageDialog(null, "Valor inválido.");
                return;
            }

            if (comboBox3NomeProjeto.getSelectedItem() == null || comboBox1Fornecedor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Selecione projeto e fornecedor.");
                return;
            }

            int idProjeto = extrairId(comboBox3NomeProjeto.getSelectedItem().toString());
            int idFornecedor = extrairId(comboBox1Fornecedor.getSelectedItem().toString());

            boolean ok = controller.inserirProduto(
                    nome,
                    descricao,
                    tipo,
                    dataCadastro,
                    dataChegada,
                    valor,
                    idProjeto,
                    idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------------
// EDITAR PRODUTO
// ------------------------------------------------------------
    private void editarProduto() {
        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        try {
            String nome = textField2nomeProduto.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o nome do produto.");
                return;
            }

            String descricao = editorPane1Descricao.getText();
            String tipo = comboBox2Status.getSelectedItem().toString();

            Date dataCadastro = converterParaDateSQL(textField1DataCadastro.getText());
            if (dataCadastro == null) {
                JOptionPane.showMessageDialog(null, "Data de cadastro inválida.");
                return;
            }
            Date dataChegada = converterParaDateSQL(textField6DataChegada.getText());
            if (dataChegada == null) {
                JOptionPane.showMessageDialog(null, "Data de chegada inválida.");
                return;
            }

            double valor = converterValor(textField3ValorProd.getText());
            if (valor < 0) {
                JOptionPane.showMessageDialog(null, "Valor inválido.");
                return;
            }

            int idProjeto = extrairId(comboBox3NomeProjeto.getSelectedItem().toString());
            int idFornecedor = extrairId(comboBox1Fornecedor.getSelectedItem().toString());

            boolean ok = controller.atualizarProduto(
                    produtoSelecionado,
                    nome,
                    descricao,
                    tipo,
                    dataCadastro,
                    dataChegada,
                    valor,
                    idProjeto,
                    idFornecedor
            );

            if (ok) {
                JOptionPane.showMessageDialog(null, "Produto atualizado!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar produto.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------------
// EXCLUIR PRODUTO
// ------------------------------------------------------------
    private void excluirProduto() {
        if (produtoSelecionado == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (controller.excluirProduto(produtoSelecionado)) {
            JOptionPane.showMessageDialog(null, "Produto excluído.");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir produto.");
        }
    }

    // ------------------------------------------------------------
// CARREGAR CAMPOS AO CLICAR NA TABELA (ajustado para novo formato)
// ------------------------------------------------------------
    private void carregarCamposDaLinha() {
        int row = table1.getSelectedRow();
        if (row == -1) return;

        produtoSelecionado = (int) tableModel.getValueAt(row, 0);

        textField2nomeProduto.setText(tableModel.getValueAt(row, 1).toString());
        comboBox2Status.setSelectedItem(tableModel.getValueAt(row, 2).toString());

        // datas já estão como String "dd/MM/yyyy" na tabela
        textField1DataCadastro.setText(tableModel.getValueAt(row, 3).toString());
        textField6DataChegada.setText(tableModel.getValueAt(row, 4).toString());

        // valor: "R$ 1.234,56" -> remover prefixo e pontos de milhar
        String valorStr = tableModel.getValueAt(row, 5).toString();
        valorStr = valorStr.replace("R$", "").trim();
        textField3ValorProd.setText(valorStr);

        selecionarItemCombo(comboBox3NomeProjeto, tableModel.getValueAt(row, 6).toString());
        selecionarItemCombo(comboBox1Fornecedor, tableModel.getValueAt(row, 7).toString());
    }

    // ------------------------------------------------------------
// UTILITÁRIOS
// ------------------------------------------------------------
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

    private int extrairId(String item) {
        try {
            return Integer.parseInt(item.split(" - ")[0].trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private double converterValor(String texto) {
        if (texto == null) return -1;
        String t = texto.replace("R$", "").replace(" ", "").trim();
        // aceita R$1.234,56 ou 1234.56 ou 1,234.56 etc.
        // remover pontos de milhar e transformar vírgula em ponto
        t = t.replace(".", "").replace(",", ".");
        try {
            return Double.parseDouble(t);
        } catch (Exception e) {
            return -1;
        }
    }

    private String formatarValorBR(double valor) {
        // Locale moderno (não depreciado)
        Locale localeBR = Locale.of("pt", "BR");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(localeBR);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return "R$ " + df.format(valor);
    }
    private String converterDataParaTela(Date dataSQL) {
        if (dataSQL == null) return "";
        return new SimpleDateFormat("dd/MM/yyyy").format(dataSQL);
    }

    private void selecionarItemCombo(JComboBox<String> combo, String valor) {
        if (valor == null) return;
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equals(valor)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}