package Pck_View_LIMS;
import Pck_Controller_LIMS.Controller_Produto_02;
import Pck_Controller_LIMS.Controller_Projeto_01;
import Pck_DAO_LIMS.DAO_Conexao;
import Pck_Model_LIMS.Model_Produto_02;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Visualizar_Dados;
import Pck_Persistencia_LIMS.Persistencia_Produto_02;
import Pck_Persistencia_LIMS.Persistencia_Projeto_01;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Detalhes_Usuario extends JDialog {

    private JPanel contentPane;

    private JTable tableDetalhes;
    private DefaultTableModel tableModel;

    private final Persistencia_Projeto_01 persistenciaProjeto = new Persistencia_Projeto_01();
    private final Persistencia_Produto_02 persistenciaProduto = new Persistencia_Produto_02(DAO_Conexao.connect());

    public Detalhes_Usuario(Frame parent, Model_Visualizar_Dados registro) {
        super(parent, "Detalhes do Registro", true);

        initUI();
        carregarDetalhes(registro);

        setSize(800, 300);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel contentPane = new JPanel(new BorderLayout(8, 8));
        contentPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setContentPane(contentPane);

        // Tabela de detalhes
        tableModel = new DefaultTableModel(
                new Object[]{"ID Projeto", "Nome Projeto", "Descrição Projeto",
                        "ID Produto", "Nome Produto", "Descrição Produto",
                        "Data Chegada", "Fornecedor"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableDetalhes = new JTable(tableModel);
        tableDetalhes.setRowHeight(28);
        tableDetalhes.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tableDetalhes);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnFechar);
        contentPane.add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarDetalhes(Model_Visualizar_Dados registro) {
        try {
            Model_Projeto_01 projeto = persistenciaProjeto.buscar_projeto(registro.getIdProjeto());
            Model_Produto_02 produto = persistenciaProduto.buscarProduto(registro.getIdProduto());

            tableModel.setRowCount(0);

            tableModel.addRow(new Object[]{
                    projeto.getA01_id_projeto(), projeto.getA01_nome_projeto(), projeto.getA01_descricao(),
                    produto.getA02_id_produto(), produto.getA02_nome_produto(), produto.getA02_descricao(),
                    registro.getDataChegada(),
                    registro.getNomeFornecedor()
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar detalhes:\n" + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
