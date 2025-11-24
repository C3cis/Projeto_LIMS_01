package Pck_View_LIMS;
import Pck_Controller_LIMS.Controller_Localizacao_07;
import Pck_Controller_LIMS.Controller_Usuario_11;
import Pck_Model_LIMS.Model_Localizacao_07;
import Pck_Model_LIMS.Model_Usuario_11;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Localizacao extends JDialog {

    private JPanel contentPane;
    private JTextField textField2Setor; // Setor
    private JTextField textField3Identificacao; // Identificação
    private JComboBox comboBox1Usuario; // usuário (ID - Nome)
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton buscarButton;
    private JButton sairButton;
    private JTable table1Geral;
    private DefaultTableModel tableModel;

    private Controller_Localizacao_07 controller;
    private Controller_Usuario_11 controllerUsuario;

    private int selecionadoId = -1;

    public Localizacao() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Cadastro de Localização");
        setSize(820, 520);
        setLocationRelativeTo(null);

        controller = new Controller_Localizacao_07();
        controllerUsuario = new Controller_Usuario_11();

        inicializarTabela();
        carregarUsuarios();
        preencherTabela();

        // Listeners
        salvarButton.addActionListener(e -> onSalvar());
        editarButton.addActionListener(e -> onEditar());
        excluirButton.addActionListener(e -> onExcluir());
        buscarButton.addActionListener(e -> onBuscar());
        sairButton.addActionListener(e -> dispose());

        table1Geral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onTabelaClick();
            }
        });

        // Esc para fechar (opcional)
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // -------------------------
    // Inicializar tabela
    // -------------------------
    private void inicializarTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Setor", "Identificação", "Usuário"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table1Geral.setModel(tableModel);
    }

    // -------------------------
    // Carregar usuários no combo (ID - Nome)
    // -------------------------
    private void carregarUsuarios() {
        comboBox1Usuario.removeAllItems();
        try {
            ArrayList<Model_Usuario_11> lista = controllerUsuario.listar();
            if (lista != null && !lista.isEmpty()) {
                for (Model_Usuario_11 u : lista) {
                    // ajusta para os nomes dos getters do model de usuário
                    comboBox1Usuario.addItem(u.getA11_id_usuario() + " - " + u.getA11_nome());
                }
            } else {
                comboBox1Usuario.addItem("0 - (sem usuários)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // -------------------------
    // Preencher tabela
    // -------------------------
    private void preencherTabela() {
        tableModel.setRowCount(0);
        try {
            List<Model_Localizacao_07> lista = controller.listar();
            if (lista != null) {
                for (Model_Localizacao_07 l : lista) {
                    // OBS: supondo que seu model tem getA07_id_usuario() e getA07_id_localizacao(), etc.
                    String usuarioExibicao = String.valueOf(l.getA07_id_usuario());
                    // se quiser tentar mostrar nome do usuário junto, buscamos no controllerUsuario:
                    try {
                        Model_Usuario_11 u = controllerUsuario.buscar(l.getA07_id_usuario());
                        if (u != null) usuarioExibicao = u.getA11_id_usuario() + " - " + u.getA11_nome();
                    } catch (Exception ignored) {}

                    tableModel.addRow(new Object[]{
                            l.getA07_id_localizacao(),
                            l.getA07_setor(),
                            l.getA07_identificacao(),
                            usuarioExibicao
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao preencher tabela: " + e.getMessage());
        }
    }

    // -------------------------
    // Extrai ID da string "ID - Nome"
    // -------------------------
    private int extrairIdDoCombo(Object item) {
        if (item == null) return 0;
        String s = item.toString().trim();
        if (s.isEmpty()) return 0;
        String[] parts = s.split(" - ");
        try {
            return Integer.parseInt(parts[0].trim());
        } catch (Exception ex) {
            return 0;
        }
    }

    // -------------------------
    // Ações: SALVAR
    // -------------------------
    private void onSalvar() {
        try {
            String setor = textField2Setor.getText().trim();
            String identificacao = textField3Identificacao.getText().trim();
            int idUsuario = extrairIdDoCombo(comboBox1Usuario.getSelectedItem());

            if (setor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o setor.");
                return;
            }
            if (identificacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe a identificação.");
                return;
            }
            if (idUsuario <= 0) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário válido.");
                return;
            }

            Model_Localizacao_07 model = new Model_Localizacao_07();
            // ajuste dos nomes dos setters conforme seu model
            model.setA07_setor(setor);
            model.setA07_identificacao(identificacao);
            model.setA07_id_usuario(idUsuario);

            boolean ok = controller.inserir(model);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Localização salva com sucesso.");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar localização.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    // -------------------------
    // Ações: EDITAR
    // -------------------------
    private void onEditar() {
        if (selecionadoId <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para editar.");
            return;
        }

        try {
            String setor = textField2Setor.getText().trim();
            String identificacao = textField3Identificacao.getText().trim();
            int idUsuario = extrairIdDoCombo(comboBox1Usuario.getSelectedItem());

            if (setor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o setor.");
                return;
            }
            if (identificacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe a identificação.");
                return;
            }
            if (idUsuario <= 0) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário válido.");
                return;
            }

            Model_Localizacao_07 model = new Model_Localizacao_07();
            model.setA07_id_localizacao(selecionadoId);
            model.setA07_setor(setor);
            model.setA07_identificacao(identificacao);
            model.setA07_id_usuario(idUsuario);

            boolean ok = controller.atualizar(model);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Localização atualizada.");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar localização.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    // -------------------------
    // Ações: EXCLUIR
    // -------------------------
    private void onExcluir() {
        if (selecionadoId <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Excluir",
                JOptionPane.YES_NO_OPTION);
        if (resp != JOptionPane.YES_OPTION) return;

        try {
            boolean ok = controller.excluir(selecionadoId);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Localização excluída.");
                limparCampos();
                preencherTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir localização.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    // -------------------------
    // Ações: BUSCAR
    // -------------------------
    private void onBuscar() {
        String s = JOptionPane.showInputDialog(this, "ID para buscar:");
        if (s == null || s.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(s.trim());
            Model_Localizacao_07 l = controller.buscar(id);
            if (l == null) {
                JOptionPane.showMessageDialog(this, "Localização não encontrada.");
                return;
            }
            carregarModeloNosCampos(l);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }

    // -------------------------
    // Ao clicar linha da tabela
    // -------------------------
    private void onTabelaClick() {
        int row = table1Geral.getSelectedRow();
        if (row < 0) return;
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            Model_Localizacao_07 l = controller.buscar(id);
            if (l != null) carregarModeloNosCampos(l);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao selecionar linha: " + e.getMessage());
        }
    }

    private void carregarModeloNosCampos(Model_Localizacao_07 l) {
        selecionadoId = l.getA07_id_localizacao();
        textField2Setor.setText(l.getA07_setor());
        textField3Identificacao.setText(l.getA07_identificacao());

        // selecionar usuário no combo usando "ID - Nome" se existir
        String idStr = String.valueOf(l.getA07_id_usuario());
        for (int i = 0; i < comboBox1Usuario.getItemCount(); i++) {
            String it = comboBox1Usuario.getItemAt(i).toString();
            if (it.startsWith(idStr + " -") || it.equals(idStr)) {
                comboBox1Usuario.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparCampos() {
        selecionadoId = -1;
        textField2Setor.setText("");
        textField3Identificacao.setText("");
        if (comboBox1Usuario.getItemCount() > 0) comboBox1Usuario.setSelectedIndex(0);
    }
}
