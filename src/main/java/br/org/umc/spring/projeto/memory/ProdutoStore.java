package br.org.umc.spring.projeto.memory;

import br.org.umc.spring.projeto.model.Produto;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProdutoStore {

    private final ConcurrentHashMap<Long, Produto> dados = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1L);

    public Produto save(Produto p) {
        if (p.getId() == 0) p.setId(seq.getAndIncrement());
        dados.put(p.getId(), p);
        return p;
    }

    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    public Collection<Produto> findAll() {
        return Collections.unmodifiableCollection(dados.values());
    }

    public boolean deleteById(Long id) {
        return dados.remove(id) != null;
    }

    public void putAll(Collection<Produto> produtos) {
        if (produtos == null) return;
        produtos.forEach(this::save);
    }

    public void clear() {
        dados.clear();
    }
}
