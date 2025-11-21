package Pck_View_LIMS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Model_LIMS.Model_Usuario_11;

public class Login extends JDialog {

    private JPanel contentPane;
    private JButton entrarButton;
    private JButton sairButton;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    public Login() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Login - LIMS");

        btnEntrar.addActionListener(e -> realizarLogin());
        btnSair.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    // =====================================================
    // LOGIN REAL
    // =====================================================
    private void realizarLogin() {

        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe E-mail e Senha.");
            return;
        }

        Controller_Usuario_11 controller = new Controller_Usuario_11();
        Model_Usuario_11 usuario = controller.login(email, senha);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            return;
        }

        // Envia o usuário para a tela principal
        Principal principal = new Principal(usuario);
        principal.setUndecorated(true);
        principal.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        principal.setLocationRelativeTo(null);
        principal.setVisible(true);

        dispose();
    }
}
