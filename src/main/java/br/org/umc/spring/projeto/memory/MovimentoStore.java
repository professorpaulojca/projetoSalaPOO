package br.org.umc.spring.projeto.memory;

import br.org.umc.spring.projeto.model.Movimento;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Store em memória para registrar e consultar movimentos de estoque.
 * Thread-safe via CopyOnWriteArrayList.
 */
@Component
public class MovimentoStore {

    private final CopyOnWriteArrayList<Movimento> dados = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(1L);

    /**
     * Salva um movimento. Se o ID for null, gera um novo.
     */
    public Movimento save(Movimento m) {
        if (m == null) throw new IllegalArgumentException("Movimento não pode ser nulo");
        if (m.getId() == null) m.setId(seq.getAndIncrement());
        dados.add(m);
        return m;
    }

    /**
     * Busca por ID.
     */
    public Optional<Movimento> findById(Long id) {
        if (id == null) return Optional.empty();
        return dados.stream().filter(m -> Objects.equals(m.getId(), id)).findFirst();
    }

    /**
     * Retorna uma cópia imutável de todos os movimentos.
     */
    public List<Movimento> findAll() {
        return List.copyOf(dados);
    }

    /**
     * Movimentos por produto.
     */
    public List<Movimento> findByProdutoId(Long produtoId) {
        return dados.stream()
                .filter(m -> m.getProduto() != null && Objects.equals(produtoId, m.getProduto().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Movimentos por pedido.
     */
    public List<Movimento> findByPedidoId(Long pedidoId) {
        return dados.stream()
                .filter(m -> m.getPedido() != null && Objects.equals(pedidoId, m.getPedido().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Movimentos pelo tipo (ENTRADA/SAIDA).
     */
    public List<Movimento> findByTipo(Movimento.TipoMovimento tipo) {
        return dados.stream()
                .filter(m -> m.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    /**
     * Últimos N movimentos por data/hora (desc).
     */
    public List<Movimento> latest(int n) {
        return dados.stream()
                .sorted(Comparator.comparing(Movimento::getDataHora).reversed())
                .limit(Math.max(0, n))
                .collect(Collectors.toList());
    }

    /**
     * Remove por ID.
     */
    public boolean deleteById(Long id) {
        return dados.removeIf(m -> Objects.equals(m.getId(), id));
    }

    /**
     * Quantidade total.
     */
    public long count() {
        return dados.size();
    }

    /**
     * Limpa tudo e reseta a sequência.
     */
    public void clear() {
        dados.clear();
        seq.set(1L);
    }
}
