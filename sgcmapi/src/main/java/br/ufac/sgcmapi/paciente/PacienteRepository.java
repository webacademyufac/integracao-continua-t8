package br.ufac.sgcmapi.paciente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("""
        SELECT p FROM Paciente p
        WHERE :termoBusca IS NULL
        OR p.nome LIKE %:termoBusca%
        OR p.email LIKE %:termoBusca%
        OR p.telefone LIKE %:termoBusca%
    """)
    List<Paciente> consultar(String termoBusca);
    
}