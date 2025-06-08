package tech.vitalis.caringu.dtos.Anamnese;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

@Schema(description = "DTO para atualização parcial de Anamnese")
public record AnamneseRequestPatchDTO(
        @Schema(description = "Objetivo principal do treino", example = "Hipertrofia e resistência")
        String objetivoTreino,

        @Schema(description = "Se o aluno possui alguma lesão", example = "false")
        Boolean lesao,

        @Schema(description = "Descrição da lesão", example = "Lesão no joelho esquerdo")
        String lesaoDescricao,

        @Schema(description = "Frequência semanal de treino", example = "5")
        FrequenciaTreinoEnum frequenciaTreino,

        @Schema(description = "Se já possui experiência com treinos", example = "true")
        Boolean experiencia,

        @Schema(description = "Descrição da experiência", example = "Treinava 3 anos atrás")
        String experienciaDescricao,

        @Schema(description = "Se sente algum desconforto durante o treino", example = "false")
        Boolean desconforto,

        @Schema(description = "Descrição do desconforto", example = "Desconforto na lombar ao agachar")
        String desconfortoDescricao,

        @Schema(description = "Se o aluno é fumante", example = "false")
        Boolean fumante,

        @Schema(description = "Se utiliza próteses", example = "false")
        Boolean proteses,

        @Schema(description = "Descrição das próteses", example = "Prótese no joelho direito")
        String protesesDescricao,

        @Schema(description = "Se possui alguma doença metabólica", example = "false")
        Boolean doencaMetabolica,

        @Schema(description = "Descrição da doença metabólica", example = "Diabetes tipo 2")
        String doencaMetabolicaDescricao,

        @Schema(description = "Se possui alguma deficiência", example = "false")
        Boolean deficiencia,

        @Schema(description = "Descrição da deficiência", example = "Deficiência auditiva unilateral")
        String deficienciaDescricao
) {
}
