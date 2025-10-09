package br.org.umc.spring.projeto.fila;

import br.org.umc.spring.projeto.DTOs.MsgEnvelope;
import br.org.umc.spring.projeto.DTOs.PedidoCriado;
import br.org.umc.spring.projeto.utilidades.Jsons;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component                                                        // registra o worker no Spring
public class FilaWorker {                                         // classe do worker
    private static final Logger log =                             // logger da classe
            LoggerFactory.getLogger(FilaWorker.class);            // instância de logger

    private final FilaPedidos filaPedidos;                        // dependência: holder da fila
    private final ApplicationEventPublisher publisher;            // publisher de eventos Spring

    public FilaWorker(FilaPedidos filaPedidos,                    // construtor com DI
                      ApplicationEventPublisher publisher) {      // injeta publisher
        this.filaPedidos = filaPedidos;                           // atribui fila
        this.publisher = publisher;                               // atribui publisher
    }                                                             // fim construtor

    @PostConstruct
        // executa após o bean estar pronto
    void start() {                                                // método para iniciar a thread
        Thread t = new Thread(() -> {                             // cria nova thread anônima
            while (!Thread.currentThread().isInterrupted()) {     // laço até interrupção
                try {                                             // bloco que pode bloquear
                    MsgEnvelope<PedidoCriado> msg =               // pega referência tipada
                            filaPedidos.fila().take();            // BLOQUEIA até haver item na fila

                    log.info("CONSUMIDO <<< \n{}",                // loga o envelope consumido
                            Jsons.toJson(msg));                   // em JSON bonitinho

                    publisher.publishEvent(msg);                  // publica o envelope como evento Spring
                    // (todos @EventListener compatíveis recebem)
                } catch (InterruptedException ie) {               // se a thread for interrompida
                    Thread.currentThread().interrupt();           // restaura flag de interrupção
                } catch (Exception e) {                           // qualquer outra exceção
                    log.error("Erro no worker da fila", e);       // loga o erro
                }                                                 // fim catch
            }                                                     // fim while
        }, "fila-pedidos-worker");                                // nome da thread (facilita debugar)

        t.setDaemon(true);                                        // marca thread como daemon (não impede shutdown)
        t.start();                                                // inicia a thread
    }
}
