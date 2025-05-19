package tech.vitalis.caringu.dtos.Aluno;

public record AlunoAtivoResponseDTO(
        Integer id,
        String nome,
        String celular,
        String objetivo,
        Boolean temAnamnese,
        Boolean temTreinos
) {
}
