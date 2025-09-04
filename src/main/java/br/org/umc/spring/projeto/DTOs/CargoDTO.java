package br.org.umc.spring.projeto.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CargoDTO {

        @NotBlank(message = "Nome do cargo é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        private String nome;

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        private String descricao;

        private Integer nivelHierarquico;

        // Construtores
        public CargoDTO() {}

        public CargoDTO(String nome, String descricao, Integer nivelHierarquico) {
            this.nome = nome;
            this.descricao = descricao;
            this.nivelHierarquico = nivelHierarquico;
        }

        // Getters e Setters
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }

        public Integer getNivelHierarquico() { return nivelHierarquico; }
        public void setNivelHierarquico(Integer nivelHierarquico) {
            this.nivelHierarquico = nivelHierarquico;
        }
    }
