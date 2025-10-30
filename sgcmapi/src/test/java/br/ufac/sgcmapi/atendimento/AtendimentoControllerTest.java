package br.ufac.sgcmapi.atendimento;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;

import java.time.LocalTime;
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

import br.ufac.sgcmapi.paciente.Paciente;
import br.ufac.sgcmapi.profissional.Profissional;

@WebMvcTest(AtendimentoController.class)
public class AtendimentoControllerTest {
    @MockitoBean
    private AtendimentoService servico;

    @Autowired
    private MockMvc mockMvc;

    private Atendimento atendimento;
    private String conteudoJson;
    private List<Atendimento> atendimentos;
    private Profissional profissional;
    private Paciente paciente;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        atendimento = new Atendimento();
        atendimento.setId(1L);
        atendimento.setStatus(EStatus.CONFIRMADO);

        conteudoJson = new ObjectMapper().writeValueAsString(atendimento);

        profissional = new Profissional();
        profissional.setId(1L);

        paciente = new Paciente();
        paciente.setId(1L);

        Atendimento atendimento1 = new Atendimento();
        atendimento1.setId(1L);
        atendimento1.setProfissional(profissional);
        atendimento1.setPaciente(paciente);
        atendimento1.setHora(LocalTime.of(15, 0));

        Atendimento atendimento2 = new Atendimento();
        atendimento2.setId(2L);
        atendimento2.setProfissional(profissional);
        atendimento2.setPaciente(paciente);
        atendimento2.setHora(LocalTime.of(16, 0));

        atendimentos = new ArrayList<>();
        atendimentos.add(atendimento1);
        atendimentos.add(atendimento2);
    }

    @Test
    void testAtendimentoConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(isNull(), isNull()))
            .thenReturn(atendimentos);
        mockMvc.perform(MockMvcRequestBuilders.get("/atendimento/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testAtendimentoConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong()))
            .thenReturn(atendimento);
        mockMvc.perform(MockMvcRequestBuilders.get("/atendimento/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testAtendimentoConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong()))
            .thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/atendimento/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAtendimentoConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString(), isNull()))
            .thenReturn(atendimentos);
        mockMvc.perform(MockMvcRequestBuilders.get("/atendimento/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testAtendimentoConsultarPorTermoBuscaStatus() throws Exception {
        Mockito.when(servico.consultar(anyString(), anyList()))
            .thenReturn(atendimentos);
        mockMvc.perform(MockMvcRequestBuilders.get("/atendimento/consultar")
            .param("termoBusca", "termo")
            .param("status", "AGENDADO,CONFIRMADO"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testAtendimentoInserir() throws Exception {
        Mockito.when(servico.salvar(any(Atendimento.class)))
            .thenReturn(atendimento);
        mockMvc.perform(MockMvcRequestBuilders.post("/atendimento/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testAtendimentoAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Atendimento.class)))
            .thenReturn(atendimento);
        mockMvc.perform(MockMvcRequestBuilders.put("/atendimento/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAtendimentoRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/atendimento/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }

    @Test
    void testAtendimentoAtualizarStatus() throws Exception {
        Mockito.when(servico.atualizarStatus(anyLong()))
            .thenReturn(atendimento);
        mockMvc.perform(MockMvcRequestBuilders.put("/atendimento/status/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("CONFIRMADO")));
    }
}
