package Pck_View_LIMS;
import Pck_Controller_LIMS.Controller_Tabela_Usuario_00;
import Pck_Model_LIMS.Model_Tabela_Usuario_00;

import javax.swing.*;
import java.awt.*;

public class Detalhes_Usuario extends JDialog {

    private JPanel contentPane;

    private JLabel lblTituloProduto, lblProduto, lblDescricao, lblTipo, lblValor, lblDataCadastro, lblDataChegada, lblStatus;
    private JLabel lblTituloProjeto, lblProjID, lblProjNome, lblProjDescricao, lblProjInicio, lblProjFim, lblProjOrcamento, lblProjStatus, lblProjDepartamento;
    private JLabel lblTituloFornecedor, lblFornNome, lblFornCNPJ, lblFornTelefone, lblFornEmail, lblFornEndereco;
    private JEditorPane DETALHESTextPane;
    private JTable table1;
    public Detalhes_Usuario(int id) {

        setTitle("Detalhes do Registro");
        setSize(500, 600);
        setModal(true);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);

        Controller_Tabela_Usuario_00 controller = new Controller_Tabela_Usuario_00();
        Model_Tabela_Usuario_00 m = controller.buscar_detalhes(id);

        // =======================
        // SEÇÃO: PRODUTO
        // =======================
        lblTituloProduto = titulo("DADOS DO PRODUTO");

        lblProduto = texto("Nome: " + m.getA00_nome_produto());
        lblDescricao = texto("Descrição: " + m.getA00_descricao_produto());
        lblTipo = texto("Tipo: " + m.getA00_tipo_produto());
        lblValor = texto("Valor Unitário: R$ " + m.getA00_valor_unitario());
        lblDataCadastro = texto("Data Cadastro: " + m.getA00_data_cadastro());
        lblDataChegada = texto("Data Chegada: " + m.getA00_data_chegada());

        contentPane.add(lblTituloProduto);
        contentPane.add(lblProduto);
        contentPane.add(lblDescricao);
        contentPane.add(lblTipo);
        contentPane.add(lblValor);
        contentPane.add(lblDataCadastro);
        contentPane.add(lblDataChegada);

        contentPane.add(Box.createVerticalStrut(15));

        // =======================
        // SEÇÃO: PROJETO
        // =======================
        lblTituloProjeto = titulo("PROJETO RELACIONADO");

        lblProjID = texto("ID Projeto: " + m.getA00_id_projeto());
        lblProjNome = texto("Nome: " + m.getA00_nome_projeto());
        lblProjDescricao = texto("Descrição: " + m.getA00_descricao_projeto());
        lblProjInicio = texto("Data Início: " + m.getA00_data_inicio());
        lblProjFim = texto("Data Fim: " + m.getA00_data_fim());
        lblProjOrcamento = texto("Orçamento: R$ " + m.getA00_orcamento());
        lblProjStatus = texto("Status: " + m.getA00_status_projeto());
        lblProjDepartamento = texto("Departamento: " + m.getA00_departamento());

        contentPane.add(lblTituloProjeto);
        contentPane.add(lblProjID);
        contentPane.add(lblProjNome);
        contentPane.add(lblProjDescricao);
        contentPane.add(lblProjInicio);
        contentPane.add(lblProjFim);
        contentPane.add(lblProjOrcamento);
        contentPane.add(lblProjStatus);
        contentPane.add(lblProjDepartamento);

        contentPane.add(Box.createVerticalStrut(15));

        // =======================
        // SEÇÃO: FORNECEDOR
        // =======================
        lblTituloFornecedor = titulo("FORNECEDOR");

        lblFornNome = texto("Nome: " + m.getA00_nome_fornecedor());
        lblFornCNPJ = texto("CNPJ: " + m.getA00_cnpj_fornecedor());
        lblFornTelefone = texto("Telefone: " + m.getA00_telefone_fornecedor());
        lblFornEmail = texto("Email: " + m.getA00_email_fornecedor());
        lblFornEndereco = texto("Endereço: " + m.getA00_endereco_fornecedor());

        contentPane.add(lblTituloFornecedor);
        contentPane.add(lblFornNome);
        contentPane.add(lblFornCNPJ);
        contentPane.add(lblFornTelefone);
        contentPane.add(lblFornEmail);
        contentPane.add(lblFornEndereco);
    }

    private JLabel titulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JLabel texto(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
}
