package tech.vitalis.caringu.dtos.Avaliacao;

public record AvaliacaoRequestDTO(
        Integer personalId,
        Integer alunoId,
        Double nota,
        String comentario
) {
}
