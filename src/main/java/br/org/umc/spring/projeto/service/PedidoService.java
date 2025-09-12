package br.org.umc.spring.projeto.service;

import br.org.umc.spring.projeto.DTOs.ItemPedidoDTO;
import br.org.umc.spring.projeto.DTOs.PedidoDTO;
import br.org.umc.spring.projeto.exception.EstoqueInsuficienteException;
import br.org.umc.spring.projeto.exception.RecursoNaoEncontradoException;
import br.org.umc.spring.projeto.memory.PedidoStore;
import br.org.umc.spring.projeto.memory.ProdutoStore;
import br.org.umc.spring.projeto.model.ItemPedido;
import br.org.umc.spring.projeto.model.Pedido;
import br.org.umc.spring.projeto.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoStore pedidoStore;
    private final ProdutoStore produtoStore;

    public PedidoService(PedidoStore pedidoStore, ProdutoStore produtoStore) {
        this.pedidoStore = pedidoStore;
        this.produtoStore = produtoStore;
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setId(null); // Será gerado pelo store

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

    private ItemPedidoDTO toItemDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setProdutoId(item.getProduto().getId());
        dto.setQuantidade(item.getQuantidade());
        return dto;
    }
}
