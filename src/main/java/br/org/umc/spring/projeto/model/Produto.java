package br.org.umc.spring.projeto.model;

import java.math.BigDecimal;

public class Produto extends Domain {
    private String descricao;
    private BigDecimal valorUnitario;
    private long estoqueAtual;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public long getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(long estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public Produto(long id, String descricao, BigDecimal valorUnitario, long estoqueAtual) {
        super(id);
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.estoqueAtual = estoqueAtual;
    }
}
