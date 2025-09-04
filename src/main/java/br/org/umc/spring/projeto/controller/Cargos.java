package br.org.umc.spring.projeto.controller;

import br.org.umc.spring.projeto.DTOs.CargoDTO;
import br.org.umc.spring.projeto.model.Cargo;
import br.org.umc.spring.projeto.services.CargoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargos")
public class Cargos {

    @Autowired
    private CargoService cargoService;

    @PostMapping
    public ResponseEntity<?> criarCargo(@Valid @RequestBody CargoDTO DTO) {
        try {
            Cargo cargo = cargoService.criarCargo(DTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> listarCargos() {
        List<Cargo> cargos = cargoService.listarTodosCargos();
        return ResponseEntity.ok(cargos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCargoPorId(@PathVariable Long id) {
        Optional<Cargo> cargo = cargoService.buscarCargoPorId(id);

        if (cargo.isPresent()) {
            return ResponseEntity.ok(cargo.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cargo não encontrado");
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> buscarCargoPorNome(@PathVariable String nome) {
        Optional<Cargo> cargo = cargoService.buscarCargoPorNome(nome);

        if (cargo.isPresent()) {
            return ResponseEntity.ok(cargo.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cargo não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCargo(@PathVariable Long id,
                                            @Valid @RequestBody CargoDTO DTO) {
        try {
            Cargo cargo = cargoService.atualizarCargo(id, DTO);
            return ResponseEntity.ok(cargo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCargo(@PathVariable Long id) {
        try {
            cargoService.deletarCargo(id);
            return ResponseEntity.ok().body("Cargo deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}