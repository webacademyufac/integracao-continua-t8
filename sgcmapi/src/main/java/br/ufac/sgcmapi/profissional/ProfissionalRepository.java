package br.ufac.sgcmapi.profissional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    @Query("""
        SELECT p FROM Profissional p
        WHERE :termoBusca IS NULL
        OR p.nome LIKE %:termoBusca%
        OR p.registroConselho LIKE %:termoBusca%
        OR p.especialidade.nome LIKE %:termoBusca%
        OR p.unidade.nome LIKE %:termoBusca%
    """)
    List<Profissional> consultar(String termoBusca);
    
}