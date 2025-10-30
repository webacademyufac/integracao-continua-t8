package br.ufac.sgcmapi.unidade;

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
public class UnidadeServiceTest {
    @Mock
    private UnidadeRepository repo;

    @InjectMocks
    private UnidadeService service;

    private Unidade unidade;
    private List<Unidade> unidades;

    @BeforeEach
    void setUp() {
        unidade = new Unidade();
        unidade.setId(1L);
        
        Unidade unidade1 = new Unidade();
        unidade1.setId(1L);

        Unidade unidade2 = new Unidade();
        unidade2.setId(2L);

        unidades = new ArrayList<>();
        unidades.add(unidade1);
        unidades.add(unidade2);
    }

    @Test
    void testUnidadeConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(unidades);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testUnidadeConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(unidade));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testUnidadeConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testUnidadeConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(unidades);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testUnidadeSave() {
        Mockito.when(repo.save(any(Unidade.class)))
            .thenReturn(unidade);
        var registro = service.salvar(unidade);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testUnidadeRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }
}
