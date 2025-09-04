package br.org.umc.spring.projeto.services;

import br.org.umc.spring.projeto.DTOs.CargoDTO;
import br.org.umc.spring.projeto.model.Cargo;
import br.org.umc.spring.projeto.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Transactional
    public Cargo criarCargo(CargoDTO DTO) {
        // Verificar se já existe cargo com mesmo nome
        if (cargoRepository.existsByNome(DTO.getNome())) {
            throw new RuntimeException("Já existe um cargo com este nome");
        }

        Cargo cargo = new Cargo();
        cargo.setNome(DTO.getNome());
        cargo.setDescricao(DTO.getDescricao());
        cargo.setNivelHierarquico(DTO.getNivelHierarquico());

        return cargoRepository.save(cargo);
    }

    public List<Cargo> listarTodosCargos() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> buscarCargoPorId(Long id) {
        return cargoRepository.findById(id);
    }

    public Optional<Cargo> buscarCargoPorNome(String nome) {
        return cargoRepository.findByNome(nome);
    }

    @Transactional
    public Cargo atualizarCargo(Long id, CargoDTO request) {
        Optional<Cargo> cargoExistente = cargoRepository.findById(id);

        if (cargoExistente.isPresent()) {
            Cargo cargo = cargoExistente.get();

            // Verificar se o novo nome já existe (exceto para o próprio cargo)
            if (!cargo.getNome().equals(request.getNome()) &&
                    cargoRepository.existsByNome(request.getNome())) {
                throw new RuntimeException("Já existe um cargo com este nome");
            }

            cargo.setNome(request.getNome());
            cargo.setDescricao(request.getDescricao());
            cargo.setNivelHierarquico(request.getNivelHierarquico());

            return cargoRepository.save(cargo);
        }

        throw new RuntimeException("Cargo não encontrado");
    }

    @Transactional
    public void deletarCargo(Long id) {
        if (cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cargo não encontrado");
        }
    }
}
