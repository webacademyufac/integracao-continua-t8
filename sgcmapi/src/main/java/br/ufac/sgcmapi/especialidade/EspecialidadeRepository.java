package br.ufac.sgcmapi.especialidade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    @Query("""
        SELECT e FROM Especialidade e
        WHERE :termoBusca IS NULL
        OR e.nome LIKE %:termoBusca%
    """)
    List<Especialidade> consultar(String termoBusca);
    
}