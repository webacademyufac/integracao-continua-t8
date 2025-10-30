package br.ufac.sgcmapi.usuario;

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

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @MockitoBean
    private UsuarioService servico;

    @Autowired
    private MockMvc mockMvc;

    private Usuario usuario;
    private List<Usuario> usuarios;
    private String conteudoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        usuario = new Usuario();
        usuario.setId(1L);

        conteudoJson = new ObjectMapper().writeValueAsString(usuario);

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
    }

    @Test
    void testUsuarioConsultarTodos() throws Exception {
        Mockito.when(servico.consultar(nullable(String.class))).thenReturn(usuarios);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/usuario/consultar"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testUsuarioConsultarPorId() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(usuario);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/usuario/consultar/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void testUsuarioConsultarPorIdNaoEncontrado() throws Exception {
        Mockito.when(servico.consultar(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/usuario/consultar/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUsuarioConsultarPorTermoBusca() throws Exception {
        Mockito.when(servico.consultar(anyString())).thenReturn(usuarios);
        mockMvc.perform(MockMvcRequestBuilders.get("/config/usuario/consultar")
            .param("termoBusca", "termo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void testUsuarioInserir() throws Exception {
        Mockito.when(servico.salvar(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(MockMvcRequestBuilders.post("/config/usuario/inserir")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void testUsuarioAtualizar() throws Exception {
        Mockito.when(servico.salvar(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(MockMvcRequestBuilders.put("/config/usuario/atualizar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(conteudoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUsuarioRemover() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/config/usuario/remover/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(servico, times(1)).remover(1L);
    }
}
