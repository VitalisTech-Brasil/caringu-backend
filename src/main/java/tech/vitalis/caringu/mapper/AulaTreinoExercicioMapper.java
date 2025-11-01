package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.ExercicioResumoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AulaTreinoExercicioRemarcarAulaResponseDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class AulaTreinoExercicioMapper {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    // Converte TreinoExercicio → AulaTreinoExercicio vinculado à Aula
    public AulaTreinoExercicio toEntity(Aula aula, TreinoExercicio treinoExercicio, Integer ordem) {
        AulaTreinoExercicio entity = new AulaTreinoExercicio();
        entity.setAula(aula);
        entity.setTreinoExercicio(treinoExercicio);
        entity.setOrdem(ordem != null ? ordem : treinoExercicio.getId()); // ou usar posição sequencial
        entity.setCarga(treinoExercicio.getCarga());
        entity.setRepeticoes(treinoExercicio.getRepeticoes());
        entity.setSeries(treinoExercicio.getSeries());
        entity.setDescanso(treinoExercicio.getDescanso());
        entity.setObservacoesPersonalizadas(treinoExercicio.getObservacoesPersonalizadas());
        return entity;
    }

    // Converte AulaTreinoExercicio → DTO de resposta
    public AulaTreinoExercicioRemarcarAulaResponseDTO toResponse(AulaTreinoExercicio entity) {
        return new AulaTreinoExercicioRemarcarAulaResponseDTO(
                entity.getId(),
                entity.getTreinoExercicio().getId(),
                entity.getTreinoExercicio().getExercicio().getId(),
                entity.getTreinoExercicio().getExercicio().getNome(),
                entity.getTreinoExercicio().getExercicio().getGrupoMuscular(),
                entity.getOrdem(),
                entity.getCarga(),
                entity.getRepeticoes(),
                entity.getSeries(),
                entity.getDescanso(),
                entity.getObservacoesPersonalizadas()
        );
    }

    public List<AulaComTreinoDTO> toAulaComTreinoDTO(List<AulaComTreinoModeloCruDTO> listaCrua) {
        Map<Integer, AulaComTreinoDTO> aulasAgrupadas = new LinkedHashMap<>();

        for (AulaComTreinoModeloCruDTO dto : listaCrua) {
            String diaSemanaCapitalizado = dto.dataHorarioInicio()
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, LOCALE_PT_BR);
            diaSemanaCapitalizado = diaSemanaCapitalizado.substring(0, 1).toUpperCase() + diaSemanaCapitalizado.substring(1);

            final String diaSemana = diaSemanaCapitalizado;

            aulasAgrupadas.computeIfAbsent(dto.idAula(), id -> new AulaComTreinoDTO(
                    dto.idAluno(),
                    dto.nomeAluno(),
                    dto.idAula(),
                    dto.dataHorarioInicio().format(FORMATO_DATA),
                    diaSemana,
                    String.format("%s - %s",
                            dto.dataHorarioInicio().format(FORMATO_HORA),
                            dto.dataHorarioFim().format(FORMATO_HORA)
                    ),
                    dto.treinoId(),
                    dto.nomeTreino(),
                    new ArrayList<>(),
                    dto.nomePersonal(),
                    dto.urlFotoPerfil()
            )).exercicios().add(new ExercicioResumoDTO(
                    dto.exercicioId(),
                    dto.nomeExercicio()
            ));
        }

        return new ArrayList<>(aulasAgrupadas.values());
    }
}
