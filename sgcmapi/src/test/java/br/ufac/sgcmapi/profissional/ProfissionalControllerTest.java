package br.ufac.sgcmapi.profissional;

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

@WebMvcTest(ProfissionalController.class)
public class ProfissionalControllerTest {
    @MockitoBean
    private ProfissionalService servico;

    @Autowired
    private MockMvc mockMvc;

    private Profissional profissional;
    private List<Profissional> profissionais;
    private String conteudoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        profissional = new Profissional();
        profissional.setId(1L);

        conteudoJson = new ObjectMapper().writeValueAsString(profissional);

        Profissional profissional1 = new Profissional();
        profissional1.setId(1L);

        Profissional profissional2 = new Profissional();
        profissional2.setId(2L);

        profissionais = new ArrayList<>();
        profissionais.add(profissional1);
        profissionais.add(profissional2);
    }

    @Test
    void testProfissionalConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class))).thenReturn(profissionais);
        mockMvc.perform(MockMvcRequestBuilders.get("/profissional/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testProfissionalConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(profissional);
        mockMvc.perform(MockMvcRequestBuilders.get("/profissional/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testProfissionalConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/profissional/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testProfissionalConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString())).thenReturn(profissionais);
        mockMvc.perform(MockMvcRequestBuilders.get("/profissional/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testProfissionalInserir() throws Exception {
        Mockito.when(servico.salvar(any(Profissional.class))).thenReturn(profissional);
        mockMvc.perform(MockMvcRequestBuilders.post("/profissional/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testProfissionalAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Profissional.class))).thenReturn(profissional);
        mockMvc.perform(MockMvcRequestBuilders.put("/profissional/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testProfissionalRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/profissional/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
