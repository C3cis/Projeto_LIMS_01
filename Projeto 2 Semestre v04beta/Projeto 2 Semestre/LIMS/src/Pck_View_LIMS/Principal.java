package Pck_View_LIMS;

import Pck_Model_LIMS.Model_Usuario_11;

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
    private JButton localizaçãoButton;

    private Model_Usuario_11 usuarioLogado;

    public Principal(Model_Usuario_11 usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setContentPane(contentPane);
        setModal(true);
        setTitle("Menu - LIMS");

        aplicarPermissoes();

        deslogarButton.addActionListener(e -> dispose());
        cadastroButton.addActionListener(e -> abrirTela(new Cadastro()));
        projetosButton.addActionListener(e -> abrirTela(new Projetos()));
        produtosButton.addActionListener(e -> abrirTela(new Produtos()));
        pedidosButton.addActionListener(e -> abrirTela(new Pedidos()));
        relatoriosButton.addActionListener(e -> abrirTela(new Relatorios()));
        fornecedoresButton.addActionListener(e -> abrirTela(new Fornecedores()));
        vizualizarDadosButton.addActionListener(e -> abrirTela(new Visualizar_Dados()));
        manutençãoButton.addActionListener(e -> abrirTela(new Manutencao()));
        estoqueButton.addActionListener(e -> abrirTela(new Estoque()));
        localizaçãoButton.addActionListener(e -> abrirTela(new Localizacao()));
    }
    private void aplicarPermissoes() {
        String cargo = usuarioLogado.getA11_cargo();

        if (!cargo.equalsIgnoreCase("Administrador")) {
            // Ocultar funções de ADM
            cadastroButton.setVisible(false);
            relatoriosButton.setVisible(false);
            fornecedoresButton.setVisible(false);
            projetosButton.setVisible(false);
            manutençãoButton.setVisible(false);
            produtosButton.setVisible(false);
            pedidosButton.setVisible(false);
            estoqueButton.setVisible(false);
            localizaçãoButton.setVisible(false);


        }
    }

    private void abrirTela(JDialog tela) {
        tela.setUndecorated(true);
        tela.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}
