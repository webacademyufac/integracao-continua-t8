package br.ufac.sgcmapi.atendimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EStatusTest {
    @Test
    void testEstatusProximo() {
        for (var status : EStatus.values()) {
            var proximo = switch (status) {
                case CANCELADO -> EStatus.CANCELADO;
                case AGENDADO -> EStatus.CONFIRMADO;
                case CONFIRMADO -> EStatus.CHEGADA;
                case CHEGADA -> EStatus.ATENDIMENTO;
                case ATENDIMENTO -> EStatus.ENCERRADO;
                case ENCERRADO -> EStatus.ENCERRADO;
            };
            assertEquals(proximo, status.proximo());
        }
    }
}
