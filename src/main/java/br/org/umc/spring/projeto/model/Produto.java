package br.org.umc.spring.projeto.model;

public class Produto extends Domain {
    private String descricao;
    private double valorUnitario;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Produto(long id, String descricao, double valorUnitario) {
        super(id);
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }
}
