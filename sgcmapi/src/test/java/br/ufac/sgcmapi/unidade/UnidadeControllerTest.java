package br.ufac.sgcmapi.unidade;

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

@WebMvcTest(UnidadeController.class)
public class UnidadeControllerTest {
    @MockitoBean
    private UnidadeService servico;

    @Autowired
    private MockMvc mockMvc;

    private Unidade unidade;
    private List<Unidade> unidades;
    private String conteudoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        unidade = new Unidade();
        unidade.setId(1L);

        conteudoJson = new ObjectMapper().writeValueAsString(unidade);

        Unidade unidade1 = new Unidade();
        unidade1.setId(1L);

        Unidade unidade2 = new Unidade();
        unidade2.setId(2L);

        unidades = new ArrayList<>();
        unidades.add(unidade1);
        unidades.add(unidade2);
    }

    @Test
    void testUnidadeConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class))).thenReturn(unidades);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/unidade/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testUnidadeConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(unidade);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/unidade/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testUnidadeConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/unidade/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUnidadeConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString())).thenReturn(unidades);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/unidade/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testUnidadeInserir() throws Exception {
        Mockito.when(servico.salvar(any(Unidade.class))).thenReturn(unidade);
        mockMvc.perform(MockMvcRequestBuilders.post("/config/unidade/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testUnidadeAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Unidade.class))).thenReturn(unidade);
        mockMvc.perform(MockMvcRequestBuilders.put("/config/unidade/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUnidadeRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/config/unidade/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
