package Pck_View_LIMS;

import javax.swing.*;
import java.awt.event.*;

public class Relatorios extends JDialog {
    private JPanel contentPane;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JEditorPane editorPane1;
    private JEditorPane editorPane2;
    private JTextArea textArea1;

    public Relatorios() {
        setContentPane(contentPane);
        setModal(true);

        table1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        // Linha 1 (Status Semanal)
                        {
                                "P001",                           // ID Projeto
                                "U101",                           // ID Usuario
                                "PROD-2025-05",                   // ID Produto
                                "Status Semanal - Feature X (12/10)", // Nome do Relatorio
                                "As tarefas de backend foram concluídas (95%). Falta apenas a integração final com a API de terceiros. Atraso de 2 dias devido a um bug crítico no módulo de cache, que já foi corrigido. Próximo passo: Revisão de código e testes de stress." // Conteudo
                        },

                        // Linha 2 (Relatório de Falha)
                        {
                                "P003",
                                "U245",
                                "PROD-2026-12",
                                "Relatório de Falha - Servidor de Produção #3",
                                "Identificada falha de hardware (disco rígido com 80% de degradação) no Servidor Prod-03. O RAID compensou a falha, mas o disco precisa ser substituído imediatamente para evitar interrupção de serviço. Chamado aberto com a equipe de manutenção. Ação urgente necessária."
                        },

                        // Linha 3 (Feedback do Cliente)
                        {
                                "P002",
                                "U310",
                                "PROD-2025-01",
                                "Análise de Feedback - Lançamento Beta v1.0",
                                "O feedback dos clientes Beta aponta alto índice de satisfação com a usabilidade (9/10). O ponto de dor mais citado é a lentidão no carregamento das imagens em dispositivos móveis. Sugestão: Otimizar o tamanho e o formato das imagens na próxima Sprint."
                        }
                },
                new String[] {"ID Projeto", "ID Usuario", "ID Produto", "Nome do Relatorio", "Conteudo"}
        ));
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        sairButton.addActionListener(new ActionListener() {
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
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
