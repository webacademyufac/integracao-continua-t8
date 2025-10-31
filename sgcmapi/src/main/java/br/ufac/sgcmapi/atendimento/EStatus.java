package br.ufac.sgcmapi.atendimento;

public enum EStatus {

    CANCELADO,
    AGENDADO,
    CONFIRMADO,
    CHEGADA,
    ATENDIMENTO,
    ENCERRADO;

    public EStatus proximo() {
        EStatus status = this;
        int index = ordinal() - 1;
        if (index > 1 && index < values().length) {
            status = values()[index];
        }
        return status;
    }

}
