package br.org.umc.spring.projeto.model;

public class Vendedor extends Funcionario{

    private String meta;
    private Empresa empresa;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Vendedor(Long id, String nome, String matricula, String meta, Empresa empresa) {
        super(id, nome, matricula);
        this.meta = meta;
        this.empresa = empresa;
    }
}
