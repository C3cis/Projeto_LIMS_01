package Pck_Model_LIMS;

public class Model_Fornecedor_04 {
    private int a10_id_fornecedor;
    private String a10_nome;
    private String a10_cnpj;
    private String a10_telefone;
    private String a10_email;
    private String a10_endereco;

    public Model_Fornecedor_04() {}

    public Model_Fornecedor_04(int a10_id_fornecedor, String a10_nome, String a10_cnpj, String a10_telefone, String a10_email, String a10_endereco) {
        this.a10_id_fornecedor = a10_id_fornecedor;
        this.a10_nome = a10_nome;
        this.a10_cnpj = a10_cnpj;
        this.a10_telefone = a10_telefone;
        this.a10_email = a10_email;
        this.a10_endereco = a10_endereco;
    }

    // SET
    public void setA10_id_fornecedor(int a10_id_fornecedor) { this.a10_id_fornecedor = a10_id_fornecedor; }
    public void setA10_nome(String a10_nome) { this.a10_nome = a10_nome; }
    public void setA10_cnpj(String a10_cnpj) { this.a10_cnpj = a10_cnpj; }
    public void setA10_telefone(String a10_telefone) { this.a10_telefone = a10_telefone; }
    public void setA10_email(String a10_email) { this.a10_email = a10_email; }
    public void setA10_endereco(String a10_endereco) { this.a10_endereco = a10_endereco; }


    // GET
    public int getA10_id_fornecedor() { return a10_id_fornecedor; }
    public String getA10_nome() { return a10_nome; }
    public String getA10_cnpj() { return a10_cnpj; }
    public String getA10_telefone() { return a10_telefone; }
    public String getA10_email() { return a10_email; }
    public String getA10_endereco() { return a10_endereco; }
}

