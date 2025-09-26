package br.org.umc.spring.projeto.interfaces;

import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.model.Paciente;

import java.math.BigDecimal;

public interface Consulta {
    // Qual especialidade essa consulta representa
    Especialidade getEspecialidade();

    // Quanto tempo dura a consulta (em minutos, por exemplo)
    default int getDuracaoMinutos() {
        return 30; // valor padrão (as classes concretas podem sobrescrever)
    }

    // Calcula o preço da consulta para um paciente
    BigDecimal calcularPreco(Paciente paciente);
}
