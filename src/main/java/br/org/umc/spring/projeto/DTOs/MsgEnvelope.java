package br.org.umc.spring.projeto.DTOs;

import java.time.Instant;
import java.util.Map;

public record MsgEnvelope<T>(
        String type,                             // tipo lógico do evento (ex.: "PedidoCriado")
        String messageId,                        // id único da mensagem (ex.: UUID)
        Instant createdAt,                       // quando o envelope foi criado
        Map<String, String> headers,             // metadados adicionais (tenant, correlationId...)
        T body                                   // o payload em si (evento de domínio)
) {
}
