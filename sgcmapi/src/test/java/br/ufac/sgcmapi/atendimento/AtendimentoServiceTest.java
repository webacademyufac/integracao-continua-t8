package br.ufac.sgcmapi.atendimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.ufac.sgcmapi.paciente.Paciente;
import br.ufac.sgcmapi.profissional.Profissional;

@ExtendWith(MockitoExtension.class)
public class AtendimentoServiceTest {
    @Mock
    private AtendimentoRepository repo;

    @InjectMocks
    private AtendimentoService servico;

    private Atendimento atendimento;
    private List<Atendimento> atendimentos;
    private Profissional profissional;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        atendimento = new Atendimento();
        atendimento.setId(1L);

        profissional = new Profissional();
        profissional.setId(1L);

        paciente = new Paciente();
        paciente.setId(1L);

        Atendimento atendimento1 = new Atendimento();
        atendimento1.setId(1L);
        atendimento1.setProfissional(profissional);
        atendimento1.setPaciente(paciente);
        atendimento1.setHora(LocalTime.of(15, 0));

        Atendimento atendimento2 = new Atendimento();
        atendimento2.setId(2L);
        atendimento2.setProfissional(profissional);
        atendimento2.setPaciente(paciente);
        atendimento2.setHora(LocalTime.of(16, 0));

        atendimentos = new ArrayList<>();
        atendimentos.add(atendimento1);
        atendimentos.add(atendimento2);
    }

    @Test
    void testAtendimentoConsultarTodos() {
        Mockito.when(repo.consultar("", null))
            .thenReturn(atendimentos);
        var resultado = servico.consultar("", null);
        assertEquals(2, resultado.size());
    }

    @Test
    void testAtendimentoConsultarPorId() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.of(atendimento));
        var registros = servico.consultar(1L);
        assertNotNull(registros);
        assertEquals(1L, registros.getId());
    }

    @Test
    void testAtendimentoConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = servico.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testAtendimentoConsultarPorTermoBusca() {
        Mockito.when(repo.consultar("termo", null)).thenReturn(atendimentos);
        var registros = servico.consultar("termo", null);
        assertEquals(2, registros.size());
    }

    @Test
    void testAtendimentoSave() {
        Mockito.when(repo.save(any(Atendimento.class))).thenReturn(atendimento);
        var registro = servico.salvar(atendimento);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testAtendimentoRemover() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.of(atendimento));
        Mockito.when(repo.save(any(Atendimento.class))).thenReturn(atendimento);
        servico.remover(1L);
        assertEquals(EStatus.CANCELADO, atendimento.getStatus());
    }

    @Test
    void testAtendimentoAtualizarStatus() {
        Mockito.when(repo.findById(1L))
            .thenReturn(Optional.of(atendimento));
        Mockito.when(repo.save(atendimento))
            .thenReturn(atendimento);
        var resultado = servico.atualizarStatus(1L);
        assertNotNull(resultado);
        assertEquals(EStatus.CONFIRMADO, resultado.getStatus());
    }
}
