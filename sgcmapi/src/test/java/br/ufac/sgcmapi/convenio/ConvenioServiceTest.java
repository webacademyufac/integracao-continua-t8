package br.ufac.sgcmapi.convenio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class ConvenioServiceTest {
    @Mock
    private ConvenioRepository repo;

    @InjectMocks
    private ConvenioService servico;

    private Convenio convenio;
    private List<Convenio> convenios;

    @BeforeEach
    void setUp() {
        convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNome("Convenio Teste");
        convenio.setAtivo(true);

        var convenio1 = new Convenio();
        convenio1.setId(1L);
        convenio1.setNome("Convenio A");
        convenio1.setAtivo(true);
        
        var convenio2 = new Convenio();
        convenio2.setId(2L);
        convenio2.setNome("Convenio B");
        convenio2.setAtivo(false);

        convenios = new ArrayList<>();
        convenios.add(convenio1);
        convenios.add(convenio2);
    }

    @Test
    void testConvenioConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(convenios);
        var resultado = servico.consultar("");
        assertEquals(2, resultado.size());
    }

    @Test
    void testConvenioConsultarPorId() {
        Mockito.when(repo.findById(1L))
            .thenReturn(Optional.of(convenio));
        var resultado = servico.consultar(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testConvenioConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(999L))
            .thenReturn(Optional.empty());
        var resultado = servico.consultar(999L);
        assertEquals(null, resultado);
    }

    @Test
    void testConvenioSalvar() {
        Mockito.when(repo.save(convenio))
            .thenReturn(convenio);
        var resultado = servico.salvar(convenio);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testConvenioRemover() {
        servico.remover(1L);
        Mockito.verify(repo).deleteById(1L);
    }

    @Test
    void testConvenioConsultarAtivos() {
        List<Convenio> conveniosAtivos = List.of(convenios.get(0));
        Mockito.when(repo.findByAtivo(true))
            .thenReturn(conveniosAtivos);
        var resultado = servico.consultarAtivos();
        assertEquals(1, resultado.size());
        assertEquals(true, resultado.get(0).isAtivo());
    }

    @Test
    void testConvenioConsultarComTermoNulo() {
        Mockito.when(repo.consultar(null))
            .thenReturn(convenios);
        var resultado = servico.consultar((String) null);
        assertEquals(2, resultado.size());
    }

    @Test
    void testConvenioConsultarComTermoComEspacos() {
        Mockito.when(repo.consultar("teste"))
            .thenReturn(convenios);
        var resultado = servico.consultar("  teste  ");
        assertEquals(2, resultado.size());
    }
}
