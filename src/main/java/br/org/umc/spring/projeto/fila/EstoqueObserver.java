package br.org.umc.spring.projeto.fila;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EstoqueObserver {
    @EventListener
    public void reservar(MsgEnvelope<PedidoCriado> e) {
        System.out.printf("[ESTOQUE] pedido %d%n", e.body().id());
    }
}
