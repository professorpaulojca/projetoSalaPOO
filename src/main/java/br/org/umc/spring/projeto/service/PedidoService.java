package br.org.umc.spring.projeto.service;

import br.org.umc.spring.projeto.DTOs.ItemPedidoDTO;
import br.org.umc.spring.projeto.DTOs.PedidoDTO;
import br.org.umc.spring.projeto.enums.Status;
import br.org.umc.spring.projeto.exception.EstoqueInsuficienteException;
import br.org.umc.spring.projeto.exception.RecursoNaoEncontradoException;
import br.org.umc.spring.projeto.fila.FilaPedidos;
import br.org.umc.spring.projeto.fila.MsgEnvelope;
import br.org.umc.spring.projeto.fila.PedidoCriado;
import br.org.umc.spring.projeto.memory.MovimentoStore;
import br.org.umc.spring.projeto.memory.PedidoStore;
import br.org.umc.spring.projeto.memory.ProdutoStore;
import br.org.umc.spring.projeto.model.ItemPedido;
import br.org.umc.spring.projeto.model.Pedido;
import br.org.umc.spring.projeto.model.Produto;
import br.org.umc.spring.projeto.utilidades.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Logger log =                             // define logger
            LoggerFactory.getLogger(PedidoService.class);         // amarrado à classe

    private final PedidoStore pedidoStore;
    private final ProdutoStore produtoStore;
    private final MovimentoStore movimentoStore;
    private final FilaPedidos filaPedidos;

    @Autowired
    public PedidoService(PedidoStore pedidoStore, ProdutoStore produtoStore, MovimentoStore movimentoStore, FilaPedidos filaPedidos) {
        this.pedidoStore = pedidoStore;
        this.produtoStore = produtoStore;
        this.movimentoStore = movimentoStore;               // construtor p/ injeção
        this.filaPedidos = filaPedidos;                           // atribui dependência
    }

    public void criarPedido(PedidoCriado pedido) {                // método público de criação
        var envelope = new MsgEnvelope<>(                         // cria o envelope genérico
                "PedidoCriado",                                   // type lógico
                UUID.randomUUID().toString(),                     // messageId único
                Instant.now(),                                    // createdAt (agora)
                Map.of(                                           // headers simples
                        "correlationId", UUID.randomUUID().toString(), // id de correlação
                        "source", "PedidoService",                // origem
                        "contentType", "application/json"         // tipo de conteúdo
                ),
                pedido                                            // body: o payload do evento
        );                                                        // fim da construção do envelope

        log.info("ENFILEIRADO >>> \n{}", Jsons.toJson(envelope)); // loga envelope em JSON para didática

        boolean ok = filaPedidos.fila().offer(envelope);          // tenta colocar na fila sem bloquear
        if (!ok) {                                                // se a fila estiver cheia (se tiver limite)
            throw new IllegalStateException(                      // lança exceção (didático)
                    "Fila de pedidos cheia, tente novamente");    // mensagem clara
        }                                                         // fim if
    }


    @Transactional
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        //criar o pedido fazendo a conversão do dto para o model
        Pedido pedido = new Pedido();
        pedido.setDataHora(new Timestamp(System.currentTimeMillis()));
        pedido.setStatus(Status.PENDENTE);
        pedido.setItens(new ArrayList<>()); // Essencial para evitar NullPointerException


        // Aqui você pode adicionar lógica para buscar comprador e vendedor
        // pedido.setComprador(comprador);
        // pedido.setVendedor(vendedor);

        // Processa os itens do pedido
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoStore.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + itemDTO.getProdutoId()));

            // Verifica estoque
            if (produto.getEstoqueAtual() < itemDTO.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        String.format("Estoque insuficiente para o produto %s. Disponível: %d, Solicitado: %d",
                                produto.getDescricao(),
                                produto.getEstoqueAtual(),
                                itemDTO.getQuantidade()
                        )
                );
            }

            // Cria o item do pedido
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getValorUnitario());

            // Adiciona o item ao pedido
            pedido.getItens().add(itemPedido);

            // Atualiza o estoque
            produto.setEstoqueAtual(produto.getEstoqueAtual() - itemDTO.getQuantidade());
            produtoStore.save(produto);
        }

        // Salva o pedido
        Pedido pedidoSalvo = pedidoStore.save(pedido);
        return toDTO(pedidoSalvo);
    }

    public PedidoDTO buscarPorId(Long id) {
        return pedidoStore.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoStore.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        // Adicione outros campos conforme necessário

        // Converte itens para DTOs
        List<ItemPedidoDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
        dto.setItens(itensDTO);

        return dto;
    }

    @Transactional
    public PedidoDTO darBaixaNoPedido(Long id) {
        // 1) consulta pedido
        Pedido pedido = pedidoStore.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));

        // se já estiver concluído, apenas retorna (idempotência)
        if (isConcluido(pedido)) {
            return toDTO(pedido);
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalStateException("Pedido " + id + " não possui itens para baixa");
        }

        // 2) valida estoques de TODOS os itens antes de baixar (evita baixa parcial)
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto() == null || item.getProduto().getId() == 0) {
                throw new IllegalStateException("Item do pedido sem produto/ID definido");
            }
            Long produtoId = item.getProduto().getId();
            Produto produto = produtoStore.findById(produtoId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + produtoId));

            if (produto.getEstoqueAtual() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        String.format("Estoque insuficiente para o produto %s. Disponível: %d, Solicitado: %d",
                                produto.getDescricao(), produto.getEstoqueAtual(), item.getQuantidade()));
            }
        }

        // 3) aplica baixas e 4) registra movimentos (se MovimentoStore estiver disponível)
        for (ItemPedido item : pedido.getItens()) {
            Long produtoId = item.getProduto().getId();
            Produto produto = produtoStore.findById(produtoId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + produtoId));

            // baixa
            produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
            produtoStore.save(produto);

            // movimento (opcional, só se o MovimentoStore estiver injetado)
            if (movimentoStore != null) {
                br.org.umc.spring.projeto.model.Movimento mov =
                        br.org.umc.spring.projeto.model.Movimento.saida(
                                produto,
                                item.getQuantidade(),
                                pedido,
                                "Baixa por pedido " + id
                        );
                movimentoStore.save(mov);
            }
        }

        // 5) marca o pedido como CONCLUIDO (se a entidade tiver esse campo)
        marcarConcluidoSePossivel(pedido);
        pedidoStore.save(pedido);

        return toDTO(pedido);
    }

    private boolean isConcluido(Pedido pedido) {
        // Verifica se o pedido tem itens e se o tipo de movimento está definido
        if (pedido == null) {
            return false;
        }
        return pedido.getStatus() != Status.CONCLUIDO &&
                pedido.getItens() != null &&
                !pedido.getItens().isEmpty();
    }

    private void marcarConcluidoSePossivel(Pedido pedido) {
        if (pedido == null) {
            return;
        }

        boolean todosItensConcluidos = true;
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                if (!item.isConcluido()) {  // Assuming ItemPedido has an isConcluido() method
                    todosItensConcluidos = false;
                    break;
                }
            }
        }

        if (todosItensConcluidos) {
            // Assuming Pedido has a method to mark it as completed
            // If not, you might need to add a status field to the Pedido class
            pedido.setStatus(Status.CONCLUIDO);
        }
    }

    private ItemPedidoDTO toItemDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setProdutoId(item.getProduto().getId());
        dto.setQuantidade(item.getQuantidade());
        return dto;
    }


}
