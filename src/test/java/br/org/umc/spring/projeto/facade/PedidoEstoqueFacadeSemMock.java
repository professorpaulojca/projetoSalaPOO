package br.org.umc.spring.projeto.facade;

import br.org.umc.spring.projeto.DTOs.ItemPedidoDTO;
import br.org.umc.spring.projeto.DTOs.PedidoDTO;
import br.org.umc.spring.projeto.enums.Status;
import br.org.umc.spring.projeto.memory.MovimentoStore;
import br.org.umc.spring.projeto.memory.PedidoStore;
import br.org.umc.spring.projeto.memory.ProdutoStore;
import br.org.umc.spring.projeto.model.Produto;
import br.org.umc.spring.projeto.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PedidoEstoqueFacadeSemMock {

    private PedidoEstoqueFacade facade;
    private ProdutoStore produtoStore;
    private PedidoStore pedidoStore;

    private Produto produto;

    @BeforeEach
    void setUp() {
        // 1. Inicializar as dependências reais (em memória)
        produtoStore = new ProdutoStore();
        pedidoStore = new PedidoStore();
        MovimentoStore movimentoStore = new MovimentoStore();

        PedidoService pedidoService = new PedidoService(pedidoStore, produtoStore, movimentoStore);
        facade = new PedidoEstoqueFacade(pedidoService);

        // 2. Configurar o estado inicial: criar um produto com estoque
        produto = new Produto();
        produto.setId(1L);
        produto.setDescricao("Produto de Teste");
        produto.setValorUnitario(BigDecimal.valueOf(10.0));
        produto.setEstoqueAtual(100);
        produtoStore.save(produto);
    }

    @Test
    @DisplayName("darBaixaNoPedido: deve dar baixa em um pedido e atualizar o estoque")
    void darBaixaNoPedido_deveConcluirPedidoEAtualizarEstoque() {
        // Arrange
        // Criar um DTO de item de pedido
        ItemPedidoDTO itemDTO = new ItemPedidoDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(5);

        // Criar um DTO de pedido
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCompradorId(1L);
        pedidoDTO.setVendedorId(2L);
        pedidoDTO.setItens(Collections.singletonList(itemDTO));

        // Primeiro, criar o pedido para que ele exista no sistema
        PedidoDTO pedidoCriado = facade.criarPedido(pedidoDTO);
        assertNotNull(pedidoCriado, "O pedido deveria ter sido criado.");
        assertEquals(95, produtoStore.findById(1L).get().getEstoqueAtual(), "O estoque deveria ser debitado na criação do pedido.");

        // Act
        // Dar baixa no pedido criado
        PedidoDTO pedidoBaixado = facade.darBaixaNoPedido(pedidoCriado.getId());

        // Assert
        assertNotNull(pedidoBaixado, "O resultado da baixa não pode ser nulo.");
        // A lógica atual não define o status no DTO de retorno, então vamos verificar o objeto real na store.
        // Se a sua implementação de toDTO for atualizada para incluir o status, podemos mudar este assert.
        assertEquals(Status.CONCLUIDO, pedidoStore.findById(pedidoCriado.getId()).get().getStatus(), "O status do pedido deveria ser CONCLUIDO.");

        // Verificar se o estoque do produto foi atualizado corretamente
        Produto produtoAtualizado = produtoStore.findById(produto.getId()).orElse(null);
        assertNotNull(produtoAtualizado, "O produto não deveria ser nulo.");
        // O estoque já foi debitado na criação, a baixa apenas conclui o pedido.
        assertEquals(95, produtoAtualizado.getEstoqueAtual(), "O estoque não deveria mudar ao dar baixa em um pedido já processado.");
    }
}