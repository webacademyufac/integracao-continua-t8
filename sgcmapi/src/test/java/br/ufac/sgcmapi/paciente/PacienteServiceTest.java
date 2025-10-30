package br.ufac.sgcmapi.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

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

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {
    @Mock
    private PacienteRepository repo;

    @InjectMocks
    private PacienteService service;

    private Paciente paciente;
    private List<Paciente> pacientes;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);

        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);

        pacientes = new ArrayList<>();
        pacientes.add(paciente1);
        pacientes.add(paciente2);
    }

    @Test
    void testPacienteConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(pacientes);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testPacienteConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(paciente));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testPacienteConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testPacienteConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(pacientes);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testPacienteSave() {
        Mockito.when(repo.save(any(Paciente.class)))
            .thenReturn(paciente);
        var registro = service.salvar(paciente);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testPacienteRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }
}

