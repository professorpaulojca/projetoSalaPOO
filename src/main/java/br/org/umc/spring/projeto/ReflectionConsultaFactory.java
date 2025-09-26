package br.org.umc.spring.projeto;

import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.interfaces.Consulta;

public class ReflectionConsultaFactory {

    public static Consulta criar(Especialidade esp) {
        try {
            Class<?> clazz = Class.forName(esp.getClassName());
            return (Consulta) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar consulta via reflection para " + esp, e);
        }
    }
}
