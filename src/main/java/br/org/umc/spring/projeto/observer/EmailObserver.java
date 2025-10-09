package br.org.umc.spring.projeto.observer;

import br.org.umc.spring.projeto.DTOs.MsgEnvelope;
import br.org.umc.spring.projeto.DTOs.PedidoCriado;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component                                                        // componente gerenciado
public class EmailObserver {                                      // classe do observer de e-mail
    @EventListener                                                // indica que este método observa eventos
    public void on(MsgEnvelope<PedidoCriado> e) {                 // recebe envelopes de PedidoCriado
        var p = e.body();                                         // extrai o payload do envelope
        System.out.printf(                                        // imprime didaticamente no console
                "[EMAIL] cid=%s -> Cliente %s, pedido %d (R$ %.2f)%n",
                e.headers().get("correlationId"),                 // mostra header de correlação
                p.cliente(),                                      // nome do cliente
                p.id(),                                           // id do pedido
                p.total()                                         // total do pedido
        );                                                        // fim printf
    }                                                             // fim do método listener
}                                                                 // fim da classe

