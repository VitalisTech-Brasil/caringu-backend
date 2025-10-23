package tech.vitalis.caringu.dtos.Avaliacao;

import java.time.LocalDateTime;

public record FiltroAvaliacaoResponseDTO (
        Integer personalId,
        Integer alunoId,
        Double nota,
        String comentario,
        LocalDateTime dataAvaliacao
){}
