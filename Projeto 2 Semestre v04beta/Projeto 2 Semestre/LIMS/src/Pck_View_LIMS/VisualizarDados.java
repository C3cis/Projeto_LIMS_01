package Pck_View_LIMS;

import javax.swing.*;
import java.awt.event.*;

public class VisualizarDados extends JDialog {
    private JPanel contentPane;
    private JButton salvarButton;
    private JButton buscarButton;
    private JTable table1;
    private JButton sairButton;

    public VisualizarDados() {
        setContentPane(contentPane);
        setModal(true);

        table1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        {
                                "Atualização do Sistema Principal",   // Nome do Projeto
                                "Módulo de Autenticação v2.1",        // Nome do Produto
                                "Componente de software essencial para login e segurança. Requer integração imediata.", // Descrição
                                "TechSolutions S.A.",                 // Nome Fornecedor
                                "Em Atraso (Em trânsito)",            // Status do Produto
                                "15/11/2025",                         // Data de Chegada (Formato dd/MM/yyyy)
                                "Servidor/Datacenter Principal"       // Local Estoque
                        },

                        // Linha 2: Peça de Hardware Recebida
                        {
                                "Expansão da Infraestrutura de Rede",
                                "Roteador Industrial Modelo X500",
                                "Equipamento de rede para o novo anexo do armazém. Necessita de configuração.",
                                "Global Gear Ltda.",
                                "Recebido (Em validação)",
                                "28/10/2025",
                                "Armário 3B (Setor TI)"
                        },

                        // Linha 3: Item de Marketing em Planejamento
                        {
                                "Campanha de Lançamento Q1",
                                "Brindes Promocionais (Pen Drives 16GB)",
                                "Material de suporte para eventos e feiras durante o lançamento do novo produto.",
                                "PrintMaster Gráfica",
                                "Em Processamento (Produção)",
                                "10/01/2026",
                                "Armazém Principal - Área Marketing"
                        }
                },
                new String[] {"Nome do Projeto", "Nome do Produto", "Descrição", "Nome Fornecedor" , "Status do Produto", "Data de Chegada", "Local Estoque"}
        ));

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
