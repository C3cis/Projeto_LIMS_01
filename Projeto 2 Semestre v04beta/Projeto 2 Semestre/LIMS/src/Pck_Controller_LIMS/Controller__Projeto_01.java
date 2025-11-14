package Pck_Controller_LIMS;

import java.util.Date;


public class Controller__Projeto_01 {
    private int a01_id_projeto;
    private String a01_nome_projeto;
    private String a01_descricao;
    private Date a01_data_inicio;
    private Date a01_data_fim;
    private double a01_estimativa;
    private String a01_status_atividade;
    private String a01_departamento;


    public Controller__Projeto_01() {}


    public Controller__Projeto_01(int a01_id_projeto, String a01_nome_projeto, String a01_descricao,
                                  Date a01_data_inicio, Date a01_data_fim, double a01_estimativa,
                                  String a01_status_atividade, String a01_departamento) {
        this.a01_id_projeto = a01_id_projeto;
        this.a01_nome_projeto = a01_nome_projeto;
        this.a01_descricao = a01_descricao;
        this.a01_data_inicio = a01_data_inicio;
        this.a01_data_fim = a01_data_fim;
        this.a01_estimativa = a01_estimativa;
        this.a01_status_atividade = a01_status_atividade;
        this.a01_departamento = a01_departamento;
    }


    // SET
    public void setA01_id_projeto(int a01_id_projeto) { this.a01_id_projeto = a01_id_projeto; }
    public void setA01_nome_projeto(String a01_nome_projeto) { this.a01_nome_projeto = a01_nome_projeto; }
    public void setA01_descricao(String a01_descricao) { this.a01_descricao = a01_descricao; }
    public void setA01_data_inicio(Date a01_data_inicio) { this.a01_data_inicio = a01_data_inicio; }
    public void setA01_data_fim(Date a01_data_fim) { this.a01_data_fim = a01_data_fim; }
    public void setA01_estimativa(double a01_estimativa) { this.a01_estimativa = a01_estimativa; }
    public void setA01_status_atividade(String a01_status_atividade) { this.a01_status_atividade = a01_status_atividade; }
    public void setA01_departamento(String a01_departamento) { this.a01_departamento = a01_departamento; }


    // GET
    public int getA01_id_projeto() { return a01_id_projeto; }
    public String getA01_nome_projeto() { return a01_nome_projeto; }
    public String getA01_descricao() { return a01_descricao; }
    public Date getA01_data_inicio() { return a01_data_inicio; }
    public Date getA01_data_fim() { return a01_data_fim; }
    public double getA01_estimativa() { return a01_estimativa; }
    public String getA01_status_atividade() { return a01_status_atividade; }
    public String getA01_departamento() { return a01_departamento; }
}
