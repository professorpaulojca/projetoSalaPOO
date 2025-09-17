package br.org.umc.spring.projeto.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Item do Pedido com referência ao Produto, preço unitário e quantidade.
 * POJO puro (sem JPA).
 */
public class ItemPedido {

    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private boolean concluido;


    public ItemPedido() {
    }

    public ItemPedido(Produto produto, int quantidade, BigDecimal precoUnitario) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");
        if (precoUnitario == null || precoUnitario.signum() < 0) {
            throw new IllegalArgumentException("Preço unitário inválido");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    /* Getters/Setters */
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido)) return false;
        ItemPedido that = (ItemPedido) o;
        return quantidade == that.quantidade &&
                Objects.equals(produto, that.produto) &&
                Objects.equals(precoUnitario, that.precoUnitario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, quantidade, precoUnitario);
    }

    @Override
    public String toString() {
        return "ItemPedido{produto=" + (produto != null ? produto.getId() : null) +
                ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario + "}";
    }
}
