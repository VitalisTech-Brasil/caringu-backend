package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.AlunoTreinoExercicio;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Component
public class AlunoTreinoExercicioMapper {

    private final TreinoMapper treinoMapper;
    private final ExercicioMapper exercicioMapper;

    public AlunoTreinoExercicioMapper(TreinoMapper treinoMapper, ExercicioMapper exercicioMapper) {
        this.treinoMapper = treinoMapper;
        this.exercicioMapper = exercicioMapper;
    }

    public AlunoTreinoExercicio toEntity(TreinoExercicioRequestPostDto dto){
        if (dto == null) return null;

        AlunoTreinoExercicio treinoExercicio = new AlunoTreinoExercicio();
        treinoExercicio.setCarga(dto.carga());
        treinoExercicio.setRepeticoes(dto.repeticoes());
        treinoExercicio.setSeries(dto.series());
        treinoExercicio.setDescanso(dto.descanso());
        treinoExercicio.setDataModificacao(dto.dataHoraModificacao());

        return treinoExercicio;
    }

    public TreinoExercicioResponseGetDto toResponseDTO(AlunoTreinoExercicio treinoExercicio){
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
                treinoExercicio.getTreino().getGrauDificuldade()
        );
    }

    public TreinoExercicioResumoDTO toResumoDTO(List<TreinoExercicioResumoModeloCruQuerySqlDTO> lista) {
        TreinoExercicioResumoModeloCruQuerySqlDTO primeiro = lista.getFirst();

        return new TreinoExercicioResumoDTO(
                primeiro.treinoId(),
                primeiro.nomeTreino(),
                primeiro.grauDificuldade(),
                primeiro.origemTreinoExercicio(),
                primeiro.favorito(),
                lista.size()
        );
    }
}
