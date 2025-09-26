package br.org.umc.spring.projeto.clinica;

import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.interfaces.Consulta;
import br.org.umc.spring.projeto.model.Paciente;

import java.math.BigDecimal;

public class ConsultaCardiologia implements Consulta {
    @Override
    public Especialidade getEspecialidade() {
        return Especialidade.CARDIOLOGIA;
    }

    @Override
    public int getDuracaoMinutos() {
        return 45;
    }

    @Override
    public BigDecimal calcularPreco(Paciente paciente) {
        BigDecimal base = new BigDecimal("350.00");
        // taxa adicional de R$ 50,00 na primeira consulta
        return paciente.isPrimeiraConsulta() ? base.add(new BigDecimal("50.00")) : base;
    }
}
