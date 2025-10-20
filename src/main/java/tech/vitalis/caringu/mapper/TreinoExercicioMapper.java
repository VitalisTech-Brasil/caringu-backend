package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Component
public class TreinoExercicioMapper {

    private final TreinoMapper treinoMapper;
    private final ExercicioMapper exercicioMapper;

    public TreinoExercicioMapper(TreinoMapper treinoMapper, ExercicioMapper exercicioMapper) {
        this.treinoMapper = treinoMapper;
        this.exercicioMapper = exercicioMapper;
    }

    public TreinoExercicio toEntity(TreinoExercicioRequestPostDto dto) {
        if (dto == null) return null;

        TreinoExercicio treinoExercicio = new TreinoExercicio();
        treinoExercicio.setCarga(dto.carga());
        treinoExercicio.setRepeticoes(dto.repeticoes());
        treinoExercicio.setSeries(dto.series());
        treinoExercicio.setDescanso(dto.descanso());
        treinoExercicio.setDataModificacao(dto.dataHoraModificacao());

        return treinoExercicio;
    }

    public TreinoExercicioResponseGetDto toResponseDTO(TreinoExercicio treinoExercicio) {
        if (treinoExercicio == null) return null;

        return new TreinoExercicioResponseGetDto(
                treinoExercicio.getId(),
                treinoMapper.toResponseDTO(treinoExercicio.getTreino()),
                exercicioMapper.toResponseDTO(treinoExercicio.getExercicio()),
                treinoExercicio.getCarga(),
                treinoExercicio.getRepeticoes(),
                treinoExercicio.getSeries(),
                treinoExercicio.getDescanso(),
                treinoExercicio.getDataModificacao(),
                treinoExercicio.getTreino().getOrigem(),
                treinoExercicio.getTreino().getGrauDificuldade().getValor()
        );
    }

    public TreinoExercicioResumoDTO toResumoDTO(List<TreinoExercicioResumoModeloCruQuerySqlDTO> lista) {
        TreinoExercicioResumoModeloCruQuerySqlDTO primeiro = lista.getFirst();

        return new TreinoExercicioResumoDTO(
                primeiro.treinoId(),
                primeiro.nomeTreino(),
                primeiro.grauDificuldade().getValor(),
                primeiro.origemTreinoExercicio(),
                primeiro.favorito(),
                lista.size()
        );
    }
}
