package br.org.umc.spring.projeto.clinica;

import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.interfaces.Consulta;
import br.org.umc.spring.projeto.model.Paciente;

import java.math.BigDecimal;

public class ConsultaPediatria implements Consulta {
    @Override
    public Especialidade getEspecialidade() {
        return Especialidade.PEDIATRIA;
    }

    @Override
    public int getDuracaoMinutos() {
        return 30;
    }

    @Override
    public BigDecimal calcularPreco(Paciente paciente) {
        BigDecimal base = new BigDecimal("180.00");
        if (paciente.isPrimeiraConsulta() && paciente.getIdade() < 12) {
            return base.multiply(new BigDecimal("0.9")); // 10% de desconto
        }
        return base;
    }
}
