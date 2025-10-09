package br.org.umc.spring.projeto.mongo;

import br.org.umc.spring.projeto.clinica.Agendamento;
import br.org.umc.spring.projeto.clinica.ConsultaCardiologia;
import br.org.umc.spring.projeto.clinica.ConsultaOrtopedia;
import br.org.umc.spring.projeto.model.Paciente;
import br.org.umc.spring.projeto.repository.AgendamentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    private Paciente paciente1;
    private Paciente paciente2;
    private ConsultaCardiologia consulta1;
    private ConsultaOrtopedia consulta2;
    private Agendamento agendamento1;
    private Agendamento agendamento2;
    private Agendamento agendamento3;

    @BeforeEach
    void setUp() {
        // Setup test data
        paciente1 = new Paciente(1L, "João Silva", false);
        paciente2 = new Paciente(2L, "Maria Santos", true);

        // Criando instâncias concretas de Consulta
        consulta1 = new ConsultaCardiologia();
        consulta2 = new ConsultaOrtopedia();

        LocalDateTime now = LocalDateTime.now();
        agendamento1 = new Agendamento(paciente1, consulta1, now.plusDays(1));
        agendamento2 = new Agendamento(paciente1, consulta2, now.plusDays(2));
        agendamento3 = new Agendamento(paciente2, consulta1, now.plusDays(3));

        // Save test data
        agendamentoRepository.save(agendamento1);
        agendamentoRepository.save(agendamento2);
        agendamentoRepository.save(agendamento3);
    }

    @AfterEach
    void tearDown() {
        // Limpa todos os dados após cada teste
        agendamentoRepository.deleteAll();
    }

    @Test
    void testFindByPacienteId() {
        // When
        List<Agendamento> agendamentos = agendamentoRepository.findByPacienteId(paciente1.getId().toString());

        // Then
        assertEquals(2, agendamentos.size());
        assertTrue(agendamentos.stream().allMatch(a -> a.getPaciente().getId().equals(paciente1.getId())));
    }

    @Test
    void testFindByDataHoraBetween() {
        // Given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        // When
        List<Agendamento> agendamentos = agendamentoRepository.findByDataHoraBetween(start, end);

        // Then
        assertEquals(2, agendamentos.size());
        assertTrue(agendamentos.stream()
                .allMatch(a -> !a.getDataHora().isBefore(start) && !a.getDataHora().isAfter(end)));
    }

    @Test
    void testFindByPacienteIdAndDataHoraBetween() {
        // Given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        String pacienteId = "1";

        // When
        List<Agendamento> agendamentos = agendamentoRepository
                .findByPacienteIdAndDataHoraBetween(pacienteId, start, end);

        // Then
        assertEquals(2, agendamentos.size());
        assertTrue(agendamentos.stream()
                .allMatch(a -> a.getPaciente().getId().equals(1L) &&
                        !a.getDataHora().isBefore(start) &&
                        !a.getDataHora().isAfter(end)));
    }

    @Test
    void testSaveAgendamento() {
        // Given
        Agendamento novoAgendamento = new Agendamento(
                paciente2,
                consulta2,
                LocalDateTime.now().plusDays(5)
        );

        // When
        Agendamento saved = agendamentoRepository.save(novoAgendamento);
        Agendamento found = agendamentoRepository.findById(saved.getId()).orElse(null);

        // Then
        assertNotNull(found);
        assertEquals(novoAgendamento.getPaciente().getId(), found.getPaciente().getId());
        assertEquals(novoAgendamento.getConsulta().getEspecialidade(), found.getConsulta().getEspecialidade());
        assertEquals(novoAgendamento.getDataHora(), found.getDataHora());
    }
}
