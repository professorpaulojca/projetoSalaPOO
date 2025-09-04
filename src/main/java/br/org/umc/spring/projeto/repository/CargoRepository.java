package br.org.umc.spring.projeto.repository;


import br.org.umc.spring.projeto.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    // Método customizado para buscar por nome
    Optional<Cargo> findByNome(String nome);

    // Método para verificar se existe por nome
    boolean existsByNome(String nome);

    // Método para buscar cargos por nível hierárquico
    List<Cargo> findByNivelHierarquico(Integer nivelHierarquico);
}
