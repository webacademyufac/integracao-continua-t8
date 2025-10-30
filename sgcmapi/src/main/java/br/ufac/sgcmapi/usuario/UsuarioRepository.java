package br.ufac.sgcmapi.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
        SELECT u FROM Usuario u
        WHERE :termoBusca IS NULL
        OR u.nomeCompleto LIKE %:termoBusca%
        OR u.nomeUsuario LIKE %:termoBusca%
    """)
    List<Usuario> consultar(String termoBusca);
    
}