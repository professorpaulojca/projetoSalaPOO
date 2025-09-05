package br.org.umc.spring.projeto.model;

public class Produto {
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

    public Produto(String descricao, double valorUnitario) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }
}
