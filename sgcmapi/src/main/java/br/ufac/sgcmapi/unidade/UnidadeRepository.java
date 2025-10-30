package br.ufac.sgcmapi.unidade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query("""
        SELECT u FROM Unidade u
        WHERE :termoBusca IS NULL
        OR u.nome LIKE %:termoBusca%
        OR u.endereco LIKE %:termoBusca%
    """)
    List<Unidade> consultar(String termoBusca);
    
}