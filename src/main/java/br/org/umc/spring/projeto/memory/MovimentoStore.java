package br.org.umc.spring.projeto.memory;

import br.org.umc.spring.projeto.model.Movimento;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MovimentoStore {

    private final CopyOnWriteArrayList<Movimento> dados = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(1L);

    public Movimento save(Movimento m) {
        if (m.getId() == null) m.setId(seq.getAndIncrement());
        dados.add(m);
        return m;
    }

    public List<Movimento> findAll() {
        return List.copyOf(dados);
    }

    public List<Movimento> findByProdutoId(Long produtoId) {
        return dados.stream()
                .filter(m -> m.getProduto() != null && produtoId.equals(m.getProduto().getId()))
                .collect(Collectors.toList());
    }

    public List<Movimento> findByPedidoId(Long pedidoId) {
        return dados.stream()
                .filter(m -> m.getPedido() != null && pedidoId.equals(m.getPedido().getId()))
                .collect(Collectors.toList());
    }

    public void clear() {
        dados.clear();
    }
}
