package br.org.umc.spring.projeto.repository;

import br.org.umc.spring.projeto.clinica.Agendamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends MongoRepository<Agendamento, String> {
    
    // Encontrar agendamentos por paciente
    List<Agendamento> findByPacienteId(String pacienteId);
    
    // Encontrar agendamentos por data
    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // Encontrar agendamentos por paciente e data
    List<Agendamento> findByPacienteIdAndDataHoraBetween(String pacienteId, LocalDateTime inicio, LocalDateTime fim);
    
    // Verificar se já existe agendamento no mesmo horário
    boolean existsByDataHora(LocalDateTime dataHora);
}
