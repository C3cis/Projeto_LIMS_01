package Pck_View_LIMS;



import Pck_Model_LIMS.Model_Visualizar_Dados;
import Pck_Persistencia_LIMS.Persistencia_Visualizar_Dados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    // persistência e lista (atributos da classe)
    private final Persistencia_Visualizar_Dados persistencia = new Persistencia_Visualizar_Dados();
    private List<Model_Visualizar_Dados> lista = new ArrayList<>();

    public Visualizar_Dados() {
        super((Frame) null, "Visualizar Dados", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        configurarTabela();
        configurarEventos();

        carregarTabela(); // carrega a lista e popula a JTable

        setSize(920, 520);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        contentPane = new JPanel(new BorderLayout(8, 8));
        setContentPane(contentPane);

        // topo: busca e botões
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        textFieldBuscar = new JTextField(30);
        btnBuscar = new JButton("Buscar");
        btnDetalhes = new JButton("Detalhes");
        btnSair = new JButton("Sair");

        topo.add(new JLabel("Pesquisar:"));
        topo.add(textFieldBuscar);
        topo.add(btnBuscar);
        topo.add(btnDetalhes);
        topo.add(btnSair);

        // tabela
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Produto", "Tipo", "Data Chegada", "Fornecedor", "Projeto"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table1 = new JTable(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(table1);

        contentPane.add(topo, BorderLayout.NORTH);
        contentPane.add(scroll, BorderLayout.CENTER);
    }

    private void configurarTabela() {
        // ajustar larguras opcionais
        if (table1.getColumnModel().getColumnCount() >= 6) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
            table1.getColumnModel().getColumn(1).setPreferredWidth(300);  // Produto
            table1.getColumnModel().getColumn(2).setPreferredWidth(120);  // Tipo
            table1.getColumnModel().getColumn(3).setPreferredWidth(110);  // Data
            table1.getColumnModel().getColumn(4).setPreferredWidth(180);  // Fornecedor
            table1.getColumnModel().getColumn(5).setPreferredWidth(80);   // Projeto
        }
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscar());
        textFieldBuscar.addActionListener(e -> buscar());
        btnDetalhes.addActionListener(e -> abrirDetalhes());
        btnSair.addActionListener(e -> dispose());

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    abrirDetalhes();
                }
            }
        });
    }

    // ---------- carregarTabela: CARREGA lista via DAO e popula a JTable ----------
    private void carregarTabela() {
        tableModel.setRowCount(0);

        try {
            // chama o método correto da persistência
            lista = persistencia.listarTudo();
            if (lista == null) lista = new ArrayList<>();
        } catch (Exception ex) {
            lista = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

    // ---------- buscar: filtra a lista em memória (pode trocar por DAO se preferir) ----------
    private void buscar() {
        String filtro = textFieldBuscar.getText();
        if (filtro == null || filtro.isBlank()) {
            carregarTabela();
            return;
        }

        filtro = filtro.trim().toLowerCase();
        tableModel.setRowCount(0);

        for (Model_Visualizar_Dados m : lista) {
            String nome = m.getNomeProduto() != null ? m.getNomeProduto().toLowerCase() : "";
            String fornecedor = m.getNomeFornecedor() != null ? m.getNomeFornecedor().toLowerCase() : "";
            String idStr = String.valueOf(m.getIdProduto());
            if (nome.contains(filtro) || fornecedor.contains(filtro) || idStr.contains(filtro)) {
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

    // ---------- abrirDetalhes: pega o model da lista correspondente e abre o dialog ----------
    private void abrirDetalhes() {
        int linhaView = table1.getSelectedRow();
        if (linhaView == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhaModel = table1.convertRowIndexToModel(linhaView);

        if (linhaModel < 0 || linhaModel >= lista.size()) {
            JOptionPane.showMessageDialog(this, "Índice inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Model_Visualizar_Dados selecionado = lista.get(linhaModel);

        // obtém o Frame pai real e passa para o dialog (evita problemas de construtor)
        Frame parentFrame = JOptionPane.getFrameForComponent(this);
        Detalhes_Usuario detalhes = new Detalhes_Usuario(parentFrame, selecionado);
        detalhes.setVisible(true);
    }
}