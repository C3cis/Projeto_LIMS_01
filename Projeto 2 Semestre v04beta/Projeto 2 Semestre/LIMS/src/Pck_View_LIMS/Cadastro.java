package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Model_LIMS.Model_Projeto_01;
import Pck_Model_LIMS.Model_Usuario_11;
import Pck_Persistencia_LIMS.Persistencia_Usuario_11;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Cadastro extends JDialog {

    private JPanel contentPane;
    private JComboBox<String> comboBox1Status;
    private JButton buttonBack;
    private JButton salvarButton;
    private JTextField nomeTextField;
    private JComboBox<String> comboBox1Cargo;
    private JTextField textField2Email;
    private JTable jTablMostrarUsu;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JPasswordField passwordFieldSenh;
    private JTextField txtBuscar;

    private JTextField txtCodigo;
    private JTextField txtID;

    public Cadastro() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Usuário");

        inicializarCampos();
        configurarEventos();
        carregarTabela();
    }

    private void inicializarCampos() {
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);

        txtID = new JTextField();
        txtID.setEditable(false);

        comboBox1Status.addItem("ATIVO");
        comboBox1Status.addItem("INATIVO");

        comboBox1Cargo.addItem("Padrão");
        comboBox1Cargo.addItem("Administrador");
    }

    private void configurarEventos() {

        salvarButton.addActionListener(e -> salvarUsuario());
        editarButton.addActionListener(e -> editarUsuario());
        excluirButton.addActionListener(e -> excluirUsuario());
        buttonBack.addActionListener(e -> dispose());
        buscarButton.addActionListener(e -> buscarUsuarios());

        jTablMostrarUsu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarCamposDaTabela();
            }
        });

        // ESC fecha
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void salvarUsuario() {

        if (nomeTextField.getText().trim().isEmpty() ||
                textField2Email.getText().trim().isEmpty() ||
                passwordFieldSenh.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Model_Usuario_11 u = new Model_Usuario_11();
        u.setA11_nome(nomeTextField.getText().trim());
        u.setA11_email(textField2Email.getText().trim());
        u.setA11_senha(new String(passwordFieldSenh.getPassword()));
        u.setA11_cargo(comboBox1Cargo.getSelectedItem().toString());
        u.setA11_status_usuario(comboBox1Status.getSelectedItem().toString());

        Controller_Usuario_11 controller = new Controller_Usuario_11();

        if (controller.inserir(u)) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
        }
    }

    private void editarUsuario() {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!");
            return;
        }

        Model_Usuario_11 u = new Model_Usuario_11();

        u.setA11_id_usuario(Integer.parseInt(txtID.getText()));
        u.setA11_nome(nomeTextField.getText());
        u.setA11_email(textField2Email.getText());
        u.setA11_cargo(comboBox1Cargo.getSelectedItem().toString());
        u.setA11_senha(new String(passwordFieldSenh.getPassword()));
        u.setA11_status_usuario(comboBox1Status.getSelectedItem().toString());

        Persistencia_Usuario_11 dao = new Persistencia_Usuario_11();

        if (dao.atualizar(u)) {
            JOptionPane.showMessageDialog(this, "Usuário atualizado!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        }
    }

    private void excluirUsuario() {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!");
            return;
        }

        int id = Integer.parseInt(txtID.getText());
        Persistencia_Usuario_11 dao = new Persistencia_Usuario_11();

        if (dao.excluir(id)) {
            JOptionPane.showMessageDialog(this, "Usuário removido!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir!");
        }
    }

    private void carregarCamposDaTabela() {
        int linha = jTablMostrarUsu.getSelectedRow();
        if (linha == -1) return;

        txtID.setText(jTablMostrarUsu.getValueAt(linha, 0).toString());
        nomeTextField.setText(jTablMostrarUsu.getValueAt(linha, 1).toString());
        textField2Email.setText(jTablMostrarUsu.getValueAt(linha, 2).toString());
        comboBox1Cargo.setSelectedItem(jTablMostrarUsu.getValueAt(linha, 3).toString());
        passwordFieldSenh.setText(jTablMostrarUsu.getValueAt(linha, 4).toString());
        comboBox1Status.setSelectedItem(jTablMostrarUsu.getValueAt(linha, 5).toString());
        txtCodigo.setText(jTablMostrarUsu.getValueAt(linha, 6).toString());
    }

    private void limparCampos() {
        txtID.setText("");
        nomeTextField.setText("");
        textField2Email.setText("");
        passwordFieldSenh.setText("");
        comboBox1Cargo.setSelectedIndex(0);
        comboBox1Status.setSelectedIndex(0);
        txtCodigo.setText("");
    }

    private void montarTabela() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("Cargo");
        modelo.addColumn("Senha");
        modelo.addColumn("Status");
        modelo.addColumn("Código");
        jTablMostrarUsu.setModel(modelo);
    }

    private void carregarTabela() {
        montarTabela();

        Persistencia_Usuario_11 dao = new Persistencia_Usuario_11();
        ArrayList<Model_Usuario_11> lista = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) jTablMostrarUsu.getModel();

        for (Model_Usuario_11 u : lista) {
            modelo.addRow(new Object[]{
                    u.getA11_id_usuario(),
                    u.getA11_nome(),
                    u.getA11_email(),
                    u.getA11_cargo(),
                    u.getA11_senha(),
                    u.getA11_status_usuario(),
                    u.getA11_codigo_usuario()
            });
        }
    }

    /*private void buscarPorTexto() {
        String termo = txtBuscar.getText().trim().toLowerCase();
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }

        Persistencia_Usuario_11 dao = new Persistencia_Usuario_11();
        ArrayList<Model_Usuario_11> lista = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) jTablMostrarUsu.getModel();
        modelo.setRowCount(0);

        for (Model_Usuario_11 u : lista) {
            String nome = u.getA11_nome() != null ? u.getA11_nome().toLowerCase() : "";
            String email = u.getA11_email() != null ? u.getA11_email().toLowerCase() : "";

            if (nome.contains(termo) || email.contains(termo)) {
                modelo.addRow(new Object[]{
                        u.getA11_id_usuario(),
                        u.getA11_nome(),
                        u.getA11_email(),
                        u.getA11_cargo(),
                        u.getA11_senha(),
                        u.getA11_status_usuario(),
                        u.getA11_codigo_usuario()
                });
            }
        }
    }*/
    private void buscarUsuarios() {
        try {
            String texto = txtBuscar.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um ID, nome ou email para buscar.");
                return;
            }

            Persistencia_Usuario_11 dao = new Persistencia_Usuario_11();
            ArrayList<Model_Usuario_11> lista = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) jTablMostrarUsu.getModel();
            modelo.setRowCount(0); // limpa tabela

            texto = texto.toLowerCase();

            for (Model_Usuario_11 u : lista) {
                if (u.getA11_nome().toLowerCase().contains(texto) ||
                        u.getA11_email().toLowerCase().contains(texto) ||
                        String.valueOf(u.getA11_id_usuario()).contains(texto)) {

                    modelo.addRow(new Object[]{
                            u.getA11_id_usuario(),
                            u.getA11_nome(),
                            u.getA11_email(),
                            u.getA11_cargo(),
                            u.getA11_senha(),
                            u.getA11_status_usuario(),
                            u.getA11_codigo_usuario()
                    });
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }
    private void telaCheia(JDialog tela) {
        tela.setUndecorated(true);
        tela.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}
