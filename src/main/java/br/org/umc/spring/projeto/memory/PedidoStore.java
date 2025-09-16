package br.org.umc.spring.projeto.memory;

import br.org.umc.spring.projeto.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PedidoStore {

    private final ConcurrentHashMap<Long, Pedido> dados = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1L);

    public Pedido save(Pedido p) {
        if (p.getId() == null) p.setId(seq.getAndIncrement());
        dados.put(p.getId(), p);
        return p;
    }

    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    public Collection<Pedido> findAll() {
        return Collections.unmodifiableCollection(dados.values());
    }

    public boolean deleteById(Long id) {
        return dados.remove(id) != null;
    }

    public void clear() {
        dados.clear();
    }
}
