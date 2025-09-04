package br.org.umc.spring.projeto.enums;

import lombok.Getter;

@Getter
public enum TipoSanguineo {
    A_POSITIVO("A+", "A Positivo"),
    A_NEGATIVO("A-", "A Negativo"),
    B_POSITIVO("B+", "B Positivo"),
    B_NEGATIVO("B-", "B Negativo"),
    AB_POSITIVO("AB+", "AB Positivo"),
    AB_NEGATIVO("AB-", "AB Negativo"),
    O_POSITIVO("O+", "O Positivo"),
    O_NEGATIVO("O-", "O Negativo"),
    NAO_INFORMADO("NI", "Não Informado");

    private final String codigo;
    private final String descricao;

    // Construtor manual para enum
    TipoSanguineo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}