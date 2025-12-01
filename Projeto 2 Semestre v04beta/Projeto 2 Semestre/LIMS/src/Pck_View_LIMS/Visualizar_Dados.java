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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Visualizar_Dados extends JDialog {

    private JPanel contentPane;
    private JTextField textFieldBuscar;
    private JButton btnBuscar, btnDetalhes, btnSair;
    private JTable table1;
    private DefaultTableModel tableModel;

    private final Persistencia_Visualizar_Dados persistencia = new Persistencia_Visualizar_Dados();
    private List<Model_Visualizar_Dados> lista = new ArrayList<>();

    private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

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
        contentPane = new JPanel(new BorderLayout(8, 8));
        contentPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setContentPane(contentPane);

        JPanel painelTopo = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Visualizar Dados", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        painelTopo.add(titulo, BorderLayout.NORTH);

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        painelBusca.add(new JLabel("Pesquisar:"));
        textFieldBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnDetalhes = new JButton("Detalhes");
        btnSair = new JButton("Sair");
        painelBusca.add(textFieldBuscar);
        painelBusca.add(btnBuscar);
        painelBusca.add(btnDetalhes);
        painelBusca.add(btnSair);

        painelTopo.add(painelBusca, BorderLayout.SOUTH);
        contentPane.add(painelTopo, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Produto", "Tipo", "Data Chegada", "Fornecedor", "Projeto"}, 0
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
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Tabela de Dados"),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        scrollPane.setPreferredSize(new Dimension(1050, 400));
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }


    private void configurarTabela() {
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        TableColumnModel cm = table1.getColumnModel();

        cm.getColumn(0).setPreferredWidth(50);   // ID
        cm.getColumn(1).setPreferredWidth(250);  // Produto
        cm.getColumn(2).setPreferredWidth(180);  // Tipo
        cm.getColumn(3).setPreferredWidth(120);  // Data Chegada
        cm.getColumn(4).setPreferredWidth(300);  // Fornecedor
        cm.getColumn(5).setPreferredWidth(100);  // Projeto

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        cm.getColumn(0).setCellRenderer(center); // ID
        cm.getColumn(3).setCellRenderer(center); // Data
        cm.getColumn(5).setCellRenderer(center); // Projeto
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
                    formatarData(m.getDataChegada()),
                    m.getNomeFornecedor(),
                    m.getIdProjeto()
            });
        }
    }

    private String formatarData(java.util.Date data) {
        if (data == null) return "";
        try {
            return formatoData.format(data);
        } catch (Exception e) {
            return data.toString();
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
            if ((m.getNomeProduto() != null && m.getNomeProduto().toLowerCase().contains(filtro)) ||
                    (m.getNomeFornecedor() != null && m.getNomeFornecedor().toLowerCase().contains(filtro)) ||
                    String.valueOf(m.getIdProduto()).contains(filtro)) {

                tableModel.addRow(new Object[]{
                        m.getIdProduto(),
                        m.getNomeProduto(),
                        m.getTipoProduto(),
                        formatarData(m.getDataChegada()),
                        m.getNomeFornecedor(),
                        m.getIdProjeto()
                });
            }
        }
    }

    private void abrirDetalhes() {
        int linhaView = table1.getSelectedRow();
        if (linhaView == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhaModel = table1.convertRowIndexToModel(linhaView);
        if (linhaModel < 0 || linhaModel >= lista.size()) {
            JOptionPane.showMessageDialog(this, "Índice inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model_Visualizar_Dados selecionado = lista.get(linhaModel);
        Frame parentFrame = JOptionPane.getFrameForComponent(this);
        Detalhes_Usuario detalhes = new Detalhes_Usuario(parentFrame, selecionado);
        detalhes.setVisible(true);
    }
}
