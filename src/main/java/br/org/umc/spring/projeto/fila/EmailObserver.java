package br.org.umc.spring.projeto.fila;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver {
    @EventListener
    public void on(MsgEnvelope<PedidoCriado> e) {
        System.out.printf("[EMAIL] pedido %d%n", e.body().id());
    }
}                                             // fim da classe

