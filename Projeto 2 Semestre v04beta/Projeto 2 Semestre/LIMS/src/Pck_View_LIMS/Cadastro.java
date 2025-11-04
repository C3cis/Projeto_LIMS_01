package Pck_View_LIMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cadastro extends JDialog {
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonBack;
    private JTextField nomeTextField;
    private JTextField enderecoTextField;
    private JPasswordField passwordField;
    private JComboBox comboBox;
    private JButton buttonTable;

    public Cadastro() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);

        comboBox.addItem("Padrão");
        comboBox.addItem("Administrador");

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });

        buttonTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onTable();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSave() {
        // add your code here

    }

    private void onBack() {
        // add your code here if necessary
        dispose();
    }

    private void onTable() {
        Usuarios tabela = new Usuarios();
        tabela.setUndecorated(true);
        tabela.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
        tabela.setLocationRelativeTo(null); // Centraliza na tela
        tabela.setVisible(true);
    }


}
