package Pck_View_LIMS;
import Pck_Model_LIMS.Model_Visualizar_Dados;
import Pck_Model_LIMS.Model_Visualizar_Dados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Detalhes_Usuario extends JDialog {

    private JPanel contentPane;
    private JTable tabelaGeral;
    private JEditorPane textDetalhes;
    private JButton bntFecha;
    public Detalhes_Usuario(Frame parent, Model_Visualizar_Dados dados) {
        super(parent, "Detalhes do Produto", true);

        setContentPane(criarLayout());
        preencherCampos(dados);

        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    // -----------------------------
    // CRIA O LAYOUT DA JANELA
    // -----------------------------
    private JPanel criarLayout() {
        contentPane = new JPanel(new BorderLayout());

        textDetalhes = new JEditorPane();
        textDetalhes.setEditable(false);
        textDetalhes.setContentType("text/plain");

        tabelaGeral = new JTable(new DefaultTableModel(
                new Object[]{"Campo", "Valor"},
                0
        ));
        tabelaGeral.setEnabled(false);

        contentPane.add(new JScrollPane(textDetalhes), BorderLayout.NORTH);
        contentPane.add(new JScrollPane(tabelaGeral), BorderLayout.CENTER);

        return contentPane;
    }

    // -----------------------------
    // PREENCHER CAMPOS DA TELA
    // -----------------------------
    private void preencherCampos(Model_Visualizar_Dados m) {

        // Painel superior com texto
        textDetalhes.setText(
                "ID Produto: " + m.getIdProduto() + "\n" +
                        "Nome Produto: " + m.getNomeProduto() + "\n" +
                        "Tipo: " + m.getTipoProduto() + "\n" +
                        "Data Chegada: " + m.getDataChegada() + "\n" +
                        "Fornecedor: " + m.getNomeFornecedor() + "\n" +
                        "ID Projeto: " + m.getIdProjeto()
        );

        // Tabela com todos os campos
        DefaultTableModel model = (DefaultTableModel) tabelaGeral.getModel();
        model.addRow(new Object[]{"ID Produto", m.getIdProduto()});
        model.addRow(new Object[]{"Nome Produto", m.getNomeProduto()});
        model.addRow(new Object[]{"Tipo Produto", m.getTipoProduto()});
        model.addRow(new Object[]{"Data Chegada", m.getDataChegada()});
        model.addRow(new Object[]{"Fornecedor", m.getNomeFornecedor()});
        model.addRow(new Object[]{"ID Projeto", m.getIdProjeto()});
    }
}
