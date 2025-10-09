package br.org.umc.spring.projeto.fila;

import br.org.umc.spring.projeto.DTOs.MsgEnvelope;
import br.org.umc.spring.projeto.DTOs.PedidoCriado;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Componente que "segura" a fila em memória e expõe acesso
@Component                                                        // registra no container do Spring
public class FilaPedidos {                                        // classe holder
    private final BlockingQueue<MsgEnvelope<PedidoCriado>> fila   // campo privado da fila
            = new LinkedBlockingQueue<>();                        // instância de LinkedBlockingQueue sem limite explícito

    public BlockingQueue<MsgEnvelope<PedidoCriado>> fila() {      // getter para expor a fila
        return fila;                                              // retorna a referência (mesma instância para todos)
    }                                                             // fim do getter
}
