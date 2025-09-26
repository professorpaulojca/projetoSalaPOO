package br.org.umc.spring.projeto.clinica;

import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.interfaces.Consulta;
import br.org.umc.spring.projeto.model.Paciente;

import java.math.BigDecimal;

public class ConsultaOrtopedia implements Consulta {
    @Override
    public Especialidade getEspecialidade() {
        return Especialidade.ORTOPEDIA;
    }

    @Override
    public int getDuracaoMinutos() {
        return 40;
    }

    @Override
    public BigDecimal calcularPreco(Paciente paciente) {
        // preço fixo por enquanto
        return new BigDecimal("220.00");
    }
}
