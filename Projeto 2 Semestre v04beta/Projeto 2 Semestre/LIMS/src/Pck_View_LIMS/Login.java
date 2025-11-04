package Pck_View_LIMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JDialog {
    private JPanel contentPane;
    private JTextField caTextField;
    private JButton buttomEnter;
    private JButton exitButton;
    private JPasswordField passwordField;

    public Login() {
        setContentPane(contentPane);
        setModal(true);

        buttomEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEntrar();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSignIn() {
        // add your code here
        Cadastro cadastro = new Cadastro();
        cadastro.setUndecorated(true);
        cadastro.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
        cadastro.setLocationRelativeTo(null); // Centraliza na tela
        cadastro.setVisible(true);
    }

    public void onEntrar(){
        if (caTextField.getText().equals("0000") && passwordField.getText().equals("1234")) {
            Principal principal = new Principal();
            principal.setUndecorated(true);
            principal.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ocupa toda a tela
            principal.setLocationRelativeTo(null); // Centraliza na tela
            principal.setVisible(true);
            caTextField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro: Usuario não encontrado.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
