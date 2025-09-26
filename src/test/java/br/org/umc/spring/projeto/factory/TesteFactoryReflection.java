package br.org.umc.spring.projeto.factory;

import br.org.umc.spring.projeto.clinica.Agendamento;
import br.org.umc.spring.projeto.clinica.AgendamentoServiceReflection;
import br.org.umc.spring.projeto.enums.Especialidade;
import br.org.umc.spring.projeto.model.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TesteFactoryReflection {

    @Autowired
    private AgendamentoServiceReflection service;

    @Test
    void testarFactory() {
        // Arrange - Preparação dos dados de teste
        Paciente ana = new Paciente(9L, "Ana", true);
        Paciente joao = new Paciente(46L, "João", false);

        LocalDateTime dataFutura1 = LocalDateTime.now().plusDays(1);
        LocalDateTime dataFutura2 = LocalDateTime.now().plusDays(2);
        LocalDateTime dataFutura3 = LocalDateTime.now().plusDays(3);

        // Act - Execução das operações
        Agendamento a1 = service.agendar(ana, Especialidade.PEDIATRIA, dataFutura1);
        Agendamento a2 = service.agendar(joao, Especialidade.CARDIOLOGIA, dataFutura2);
        Agendamento a3 = service.agendar(joao, Especialidade.ORTOPEDIA, dataFutura3);

        // Assert - Validações com assertions adequadas

        // Validações de não-nulidade (Segurança)
        assertNotNull(a1, "Agendamento de Pediatria não deve ser nulo");
        assertNotNull(a2, "Agendamento de Cardiologia não deve ser nulo");
        assertNotNull(a3, "Agendamento de Ortopedia não deve ser nulo");

        // Validações de pacientes
        assertEquals(ana, a1.getPaciente(), "Paciente do agendamento 1 deve ser Ana");
        assertEquals(joao, a2.getPaciente(), "Paciente do agendamento 2 deve ser João");
        assertEquals(joao, a3.getPaciente(), "Paciente do agendamento 3 deve ser João");

        // Validações do Factory Pattern - Especialidades corretas
        assertEquals(Especialidade.PEDIATRIA, a1.getConsulta().getEspecialidade());
        assertEquals(Especialidade.CARDIOLOGIA, a2.getConsulta().getEspecialidade());
        assertEquals(Especialidade.ORTOPEDIA, a3.getConsulta().getEspecialidade());

        // Validações de datas
        assertEquals(dataFutura1, a1.getDataHora());
        assertEquals(dataFutura2, a2.getDataHora());
        assertEquals(dataFutura3, a3.getDataHora());

        // Validações de qualidade - Preços e durações
        assertNotNull(a1.getPreco(), "Preço não deve ser nulo");
        assertNotNull(a2.getPreco(), "Preço não deve ser nulo");
        assertNotNull(a3.getPreco(), "Preço não deve ser nulo");

        assertTrue(a1.getConsulta().getDuracaoMinutos() > 0, "Duração deve ser positiva");
        assertTrue(a2.getConsulta().getDuracaoMinutos() > 0, "Duração deve ser positiva");
        assertTrue(a3.getConsulta().getDuracaoMinutos() > 0, "Duração deve ser positiva");

        // Output para verificação visual
        System.out.println("=== Agendamentos (Reflection Factory) ===");
        System.out.println(a1.resumo());
        System.out.println(a2.resumo());
        System.out.println(a3.resumo());

        System.out.println("✅ Todos os testes do Factory Pattern com Reflection passaram!");
    }

    @Test
    void testarValidacaoDataPassado() {
        // Teste de segurança - não deve permitir agendamento no passado
        Paciente paciente = new Paciente(1L, "Teste", false);
        AgendamentoServiceReflection service = new AgendamentoServiceReflection();

        assertThrows(IllegalArgumentException.class, () -> {
            service.agendar(paciente, Especialidade.PEDIATRIA, LocalDateTime.now().minusDays(1));
        }, "Deve lançar exceção ao tentar agendar no passado");
    }

    @Test
    void testarReflectionParaTodasEspecialidades() {
        // Teste específico do Reflection Factory
        Paciente paciente = new Paciente(1L, "Teste", false);
        LocalDateTime dataFutura = LocalDateTime.now().plusDays(1);

        // Testa se o Reflection consegue criar todas as especialidades
        for (Especialidade esp : Especialidade.values()) {
            assertDoesNotThrow(() -> {
                Agendamento agendamento = service.agendar(paciente, esp, dataFutura);
                assertNotNull(agendamento, "Agendamento para " + esp + " não deve ser nulo");
                assertEquals(esp, agendamento.getConsulta().getEspecialidade());
            }, "Reflection deve funcionar para " + esp);
        }
    }
}