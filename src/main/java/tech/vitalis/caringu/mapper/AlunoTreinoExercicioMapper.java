package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Component
public class AlunoTreinoExercicioMapper {

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
