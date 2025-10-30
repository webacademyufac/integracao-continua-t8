package br.ufac.sgcmapi.convenio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ConvenioController.class)
public class ConvenioControllerTest {
    @MockitoBean
    private ConvenioService servico;

    @Autowired
    private MockMvc mockMvc;

    private Convenio convenio;
    private String conteudoJson;
    private List<Convenio> convenios;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNome("Convenio Teste");
        convenio.setAtivo(true);

        conteudoJson = new ObjectMapper().writeValueAsString(convenio);

        Convenio convenio1 = new Convenio();
        convenio1.setId(1L);
        convenio1.setNome("Convenio A");
        convenio1.setAtivo(true);

        Convenio convenio2 = new Convenio();
        convenio2.setId(2L);
        convenio2.setNome("Convenio B");
        convenio2.setAtivo(false);

        convenios = new ArrayList<>();
        convenios.add(convenio1);
        convenios.add(convenio2);
    }

    @Test
    void testConvenioConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class)))
            .thenReturn(convenios);

        mockMvc.perform(MockMvcRequestBuilders.get("/convenio/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testConvenioConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong()))
            .thenReturn(convenio);

        mockMvc.perform(MockMvcRequestBuilders.get("/convenio/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testConvenioConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong()))
            .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/convenio/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testConvenioConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString()))
            .thenReturn(convenios);

        mockMvc.perform(MockMvcRequestBuilders.get("/convenio/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testConvenioConsultarAtivos() throws Exception {
        List<Convenio> conveniosAtivos = List.of(convenios.get(0));
        Mockito.when(servico.consultarAtivos())
            .thenReturn(conveniosAtivos);

        mockMvc.perform(MockMvcRequestBuilders.get("/convenio/ativos"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)));
    }

    @Test
    void testConvenioInserir() throws Exception {
        Mockito.when(servico.salvar(any(Convenio.class)))
            .thenReturn(convenio);
        mockMvc.perform(MockMvcRequestBuilders.post("/convenio/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testConvenioAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Convenio.class)))
            .thenReturn(convenio);

        mockMvc.perform(MockMvcRequestBuilders.put("/convenio/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testConvenioRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/convenio/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
