package tech.vitalis.caringu.dtos.AlunosTreino;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AlunoTreinoResponseGetDTO(
        Integer id,
        AlunoResponseGetDTO alunos,
        TreinoExercicioResponseGetDto treinosExercicios,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        List<String> diasSemana,
        Integer periodoAvaliacao,
        LocalDate dataVencimento
) {
}
