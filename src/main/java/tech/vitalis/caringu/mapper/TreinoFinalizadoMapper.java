package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoFinalizadoRequestPostDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoFinalizadoResponseGetDTO;
import tech.vitalis.caringu.entity.TreinoFinalizado;

@Component
public class TreinoFinalizadoMapper {

    private final AlunoTreinoMapper alunoTreinoMapper;

    public TreinoFinalizadoMapper(AlunoTreinoMapper alunoTreinoMapper) {
        this.alunoTreinoMapper = alunoTreinoMapper;
    }

    public TreinoFinalizado toEntity(TreinoFinalizadoRequestPostDTO dto){
        if(dto == null) return null;

        TreinoFinalizado treinoFinalizado = new TreinoFinalizado();
        treinoFinalizado.setDataHorarioInicio(dto.dataHorarioInicio());
        treinoFinalizado.setDataHorarioFim(dto.dataHorarioFim());
        return treinoFinalizado;
    }

    public TreinoFinalizadoResponseGetDTO toResponseDTO(TreinoFinalizado treinoFinalizado){
        if (treinoFinalizado == null) return null;

        return new TreinoFinalizadoResponseGetDTO(
                treinoFinalizado.getId(),
                treinoFinalizado.getDataHorarioInicio(),
                treinoFinalizado.getDataHorarioFim(),
                alunoTreinoMapper.toResponseDTO(treinoFinalizado.getAlunoTreino())
        );
    }
}
