package br.org.umc.spring.projeto.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Movimento {

    public enum TipoMovimento { ENTRADA, SAIDA }

    private Long id;
    private LocalDateTime dataHora = LocalDateTime.now();
    private TipoMovimento tipo;
    private Produto produto;
    private Pedido pedido;        // opcional, pode ser null
    private int quantidade;       // conv.: valor positivo; o tipo indica direção
    private String observacao;    // livre

    public Movimento() {}

    public Movimento(TipoMovimento tipo, Produto produto, int quantidade, Pedido pedido, String observacao) {
        if (tipo == null) throw new IllegalArgumentException("Tipo do movimento é obrigatório");
        if (produto == null) throw new IllegalArgumentException("Produto é obrigatório");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");

        this.tipo = tipo;
        this.produto = produto;
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.observacao = observacao;
        this.dataHora = LocalDateTime.now();
    }

    /* Fábricas convenientes */
    public static Movimento entrada(Produto produto, int quantidade, String observacao) {
        return new Movimento(TipoMovimento.ENTRADA, produto, quantidade, null, observacao);
    }

    public static Movimento saida(Produto produto, int quantidade, Pedido pedido, String observacao) {
        return new Movimento(TipoMovimento.SAIDA, produto, quantidade, pedido, observacao);
    }

    /* Getters/Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public TipoMovimento getTipo() { return tipo; }
    public void setTipo(TipoMovimento tipo) { this.tipo = tipo; }

    /** Compat: permite setar via String como "ENTRADA"/"SAIDA" */
    public void setTipo(String tipoComoTexto) {
        if (tipoComoTexto == null) throw new IllegalArgumentException("Tipo não pode ser nulo");
        this.tipo = TipoMovimento.valueOf(tipoComoTexto.trim().toUpperCase());
    }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movimento)) return false;
        Movimento that = (Movimento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Movimento{id=" + id + ", tipo=" + tipo + ", produto=" +
                (produto != null ? produto.getId() : null) + ", quantidade=" + quantidade +
                ", pedido=" + (pedido != null ? pedido.getId() : null) +
                ", dataHora=" + dataHora + ", obs='" + observacao + "'}";
    }
}
