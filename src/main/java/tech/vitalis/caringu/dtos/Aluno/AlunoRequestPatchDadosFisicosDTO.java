package tech.vitalis.caringu.dtos.Aluno;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;

public record AlunoRequestPatchDadosFisicosDTO(
        @Positive(message = "O peso deve ser um número positivo.")
        Double peso,

        @Positive(message = "A altura deve ser um número positivo.")
        Double altura,

        @Schema(description = "Nível de atividade do aluno", allowableValues = {"SEDENTARIO", "LEVEMENTE_ATIVO", "MODERADAMENTE_ATIVO", "MUITO_ATIVO", "EXTREMAMENTE_ATIVO"})
        NivelAtividadeEnum nivelAtividade,

        @Schema(description = "Nível de experiência do aluno", allowableValues = {"INICIANTE", "INTERMEDIARIO", "AVANCADO"})
        NivelExperienciaEnum nivelExperiencia
) {
}
