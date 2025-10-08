package tech.vitalis.caringu.dtos.SessaoTreino;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;

public record SessaoAulasAgendadasResponseDTO(
        Integer idAluno,
        String nomeAluno,
        String urlFotoPerfil,
        Integer idSessaoTreino,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        AulaStatusEnum status
) {
}
