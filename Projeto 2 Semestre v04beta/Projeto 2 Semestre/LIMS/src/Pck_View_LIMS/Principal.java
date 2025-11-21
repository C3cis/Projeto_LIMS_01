package Pck_View_LIMS;

import Pck_Model_LIMS.Model_Usuario_11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Principal extends JDialog {

    private JPanel contentPane;
    private JButton btnDeslogar;
    private JButton btnCadastro;
    private JButton btnProdutos;
    private JButton btnFornecedores;
    private JButton btnProjetos;
    private JButton btnPedidos;
    private JButton btnRelatorios;
    private JButton btnVisualizar;
    private JButton btnManutencao;
    private JButton btnEstoque;

    private Model_Usuario_11 usuarioLogado;

    public Principal(Model_Usuario_11 usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setContentPane(contentPane);
        setModal(true);
        setTitle("Menu - LIMS");

        aplicarPermissoes();

        btnDeslogar.addActionListener(e -> dispose());
        btnCadastro.addActionListener(e -> abrirTela(new Cadastro()));
        btnProjetos.addActionListener(e -> abrirTela(new Projetos()));
        btnProdutos.addActionListener(e -> abrirTela(new Produtos()));
        btnPedidos.addActionListener(e -> abrirTela(new Pedidos()));
        btnRelatorios.addActionListener(e -> abrirTela(new Relatorios()));
        btnFornecedores.addActionListener(e -> abrirTela(new Fornecedores()));
        btnVisualizar.addActionListener(e -> abrirTela(new VisualizarDados()));

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    // =========================================================
    // PERMISSÕES DO USUÁRIO
    // =========================================================
    private void aplicarPermissoes() {
        String cargo = usuarioLogado.getA11_cargo();

        if (!cargo.equalsIgnoreCase("Administrador")) {
            // Ocultar funções de ADM
            btnCadastro.setVisible(false);
            btnRelatorios.setVisible(false);
            btnFornecedores.setVisible(false);
            btnProjetos.setVisible(false);
            btnManutencao.setVisible(false);
        }
    }

    private void abrirTela(JDialog tela) {
        tela.setUndecorated(true);
        tela.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}