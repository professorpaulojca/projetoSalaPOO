package br.org.umc.spring.projeto.controller;

import br.org.umc.spring.projeto.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/pedidos")
public class pedidos {

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        try {
            // Aqui você pode adicionar lógica de validação e processamento
            // Por exemplo, validar se os dados estão corretos

            // Simulando a criação de um pedido (em uma aplicação real, salvaria no banco)
            Pedido novoPedido = processarPedido(pedido);

            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private Pedido processarPedido(Pedido pedido) {
        // Adiciona timestamp atual se não foi fornecido
        if (pedido.getDataHora() == null) {
            pedido.setDataHora(new Timestamp(System.currentTimeMillis()));
        }

        // Valida se a lista de itens não é nula
        if (pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }

        // Aqui você pode adicionar mais lógica de processamento
        // como calcular total, validar estoque, etc.

        return pedido;
    }
}