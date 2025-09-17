package br.org.umc.spring.projeto.facade;

import br.org.umc.spring.projeto.DTOs.PedidoDTO;
import br.org.umc.spring.projeto.exception.EstoqueInsuficienteException;
import br.org.umc.spring.projeto.exception.RecursoNaoEncontradoException;
import br.org.umc.spring.projeto.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Fachada para operações de Pedido + Estoque.
 * Delega as operações para o PedidoService.
 */
@Service
public class PedidoEstoqueFacade {

    private final PedidoService pedidoService;

    public PedidoEstoqueFacade(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * Cria um novo pedido com os itens fornecidos
     *
     * @param pedidoDTO DTO com os dados do pedido
     * @return Pedido criado
     * @throws EstoqueInsuficienteException  Se não houver estoque para algum produto
     * @throws RecursoNaoEncontradoException Se algum recurso não for encontrado
     */
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        return pedidoService.criarPedido(pedidoDTO);
    }

    /**
     * Busca um pedido pelo ID
     *
     * @param id ID do pedido
     * @return Pedido encontrado
     * @throws RecursoNaoEncontradoException Se o pedido não for encontrado
     */
    public PedidoDTO consultarPedido(Long id) {
        return pedidoService.buscarPorId(id);
    }

    public PedidoDTO darBaixaNoPedido(Long id)
            throws RecursoNaoEncontradoException, EstoqueInsuficienteException {
        return pedidoService.darBaixaNoPedido(id);
    }

    /**
     * Lista todos os pedidos
     *
     * @return Lista de pedidos
     */
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarTodos();
    }
}
