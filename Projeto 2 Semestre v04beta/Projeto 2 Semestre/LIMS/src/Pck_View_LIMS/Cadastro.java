package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Model_LIMS.Model_Usuario_11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cadastro extends JDialog {

    private JPanel contentPane;
    private JButton btnSalvar;
    private JButton btnVoltar;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> comboCargo;
    private JButton btnTabela;

    public Cadastro() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Usuário");

        comboCargo.addItem("Padrão");
        comboCargo.addItem("Administrador");

        btnSalvar.addActionListener(e -> salvarUsuario());
        btnVoltar.addActionListener(e -> dispose());
        btnTabela.addActionListener(e -> abrirTabela());

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void salvarUsuario() {

        if (txtNome.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtSenha.getPassword().length == 0) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Model_Usuario_11 u = new Model_Usuario_11();
        u.setA11_nome(txtNome.getText().trim());
        u.setA11_email(txtEmail.getText().trim());
        u.setA11_senha(new String(txtSenha.getPassword()));
        u.setA11_cargo(comboCargo.getSelectedItem().toString());
        u.setA11_status_usuario("ATIVO");

        Controller_Usuario_11 controller = new Controller_Usuario_11();

        if (controller.inserir(u)) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        comboCargo.setSelectedIndex(0);
    }

    private void abrirTabela() {
        Usuarios tabela = new Usuarios();
        telaCheia(tabela);
    }

    private void telaCheia(JDialog tela) {
        tela.setUndecorated(true);
        tela.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}
