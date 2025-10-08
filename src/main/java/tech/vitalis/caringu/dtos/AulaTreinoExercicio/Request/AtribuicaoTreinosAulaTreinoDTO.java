package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AtribuicaoTreinosAulaTreinoDTO(

        @NotNull(message = "Id do aluno é obrigatório.")
        Integer idAluno,

        @NotNull(message = "Id do plano contratado é obrigatório.")
        Integer idPlanoContratado,

        @NotNull(message = "Id do treino é obrigatório.")
        Integer idTreino,

        @NotEmpty(message = "É necessário informar pelo menos uma aula para atribuir o treino.")
        List<@Valid HorarioAulaDTO> listaHorariosAula
) {}