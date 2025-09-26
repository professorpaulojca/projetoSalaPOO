package br.org.umc.spring.projeto.clinica;

import br.org.umc.spring.projeto.interfaces.Consulta;
import br.org.umc.spring.projeto.model.Paciente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Agendamento {
    private final Paciente paciente;
    private final Consulta consulta;
    private final LocalDateTime dataHora;

    public Agendamento(Paciente paciente, Consulta consulta, LocalDateTime dataHora) {
        this.paciente = paciente;
        this.consulta = consulta;
        this.dataHora = dataHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public BigDecimal getPreco() {
        return consulta.calcularPreco(paciente);
    }

    public String resumo() {
        return String.format("Consulta %s (%d min) em %s para %s. Preço: R$ %s",
                consulta.getEspecialidade(), consulta.getDuracaoMinutos(), dataHora, paciente, getPreco());
    }
}