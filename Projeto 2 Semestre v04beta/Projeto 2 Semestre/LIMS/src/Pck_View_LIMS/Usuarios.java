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

        jTableUser.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        {1, "Ana", "ana@exemplo.com", "Analista", "senha1", "Ativo"},
                        {2, "Bruno", "bruno@exemplo.com", "Desenvolvedor", "senha2", "Ativo"},
                        {3, "Carlos", "carlos@exemplo.com", "Gerente", "senha3", "Inativo"},
                        {4, "Daniela", "daniela@exemplo.com", "Analista", "senha4", "Ativo"},
                        {5, "Eduardo", "eduardo@exemplo.com", "Desenvolvedor", "senha5", "Ativo"},
                        {6, "Fernanda", "fernanda@exemplo.com", "Gerente", "senha6", "Inativo"},
                        {7, "Gustavo", "gustavo@exemplo.com", "Analista", "senha7", "Ativo"},
                        {8, "Helena", "helena@exemplo.com", "Desenvolvedor", "senha8", "Ativo"},
                        {9, "Igor", "igor@exemplo.com", "Gerente", "senha9", "Inativo"},
                        {10, "Juliana", "juliana@exemplo.com", "Analista", "senha10", "Ativo"},
                        {11, "Kleber", "kleber@exemplo.com", "Desenvolvedor", "senha11", "Ativo"},
                        {12, "Larissa", "larissa@exemplo.com", "Gerente", "senha12", "Inativo"},
                        {13, "Marcos", "marcos@exemplo.com", "Analista", "senha13", "Ativo"},
                        {14, "Natalia", "natalia@exemplo.com", "Desenvolvedor", "senha14", "Ativo"},
                        {15, "Otávio", "otavio@exemplo.com", "Gerente", "senha15", "Inativo"},
                        {16, "Patrícia", "patricia@exemplo.com", "Analista", "senha16", "Ativo"},
                        {17, "Quintino", "quintino@exemplo.com", "Desenvolvedor", "senha17", "Ativo"},
                        {18, "Renata", "renata@exemplo.com", "Gerente", "senha18", "Inativo"},
                        {19, "Samuel", "samuel@exemplo.com", "Analista", "senha19", "Ativo"},
                        {20, "Tatiane", "tatiane@exemplo.com", "Desenvolvedor", "senha20", "Ativo"},
                        {21, "Ubirajara", "ubirajara@exemplo.com", "Gerente", "senha21", "Inativo"},
                        {22, "Valéria", "valeria@exemplo.com", "Analista", "senha22", "Ativo"},
                        {23, "Wagner", "wagner@exemplo.com", "Desenvolvedor", "senha23", "Ativo"},
                        {24, "Xuxa", "xuxa@exemplo.com", "Gerente", "senha24", "Inativo"},
                        {25, "Yasmin", "yasmin@exemplo.com", "Analista", "senha25", "Ativo"},
                        {26, "Zeca", "zeca@exemplo.com", "Desenvolvedor", "senha26", "Ativo"},
                        {1, "Ana", "ana@exemplo.com", "Analista", "senha1", "Ativo"},
                        {2, "Bruno", "bruno@exemplo.com", "Desenvolvedor", "senha2", "Ativo"},
                        {3, "Carlos", "carlos@exemplo.com", "Gerente", "senha3", "Inativo"},
                        {4, "Daniela", "daniela@exemplo.com", "Analista", "senha4", "Ativo"},
                        {5, "Eduardo", "eduardo@exemplo.com", "Desenvolvedor", "senha5", "Ativo"},
                        {6, "Fernanda", "fernanda@exemplo.com", "Gerente", "senha6", "Inativo"},
                        {7, "Gustavo", "gustavo@exemplo.com", "Analista", "senha7", "Ativo"},
                        {8, "Helena", "helena@exemplo.com", "Desenvolvedor", "senha8", "Ativo"},
                        {9, "Igor", "igor@exemplo.com", "Gerente", "senha9", "Inativo"},
                        {10, "Juliana", "juliana@exemplo.com", "Analista", "senha10", "Ativo"},
                        {11, "Kleber", "kleber@exemplo.com", "Desenvolvedor", "senha11", "Ativo"},
                        {12, "Larissa", "larissa@exemplo.com", "Gerente", "senha12", "Inativo"},
                        {13, "Marcos", "marcos@exemplo.com", "Analista", "senha13", "Ativo"},
                        {14, "Natalia", "natalia@exemplo.com", "Desenvolvedor", "senha14", "Ativo"},
                        {15, "Otávio", "otavio@exemplo.com", "Gerente", "senha15", "Inativo"},
                        {16, "Patrícia", "patricia@exemplo.com", "Analista", "senha16", "Ativo"},
                        {17, "Quintino", "quintino@exemplo.com", "Desenvolvedor", "senha17", "Ativo"},
                        {18, "Renata", "renata@exemplo.com", "Gerente", "senha18", "Inativo"},
                        {19, "Samuel", "samuel@exemplo.com", "Analista", "senha19", "Ativo"},
                        {20, "Tatiane", "tatiane@exemplo.com", "Desenvolvedor", "senha20", "Ativo"},
                        {21, "Ubirajara", "ubirajara@exemplo.com", "Gerente", "senha21", "Inativo"},
                        {22, "Valéria", "valeria@exemplo.com", "Analista", "senha22", "Ativo"},
                        {23, "Wagner", "wagner@exemplo.com", "Desenvolvedor", "senha23", "Ativo"},
                        {24, "Xuxa", "xuxa@exemplo.com", "Gerente", "senha24", "Inativo"},
                        {25, "Yasmin", "yasmin@exemplo.com", "Analista", "senha25", "Ativo"},
                        {26, "Zeca", "zeca@exemplo.com", "Desenvolvedor", "senha26", "Ativo"}
                },
                new String[] {"ID", "Nome", "Email", "Cargo", "Senha", "Status"}
        ));

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

    private void onOK() {
        // add your code here

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
