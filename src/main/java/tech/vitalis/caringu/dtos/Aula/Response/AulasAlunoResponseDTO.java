package tech.vitalis.caringu.dtos.Aula.Response;

public record AulasAlunoResponseDTO(
        Integer aulaId,
        String dataAula,
        String diaSemana,
        String horarioAula,
        String horarioFim,
        String nomePersonal,
        Integer treinoId
) {}
