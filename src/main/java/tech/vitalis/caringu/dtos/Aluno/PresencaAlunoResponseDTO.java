package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

public record PresencaAlunoResponseDTO(
        Integer id,
        String nome,
        String urlFotoPerfil,
        Long totalPresencas,
        FrequenciaTreinoEnum frequenciaEsperada
) {
}
