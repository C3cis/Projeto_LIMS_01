package Pck_Model_LIMS;

import java.sql.Date;

public class Model_Projeto_01 {
    private int A01_id_projeto;
    private String A01_nome_projeto;
    private String A01_descricao;
    private Date A01_data_inicial;
    private Date A01_data_final;
    private double A01_orcamento;
    private String A01_status_projeto;
    private String A01_departamento;
    private int A01_id_usuario;

    // getters e setters
    public int getA01_id_projeto() { return A01_id_projeto; }
    public void setA01_id_projeto(int a01_id_projeto) { this.A01_id_projeto = a01_id_projeto; }

    public String getA01_nome_projeto() { return A01_nome_projeto; }
    public void setA01_nome_projeto(String a01_nome_projeto) { this.A01_nome_projeto = a01_nome_projeto; }

    public String getA01_descricao() { return A01_descricao; }
    public void setA01_descricao(String a01_descricao) { this.A01_descricao = a01_descricao; }

    public Date getA01_data_inicial() { return A01_data_inicial; }
    public void setA01_data_inicial(Date a01_data_inicial) { this.A01_data_inicial = a01_data_inicial; }

    public Date getA01_data_final() { return A01_data_final; }
    public void setA01_data_final(Date a01_data_final) { this.A01_data_final = a01_data_final; }

    public double getA01_orcamento() { return A01_orcamento; }
    public void setA01_orcamento(double a01_orcamento) { this.A01_orcamento = a01_orcamento; }

    public String getA01_status_projeto() { return A01_status_projeto; }
    public void setA01_status_projeto(String a01_status_projeto) { this.A01_status_projeto = a01_status_projeto; }

    public String getA01_departamento() { return A01_departamento; }
    public void setA01_departamento(String a01_departamento) { this.A01_departamento = a01_departamento; }

    public int getA01_id_usuario() { return A01_id_usuario; }
    public void setA01_id_usuario(int a01_id_usuario) { this.A01_id_usuario = a01_id_usuario; }
}