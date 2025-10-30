package br.ufac.sgcmapi.convenio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    List<Convenio> findByAtivo(boolean ativo);
    
    @Query("""
        SELECT c FROM Convenio c
        WHERE :termoBusca IS NULL
        OR c.nome LIKE %:termoBusca%
        OR c.razaoSocial LIKE %:termoBusca%
        OR c.cnpj LIKE %:termoBusca%
        OR c.representante LIKE %:termoBusca%
    """)
    List<Convenio> consultar(String termoBusca);
    
}
