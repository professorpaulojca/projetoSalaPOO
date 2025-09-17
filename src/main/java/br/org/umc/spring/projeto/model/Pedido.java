package br.org.umc.spring.projeto.model;

import br.org.umc.spring.projeto.enums.Status;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Pedido {
    private Long id;
    private Pessoa comprador;
    private Vendedor vendedor;
    private Timestamp dataHora;
    private ArrayList<ItemPedido> itens;
    private Status status;


    public Pedido() {

    }

    public Pedido(Long id, Pessoa comprador, Vendedor vendedor, Timestamp dataHora, ArrayList<ItemPedido> itens, Status status) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.dataHora = dataHora;
        this.itens = itens;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getComprador() {
        return comprador;
    }

    public void setComprador(Pessoa comprador) {
        this.comprador = comprador;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }


}
