package Pck_View_LIMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Principal extends JDialog {
    private JPanel contentPane;
    private JButton deslogarButton;
    private JButton cadastroButton;
    private JButton produtosButton;
    private JButton fornecedoresButton;
    private JButton projetosButton;
    private JButton pedidosButton;
    private JButton relatoriosButton;
    private JButton vizualizarDadosButton;
    private JButton manutençãoButton;
    private JButton estoqueButton;

    public Principal() {
        setContentPane(contentPane);
        setModal(true);
        int v = 1;

        if (v == 1) {

        } else {
            projetosButton.setVisible(false);
            cadastroButton.setVisible(false);
            produtosButton.setVisible(false);
            fornecedoresButton.setVisible(false);
            produtosButton.setVisible(false);
            pedidosButton.setVisible(false);
            relatoriosButton.setVisible(false);
        }


        deslogarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cadastro cadastro = new Cadastro();
                cadastro.setUndecorated(true);
                cadastro.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                cadastro.setLocationRelativeTo(null); // Centraliza na tela
                cadastro.setVisible(true);
            }
        });

        produtosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Produtos funcionarios = new Produtos();
                funcionarios.setUndecorated(true);
                funcionarios.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                funcionarios.setLocationRelativeTo(null); // Centraliza na tela
                funcionarios.setVisible(true);
            }
        });

        fornecedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fornecedores fornecedores = new Fornecedores();
                fornecedores.setUndecorated(true);
                fornecedores.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                fornecedores.setLocationRelativeTo(null); // Centraliza na tela
                fornecedores.setVisible(true);
            }
        });

        projetosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Projetos projetos = new Projetos();
                projetos.setUndecorated(true);
                projetos.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                projetos.setLocationRelativeTo(null); // Centraliza na tela
                projetos.setVisible(true);
            }
        });

        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pedidos pedidos = new Pedidos();
                pedidos.setUndecorated(true);
                pedidos.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                pedidos.setLocationRelativeTo(null); // Centraliza na tela
                pedidos.setVisible(true);
            }
        });

        relatoriosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Relatorios relatorios = new Relatorios();
                relatorios.setUndecorated(true);
                relatorios.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                relatorios.setLocationRelativeTo(null); // Centraliza na tela
                relatorios.setVisible(true);
            }
        });

        vizualizarDadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizarDados visualizarDados = new VisualizarDados();
                visualizarDados.setUndecorated(true);
                visualizarDados.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
                visualizarDados.setLocationRelativeTo(null); // Centraliza na tela
                visualizarDados.setVisible(true);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cancelar();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void cancelar() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setUndecorated(true);
        principal.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
        principal.setLocationRelativeTo(null); // Centraliza na tela
        principal.setVisible(true);
    }

    public static void configurarTela(JFrame frame) {
        // Remove as bordas da janela
        frame.setUndecorated(true);

        // Obtém o tamanho total da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Define o tamanho do frame para ocupar toda a tela
        frame.setSize(screenSize);

        // Centraliza a janela na tela (embora em tela cheia isso não faça diferença visual)
        frame.setLocationRelativeTo(null);

        // Torna a janela visível
        frame.setVisible(true);
    }
}
