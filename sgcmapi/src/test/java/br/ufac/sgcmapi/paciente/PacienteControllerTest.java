package br.ufac.sgcmapi.paciente;

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

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {
    @MockitoBean
    private PacienteService servico;

    @Autowired
    private MockMvc mockMvc;

    private Paciente paciente;
    private List<Paciente> pacientes;
    private String conteudoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        paciente = new Paciente();
        paciente.setId(1L);

        conteudoJson = new ObjectMapper().writeValueAsString(paciente);

        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);

        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);

        pacientes = new ArrayList<>();
        pacientes.add(paciente1);
        pacientes.add(paciente2);
    }

    @Test
    void testPacienteConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class))).thenReturn(pacientes);
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testPacienteConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(paciente);
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testPacienteConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());        
    }

    @Test
    void testPacienteConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString())).thenReturn(pacientes);
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testPacienteInserir() throws Exception {
        Mockito.when(servico.salvar(any(Paciente.class))).thenReturn(paciente);
        mockMvc.perform(MockMvcRequestBuilders.post("/paciente/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testPacienteAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Paciente.class))).thenReturn(paciente);
        mockMvc.perform(MockMvcRequestBuilders.put("/paciente/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPacienteRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/paciente/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
