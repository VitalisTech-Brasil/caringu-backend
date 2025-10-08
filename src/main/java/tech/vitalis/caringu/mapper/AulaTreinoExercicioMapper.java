package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AulaTreinoExercicioRemarcarAulaResponseDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.entity.TreinoExercicio;

@Component
public class AulaTreinoExercicioMapper {
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
}
