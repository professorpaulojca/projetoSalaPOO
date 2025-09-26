package br.org.umc.spring.projeto.enums;

import lombok.Getter;

@Getter
public enum Especialidade {
    PEDIATRIA("br.org.umc.spring.projeto.clinica.ConsultaPediatria"),
    CARDIOLOGIA("br.org.umc.spring.projeto.clinica.ConsultaCardiologia"),
    ORTOPEDIA("br.org.umc.spring.projeto.clinica.ConsultaOrtopedia");

    private final String className;

    Especialidade(String className) {
        this.className = className;
    }

}
