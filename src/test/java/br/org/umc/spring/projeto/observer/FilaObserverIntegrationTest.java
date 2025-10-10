package br.org.umc.spring.projeto.observer;

import br.org.umc.spring.projeto.fila.MsgEnvelope;
import br.org.umc.spring.projeto.fila.PedidoCriado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest                                                     // sobe contexto Spring completo
class EventListenersSpyTest {                                       // teste de integração dos observers

    @Autowired
    ApplicationEventPublisher publisher;                            // injeta publisher de eventos

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // captura System.out
    private PrintStream originalOut;                                // guarda System.out original

    @BeforeEach
    void setUp() {                                                  // antes de cada teste
        originalOut = System.out;                                   // salva System.out original
        System.setOut(new PrintStream(outContent));                 // redireciona para buffer
    }

    @AfterEach
    void tearDown() {                                               // depois de cada teste
        System.setOut(originalOut);                                 // restaura System.out
    }

    @Test
    void publicaEnvelope_disparaAmbos() throws InterruptedException { // testa se ambos observers recebem evento
        var payload = new PedidoCriado(42L, "Aluno", 123.45);       // cria payload
        var env = new MsgEnvelope<>(                                // cria envelope
                "PedidoCriado",                                     // tipo do evento
                UUID.randomUUID().toString(),                       // id único
                Instant.now(),                                      // timestamp
                Map.of("correlationId", UUID.randomUUID().toString()), // headers
                payload                                             // body
        );

        publisher.publishEvent(env);                                // publica evento síncrono
        
        Thread.sleep(100);                                          // aguarda processamento (eventos síncronos)

        String output = outContent.toString();                      // captura saída do console
        
        // Verifica se ambos observers processaram o evento
        assertTrue(output.contains("[EMAIL] pedido 42"),            // EmailObserver processou
                "EmailObserver não processou o evento");
        assertTrue(output.contains("[ESTOQUE] pedido 42"),          // EstoqueObserver processou
                "EstoqueObserver não processou o evento");
    }
}
