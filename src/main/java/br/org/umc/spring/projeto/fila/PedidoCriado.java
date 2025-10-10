package br.org.umc.spring.projeto.fila;

public record PedidoCriado(
        Long id,                                 // identificador do pedido
        String cliente,                          // nome/identificação do cliente
        Double total                             // valor total do pedido
) {
}