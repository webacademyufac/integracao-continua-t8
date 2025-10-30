package br.ufac.sgcmapi.usuario;

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
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private List<Usuario> usuarios;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
    }

    @Test
    void testUsuarioConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(usuarios);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testUsuarioConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(usuario));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testUsuarioConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testUsuarioConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(usuarios);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testUsuarioSave() {
        Mockito.when(repo.save(any(Usuario.class)))
            .thenReturn(usuario);
        var registro = service.salvar(usuario);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testUsuarioRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }
}
