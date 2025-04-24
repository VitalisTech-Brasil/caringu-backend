package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.entity.TreinoExercicio;

@Component
public class TreinoExercicioMapper {

    private final TreinoMapper treinoMapper;
    private final ExercicioMapper exercicioMapper;

    public TreinoExercicioMapper(TreinoMapper treinoMapper, ExercicioMapper exercicioMapper) {
        this.treinoMapper = treinoMapper;
        this.exercicioMapper = exercicioMapper;
    }

    public TreinoExercicio toEntity(TreinoExercicioRequestPostDto dto){
        if (dto == null) return null;

        TreinoExercicio treinoExercicio = new TreinoExercicio();
        treinoExercicio.setCarga(dto.carga());
        treinoExercicio.setRepeticoes(dto.repeticoes());
        treinoExercicio.setSeries(dto.series());
        treinoExercicio.setDescanso(dto.descanso());
        treinoExercicio.setDataHoraCriacao(dto.dataHoraCriacao());
        treinoExercicio.setDataHoraModificacao(dto.dataHoraModificacao());
        treinoExercicio.setFavorito(dto.favorito());
        treinoExercicio.setGrauDificuldade(dto.grauDificuldade());

        return treinoExercicio;
    }

    public TreinoExercicioResponseGetDto toResponseDTO(TreinoExercicio treinoExercicio){
        if (treinoExercicio == null) return null;

        return new TreinoExercicioResponseGetDto(
                treinoExercicio.getId(),
                treinoMapper.toResponseDTO(treinoExercicio.getTreinos()),
                exercicioMapper.toResponseDTO(treinoExercicio.getExercicio()),
                treinoExercicio.getCarga(),
                treinoExercicio.getRepeticoes(),
                treinoExercicio.getSeries(),
                treinoExercicio.getDescanso(),
                treinoExercicio.getDataHoraCriacao(),
                treinoExercicio.getDataHoraModificacao(),
                treinoExercicio.getFavorito(),
                treinoExercicio.getGrauDificuldade()
        );
    }
}
