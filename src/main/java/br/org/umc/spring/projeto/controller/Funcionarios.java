package br.org.umc.spring.projeto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionario")
@Tag(name = "Funcionários", description = "API para gerenciamento de funcionários")
public class Funcionarios {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @Operation(summary = "Criar um novo funcionario")
    public ResponseEntity<?> criar(@Valid @RequestBody Funcionario funcionario) {
        try {
            funcionarioService.cadastrarFuncionario(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar funcionário: " + e.getMessage());
        }
    }

    // Método adicional para teste
    @GetMapping("/teste")
    @Operation(summary = "Teste de funcionamento")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("API funcionando!");
    }
}