package Pck_View_LIMS;

import javax.swing.*;
import java.awt.event.*;

public class Usuarios extends JDialog {
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonBack;
    private JTable jTableUser;
    private JButton inserirButton;
    private JButton editarButton;
    private JButton buscarButton;
    private JScrollPane jScrollPane;
    private JButton excluirButton;

    public Usuarios() {
        setTitle("Tabela Usuário");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);

        setContentPane(contentPane);

        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void onOK() {
        // add your code here

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
