package br.org.umc.spring.projeto.observer;

import br.org.umc.spring.projeto.DTOs.MsgEnvelope;
import br.org.umc.spring.projeto.DTOs.PedidoCriado;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component                                                        // registra no Spring
public class EstoqueObserver {                                    // observer de estoque
    @Order(2)                                                     // se quiser ordenar entre listeners (opcional)
    @EventListener                                                // marca como ouvinte
    public void reservar(MsgEnvelope<PedidoCriado> e) {           // recebe envelope de PedidoCriado
        System.out.printf(                                        // imprime didaticamente
                "[ESTOQUE] msgId=%s -> Reservando itens do pedido %d%n",
                e.messageId(),                                    // imprime id da mensagem
                e.body().id()                                     // imprime id do pedido
        );                                                        // fim printf
    }                                                             // fim listener
}
