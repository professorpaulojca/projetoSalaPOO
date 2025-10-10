package br.org.umc.spring.projeto.fila;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.time.Instant;
import java.util.Map;

public record MsgEnvelope<T>(
        String type,                             // tipo lógico do evento (ex.: "PedidoCriado")
        String messageId,                        // id único da mensagem (ex.: UUID)
        Instant createdAt,                       // quando o envelope foi criado
        Map<String, String> headers,             // metadados adicionais (tenant, correlationId...)
        T body                                   // o payload em si (evento de domínio)
) implements ResolvableTypeProvider {            // permite Spring resolver tipo genérico

    @Override
    public ResolvableType getResolvableType() {  // retorna tipo resolvível para Spring
        return ResolvableType.forClassWithGenerics(
                getClass(),                      // MsgEnvelope
                ResolvableType.forInstance(body) // tipo do body
        );
    }
}
