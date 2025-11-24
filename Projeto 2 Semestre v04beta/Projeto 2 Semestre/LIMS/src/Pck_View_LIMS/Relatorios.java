package Pck_View_LIMS;

import Pck_Controller_LIMS.Controller_Relatorio_08;
import Pck_Model_LIMS.Model_Relatorio_08;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class Relatorios extends JDialog {

    private JPanel contentPane;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton listarButton;
    private JButton sairButton;

    private JTable table1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JEditorPane editorPane1;
    private JEditorPane editorPane2;
    private JTextField textField1;

    private JTextField textFieldID;
    private JTextField textFieldTitulo;
    private JTextField textFieldDataGeracao;

    private JTextArea textAreaConteudo;
    private JComboBox comboBoxUsuario;

    private Controller_Relatorio_08 controller = new Controller_Relatorio_08();

    public Relatorios() {

        setContentPane(contentPane);
        setModal(true);

        salvarButton.addActionListener(e -> inserir());
        editarButton.addActionListener(e -> atualizar());
        excluirButton.addActionListener(e -> excluir());
        buscarButton.addActionListener(e -> buscar());
        listarButton.addActionListener(e -> listar());
        sairButton.addActionListener(e -> dispose());
    }

    private void inserir() {
        try {
            Model_Relatorio_08 m = new Model_Relatorio_08();
            m.setA08_titulo(textFieldTitulo.getText());
            m.setA08_data_geracao(textFieldDataGeracao.getText());
            m.setA08_conteudo(textAreaConteudo.getText());
            m.setA08_id_usuario(Integer.parseInt(comboBoxUsuario.getSelectedItem().toString()));

            if (controller.inserir(m)) {
                JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
                listar();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void atualizar() {
        try {
            Model_Relatorio_08 m = new Model_Relatorio_08();
            m.setA08_id_relatorio(Integer.parseInt(textFieldID.getText()));
            m.setA08_titulo(textFieldTitulo.getText());
            m.setA08_data_geracao(textFieldDataGeracao.getText());
            m.setA08_conteudo(textAreaConteudo.getText());
            m.setA08_id_usuario(Integer.parseInt(comboBoxUsuario.getSelectedItem().toString()));

            if (controller.atualizar(m)) {
                JOptionPane.showMessageDialog(null, "Atualizado!");
                listar();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void excluir() {
        try {
            int id = Integer.parseInt(textFieldID.getText());
            if (controller.excluir(id)) {
                JOptionPane.showMessageDialog(null, "Excluído!");
                listar();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(textFieldID.getText());
            Model_Relatorio_08 m = controller.buscar(id);

            if (m != null) {
                textFieldTitulo.setText(m.getA08_titulo());
                textFieldDataGeracao.setText(m.getA08_data_geracao());
                textAreaConteudo.setText(m.getA08_conteudo());
                comboBoxUsuario.setSelectedItem(String.valueOf(m.getA08_id_usuario()));
            } else {
                JOptionPane.showMessageDialog(null, "Não encontrado.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void listar() {
        try {
            List<Model_Relatorio_08> lista = controller.listar();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Título");
            model.addColumn("Data Geração");
            model.addColumn("Conteúdo");
            model.addColumn("Usuário");

            for (Model_Relatorio_08 m : lista) {
                model.addRow(new Object[]{
                        m.getA08_id_relatorio(),
                        m.getA08_titulo(),
                        m.getA08_data_geracao(),
                        m.getA08_conteudo(),
                        m.getA08_id_usuario()
                });
            }

            table1.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}