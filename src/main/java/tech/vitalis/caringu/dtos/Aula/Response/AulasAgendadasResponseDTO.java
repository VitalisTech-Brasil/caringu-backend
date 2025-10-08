package tech.vitalis.caringu.dtos.Aula.Response;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;

public record AulasAgendadasResponseDTO(
        Integer idAluno,
        String nomeAluno,
        String emailAluno,
        String urlFotoPerfil,
        Integer idTreino,
        String nomeTreino,
        Integer idAula,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        AulaStatusEnum status
) {
}
