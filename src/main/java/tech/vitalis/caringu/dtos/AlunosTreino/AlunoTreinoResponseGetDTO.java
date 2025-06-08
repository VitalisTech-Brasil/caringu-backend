package tech.vitalis.caringu.dtos.AlunosTreino;

import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;

import java.time.LocalDate;
import java.util.List;

public record AlunoTreinoResponseGetDTO(
        Integer id,
        AlunoResponseGetDTO alunos,
        TreinoExercicioResponseGetDto treinosExercicios,
        //LocalDateTime dataHorarioInicio,
        //LocalDateTime dataHorarioFim,
        List<String> diasSemana,
//        Integer periodoAvaliacao,
        LocalDate dataVencimento
) {
}
