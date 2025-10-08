package tech.vitalis.caringu.dtos.Avaliacao;

import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Integer id,
        Double nota,
        PersonalTrainerResponseGetDTO personal,
        AlunoResponseGetDTO aluno,
        String comentario,
        LocalDateTime dataAvaliacao
) {
}
