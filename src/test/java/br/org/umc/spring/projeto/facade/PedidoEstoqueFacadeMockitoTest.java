package br.org.umc.spring.projeto.facade;


import br.org.umc.spring.projeto.DTOs.ItemPedidoDTO;
import br.org.umc.spring.projeto.DTOs.PedidoDTO;
import br.org.umc.spring.projeto.enums.Status;
import br.org.umc.spring.projeto.exception.EstoqueInsuficienteException;
import br.org.umc.spring.projeto.exception.RecursoNaoEncontradoException;
import br.org.umc.spring.projeto.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoEstoqueFacadeMockitoTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoEstoqueFacade facade;

    private PedidoDTO pedidoDTO;
    private ItemPedidoDTO item1;
    private ItemPedidoDTO item2;

    @BeforeEach
    void setUp() {
        // Configuração dos itens do pedido
        item1 = new ItemPedidoDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(3);

        item2 = new ItemPedidoDTO();
        item2.setProdutoId(2L);
        item2.setQuantidade(2);

        // Configuração do pedido
        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(100L);
        pedidoDTO.setCompradorId(1L);
        pedidoDTO.setVendedorId(1L);
        pedidoDTO.setItens(Arrays.asList(item1, item2));
    }

    @Test
    @DisplayName("criarPedido: deve criar um novo pedido com sucesso")
    void criarPedido_deveCriarPedidoComSucesso() {
        // Arrange
        when(pedidoService.criarPedido(any(PedidoDTO.class))).thenReturn(pedidoDTO);

        // Act
        PedidoDTO resultado = facade.criarPedido(pedidoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals(2, resultado.getItens().size());
        verify(pedidoService).criarPedido(pedidoDTO);
    }

    @Test
    @DisplayName("criarPedido: deve lançar EstoqueInsuficienteException quando não houver estoque")
    void criarPedido_deveLancarExcecaoQuandoSemEstoque() {
        // Arrange
        when(pedidoService.criarPedido(any(PedidoDTO.class)))
                .thenThrow(new EstoqueInsuficienteException("Estoque insuficiente"));

        // Act & Assert
        assertThrows(EstoqueInsuficienteException.class,
                () -> facade.criarPedido(pedidoDTO));

        verify(pedidoService).criarPedido(pedidoDTO);
    }

    @Test
    @DisplayName("consultarPedido: deve retornar pedido existente")
    void consultarPedido_deveRetornarPedidoExistente() {
        // Arrange
        when(pedidoService.buscarPorId(100L)).thenReturn(pedidoDTO);

        // Act
        PedidoDTO resultado = facade.consultarPedido(100L);

        // Assert
        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        verify(pedidoService).buscarPorId(100L);
    }

    @Test
    @DisplayName("consultarPedido: deve lançar exceção quando pedido não encontrado")
    void consultarPedido_deveLancarExcecaoQuandoNaoEncontrado() {
        // Arrange
        when(pedidoService.buscarPorId(999L))
                .thenThrow(new RecursoNaoEncontradoException("Pedido não encontrado"));

        // Act & Assert
        assertThrows(RecursoNaoEncontradoException.class,
                () -> facade.consultarPedido(999L));

        verify(pedidoService).buscarPorId(999L);
    }

    @Test
    @DisplayName("listarPedidos: deve retornar lista de pedidos")
    void listarPedidos_deveRetornarListaDePedidos() {
        // Arrange
        List<PedidoDTO> pedidos = Collections.singletonList(pedidoDTO);
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        // Act
        List<PedidoDTO> resultado = facade.listarPedidos();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getId());
        verify(pedidoService).listarTodos();
    }

    @Test
    @DisplayName("listarPedidos: deve retornar lista vazia quando não houver pedidos")
    void listarPedidos_deveRetornarListaVazia() {
        // Arrange
        when(pedidoService.listarTodos()).thenReturn(Collections.emptyList());

        // Act
        List<PedidoDTO> resultado = facade.listarPedidos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pedidoService).listarTodos();
    }

    @Test
    @DisplayName("listarPedidos: deve retornar lista vazia quando não houver pedidos")
    void dar_baixa_em_um_pedido() {

        pedidoService.darBaixaNoPedido(100L);

        when(pedidoService.buscarPorId(100L)).thenReturn(pedidoDTO);

        PedidoDTO resultado = facade.consultarPedido(100L);

        assertTrue(resultado.getStatus() == Status.CONCLUIDO);


    }

}