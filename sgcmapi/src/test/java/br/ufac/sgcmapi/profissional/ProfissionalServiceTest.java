package br.ufac.sgcmapi.profissional;

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
public class ProfissionalServiceTest {
    @Mock
    private ProfissionalRepository repo;

    @InjectMocks
    private ProfissionalService service;

    private Profissional profissional;
    private List<Profissional> profissionais;

    @BeforeEach
    void setUp() {
        profissional = new Profissional();
        profissional.setId(1L);
        
        Profissional profissional1 = new Profissional();
        profissional1.setId(1L);

        Profissional profissional2 = new Profissional();
        profissional2.setId(2L);

        profissionais = new ArrayList<>();
        profissionais.add(profissional1);
        profissionais.add(profissional2);
    }

    @Test
    void testProfissionalConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(profissionais);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testProfissionalConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(profissional));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testProfissionalConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testProfissionalConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(profissionais);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testProfissionalSave() {
        Mockito.when(repo.save(any(Profissional.class)))
            .thenReturn(profissional);
        var registro = service.salvar(profissional);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testProfissionalRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }
}
