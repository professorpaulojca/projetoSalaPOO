package br.org.umc.spring.projeto.clinica;

import br.org.umc.spring.projeto.ReflectionConsultaFactory;
import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.interfaces.Consulta;
import br.org.umc.spring.projeto.model.Paciente;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendamentoServiceReflection {
    public Agendamento agendar(Paciente paciente, Especialidade especialidade, LocalDateTime quando) {
        if (quando.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar no passado");
        }
        Consulta consulta = ReflectionConsultaFactory.criar(especialidade);
        return new Agendamento(paciente, consulta, quando);
    }
}
