package br.ufac.sgcmapi.especialidade;

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
public class EspecialidadeServiceTest {
    @Mock
    private EspecialidadeRepository repo;

    @InjectMocks
    private EspecialidadeService service;

    private Especialidade especialidade;
    private List<Especialidade> especialidades;

    @BeforeEach
    void setUp() {
        especialidade = new Especialidade();
        especialidade.setId(1L);
        
        Especialidade especialidade1 = new Especialidade();
        especialidade1.setId(1L);

        Especialidade especialidade2 = new Especialidade();
        especialidade2.setId(2L);

        especialidades = new ArrayList<>();
        especialidades.add(especialidade1);
        especialidades.add(especialidade2);
    }

    @Test
    void testEspecialidadeConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(especialidades);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testEspecialidadeConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(especialidade));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testEspecialidadeConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testEspecialidadeConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(especialidades);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testEspecialidadeSave() {
        Mockito.when(repo.save(any(Especialidade.class)))
            .thenReturn(especialidade);
        var registro = service.salvar(especialidade);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testEspecialidadeRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }
}
