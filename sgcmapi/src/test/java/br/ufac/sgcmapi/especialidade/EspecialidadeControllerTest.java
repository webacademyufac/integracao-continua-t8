package br.ufac.sgcmapi.especialidade;

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

@WebMvcTest(EspecialidadeController.class)
public class EspecialidadeControllerTest {
    @MockitoBean
    private EspecialidadeService servico;

    @Autowired
    private MockMvc mockMvc;

    private Especialidade especialidade;
    private List<Especialidade> especialidades;
    private String conteudoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        especialidade = new Especialidade();
        especialidade.setId(1L);

        conteudoJson = new ObjectMapper().writeValueAsString(especialidade);

        Especialidade especialidade1 = new Especialidade();
        especialidade1.setId(1L);

        Especialidade especialidade2 = new Especialidade();
        especialidade2.setId(2L);

        especialidades = new ArrayList<>();
        especialidades.add(especialidade1);
        especialidades.add(especialidade2);
    }

    @Test
    void testEspecialidadeConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class))).thenReturn(especialidades);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/especialidade/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));

    }

    @Test
    void testEspecialidadeConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(especialidade);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/especialidade/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testEspecialidadeConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/especialidade/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());        
    }

    @Test
    void testEspecialidadeConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString())).thenReturn(especialidades);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/especialidade/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testEspecialidadeInserir() throws Exception {
        Mockito.when(servico.salvar(any(Especialidade.class))).thenReturn(especialidade);
        mockMvc.perform(MockMvcRequestBuilders.post("/config/especialidade/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testEspecialidadeAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Especialidade.class))).thenReturn(especialidade);
        mockMvc.perform(MockMvcRequestBuilders.put("/config/especialidade/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testEspecialidadeRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/config/especialidade/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
