package tech.vitalis.caringu.dtos.TreinoFinalizado;

import java.time.LocalDateTime;

public record TreinoIdentificacaoFinalizadoResponseDTO(
        Integer id,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        Integer idAluno,
        String nomeAluno,
        String urlFotoPerfil,
        boolean finalizado
) {
}
