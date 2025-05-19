package tech.vitalis.caringu.dtos.Aluno;

public record PlanoPertoFimResponseDTO(
        Integer id,
        String nome,
        String urlFotoPerfil,
        Integer aulasRestantes
) {}