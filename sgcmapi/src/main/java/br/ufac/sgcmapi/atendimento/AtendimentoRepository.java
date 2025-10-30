package br.ufac.sgcmapi.atendimento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    @Query("""
        SELECT a FROM Atendimento a
        WHERE (:termoBusca IS NULL
        OR a.profissional.nome LIKE %:termoBusca%
        OR a.paciente.nome LIKE %:termoBusca%
        OR a.convenio.nome LIKE %:termoBusca%
        OR a.profissional.unidade.nome LIKE %:termoBusca%)
        AND (:status IS NULL OR a.status IN :status)
    """)
    List<Atendimento> consultar(String termoBusca, List<EStatus> status);
}
