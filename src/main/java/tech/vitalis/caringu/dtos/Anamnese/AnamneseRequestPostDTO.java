package tech.vitalis.caringu.dtos.Anamnese;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para criação de uma nova Anamnese")
public record AnamneseRequestPostDTO(
        @NotNull(message = "O campo 'alunoId' é obrigatório e deve conter o ID do aluno.")
        @Schema(description = "ID do aluno relacionado à anamnese", example = "1")
        Integer alunoId,

        @NotBlank(message = "O campo 'objetivoTreino' é obrigatório e não pode estar em branco.")
        @Schema(description = "Objetivo principal do treino", example = "Hipertrofia e resistência")
        String objetivoTreino,

        @NotNull(message = "O campo 'lesao' é obrigatório. Informe se o aluno possui lesão.")
        @Schema(description = "Se o aluno possui alguma lesão", example = "false")
        Boolean lesao,

        @Schema(description = "Descrição da lesão (caso exista)", example = "Lesão no joelho esquerdo")
        String lesaoDescricao,

        @Schema(
                description = "Frequência semanal de treino (1 a 7 dias)",
                example = "5",
                allowableValues = {"1", "2", "3", "4", "5", "6", "7"}
        )
        String frequenciaTreino,

        @NotNull(message = "O campo 'experiencia' é obrigatório. Informe se o aluno já teve experiência com treinos.")
        @Schema(description = "Se já possui experiência com treinos", example = "true")
        Boolean experiencia,

        @Schema(description = "Descrição da experiência (caso tenha)", example = "Treinava 3 anos atrás")
        String experienciaDescricao,

        @NotNull(message = "O campo 'desconforto' é obrigatório. Informe se o aluno sente desconforto durante o treino.")
        @Schema(description = "Se sente algum desconforto durante o treino", example = "false")
        Boolean desconforto,

        @Schema(description = "Descrição do desconforto (caso exista)", example = "Desconforto na lombar ao agachar")
        String desconfortoDescricao,

        @NotNull(message = "O campo 'fumante' é obrigatório. Informe se o aluno é fumante.")
        @Schema(description = "Se o aluno é fumante", example = "false")
        Boolean fumante,

        @NotNull(message = "O campo 'proteses' é obrigatório. Informe se o aluno utiliza próteses.")
        @Schema(description = "Se utiliza próteses", example = "false")
        Boolean proteses,

        @Schema(description = "Descrição das próteses (caso utilize)", example = "Prótese no joelho direito")
        String protesesDescricao,

        @NotNull(message = "O campo 'doencaMetabolica' é obrigatório. Informe se possui alguma doença metabólica.")
        @Schema(description = "Se possui alguma doença metabólica", example = "false")
        Boolean doencaMetabolica,

        @Schema(description = "Descrição da doença metabólica (caso tenha)", example = "Diabetes tipo 2")
        String doencaMetabolicaDescricao,

        @NotNull(message = "O campo 'deficiencia' é obrigatório. Informe se possui alguma deficiência.")
        @Schema(description = "Se possui alguma deficiência", example = "false")
        Boolean deficiencia,

        @Schema(description = "Descrição da deficiência (caso tenha)", example = "Deficiência auditiva unilateral")
        String deficienciaDescricao
) {
}
