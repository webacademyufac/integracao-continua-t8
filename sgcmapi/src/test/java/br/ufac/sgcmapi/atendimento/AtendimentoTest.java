package br.ufac.sgcmapi.atendimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AtendimentoTest {
    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        atendimento = new Atendimento();
    }

    @Test
    void testAtendimentoId() {
        var id = 1L;
        atendimento.setId(id);
        assertEquals(1L, atendimento.getId());
    }
}
