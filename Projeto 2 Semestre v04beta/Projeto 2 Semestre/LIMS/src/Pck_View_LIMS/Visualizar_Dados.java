package Pck_View_LIMS;

import Pck_Model_LIMS.Model_Visualizar_Dados;
import Pck_Persistencia_LIMS.Persistencia_Visualizar_Dados;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Visualizar_Dados extends JDialog {

    private JPanel contentPane;
    private JTextField textFieldBuscar;
    private JButton btnBuscar;
    private JButton btnDetalhes;
    private JButton btnSair;
    private JTable table1;
    private DefaultTableModel tableModel;

    private final Persistencia_Visualizar_Dados persistencia = new Persistencia_Visualizar_Dados();
    private List<Model_Visualizar_Dados> lista = new ArrayList<>();

    public Visualizar_Dados() {
        super((Frame) null, "Visualizar Dados", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        configurarTabela();
        configurarEventos();
        carregarTabela();

        setSize(1080, 500);
        setLocationRelativeTo(null);
    }

    private void initUI() {

        contentPane = new JPanel(new BorderLayout(8, 8)); // diminui espaçamento geral
        contentPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setContentPane(contentPane);

        // ====== PAINEL DO TOPO (TÍTULO + BUSCA) ======
        JPanel painelTopo = new JPanel(new BorderLayout());

        // ---------- TÍTULO ----------
        JLabel titulo = new JLabel("Visualizar Dados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); // era 26
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0)); // menos espaço
        painelTopo.add(titulo, BorderLayout.NORTH);

        // ---------- BUSCA + BOTÕES ----------
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4)); // mais compacto

        JLabel lblBuscar = new JLabel("Pesquisar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        textFieldBuscar = new JTextField(20); // era 25
        btnBuscar = new JButton("Buscar");
        btnDetalhes = new JButton("Detalhes");
        btnSair = new JButton("Sair");

        painelBusca.add(lblBuscar);
        painelBusca.add(textFieldBuscar);
        painelBusca.add(btnBuscar);
        painelBusca.add(btnDetalhes);
        painelBusca.add(btnSair);

        painelTopo.add(painelBusca, BorderLayout.SOUTH);

        contentPane.add(painelTopo, BorderLayout.NORTH);

        // ================= TABELA =================
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Produto", "Tipo", "Data Chegada", "Fornecedor", "Projeto"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1 = new JTable(tableModel);
        table1.setRowHeight(28);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table1);

        // 🔥 NÚMERO CHAVE: ISSO ABAIXA A TABELA E EQUILIBRA A TELA
        scrollPane.setPreferredSize(new Dimension(820, 420)); // estava baixo demais e fazia subir tudo

        scrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Tabela de Dados"),
                        BorderFactory.createEmptyBorder(4, 4, 4, 4)
                )
        );

        contentPane.add(scrollPane, BorderLayout.CENTER);
    }


    private void configurarTabela() {

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setRowHeight(28);
        table1.setFillsViewportHeight(true);

        TableColumnModel cm = table1.getColumnModel();

        cm.getColumn(0).setPreferredWidth(50);   // ID
        cm.getColumn(1).setPreferredWidth(200);  // Produto
        cm.getColumn(2).setPreferredWidth(140);  // Tipo
        cm.getColumn(3).setPreferredWidth(110);  // Data
        cm.getColumn(4).setPreferredWidth(220);  // Fornecedor
        cm.getColumn(5).setPreferredWidth(70);   // Projeto

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        cm.getColumn(0).setCellRenderer(center);
        cm.getColumn(5).setCellRenderer(center);
    }

    private void configurarEventos() {

        btnBuscar.addActionListener(e -> buscar());
        textFieldBuscar.addActionListener(e -> buscar());

        btnSair.addActionListener(e -> dispose());

        btnDetalhes.addActionListener(e -> abrirDetalhes());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    abrirDetalhes();
                }
            }
        });
    }

    private void carregarTabela() {

        tableModel.setRowCount(0);

        try {
            lista = persistencia.listarTudo();
            if (lista == null) lista = new ArrayList<>();

        } catch (Exception ex) {
            lista = new ArrayList<>();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados:\n" + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Model_Visualizar_Dados m : lista) {
            tableModel.addRow(new Object[]{
                    m.getIdProduto(),
                    m.getNomeProduto(),
                    m.getTipoProduto(),
                    m.getDataChegada(),
                    m.getNomeFornecedor(),
                    m.getIdProjeto()
            });
        }
    }

    private void buscar() {

        String filtro = textFieldBuscar.getText();

        if (filtro == null || filtro.isBlank()) {
            carregarTabela();
            return;
        }

        filtro = filtro.trim().toLowerCase();
        tableModel.setRowCount(0);

        for (Model_Visualizar_Dados m : lista) {

            String produto = m.getNomeProduto() != null ? m.getNomeProduto().toLowerCase() : "";
            String fornecedor = m.getNomeFornecedor() != null ? m.getNomeFornecedor().toLowerCase() : "";
            String idTxt = String.valueOf(m.getIdProduto());

            if (produto.contains(filtro) ||
                    fornecedor.contains(filtro) ||
                    idTxt.contains(filtro)) {

                tableModel.addRow(new Object[]{
                        m.getIdProduto(),
                        m.getNomeProduto(),
                        m.getTipoProduto(),
                        m.getDataChegada(),
                        m.getNomeFornecedor(),
                        m.getIdProjeto()
                });
            }
        }
    }

    private void abrirDetalhes() {

        int linhaView = table1.getSelectedRow();

        if (linhaView == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um registro na tabela.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhaModel = table1.convertRowIndexToModel(linhaView);

        if (linhaModel < 0 || linhaModel >= lista.size()) {
            JOptionPane.showMessageDialog(this,
                    "Índice inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model_Visualizar_Dados selecionado = lista.get(linhaModel);

        Frame parentFrame = JOptionPane.getFrameForComponent(this);
        Detalhes_Usuario detalhes = new Detalhes_Usuario(parentFrame, selecionado);
        detalhes.setVisible(true);
    }
}