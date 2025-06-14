package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoRequestUpdateDTO;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.AlunoTreino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;

@Component
public class AlunoTreinoMapper {

    private final AlunoMapper alunoMapper;
    private final TreinoExercicioMapper treinoExercicioMapper;
    private final AlunoRepository alunoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;

    public AlunoTreinoMapper(AlunoMapper alunoMapper, TreinoExercicioMapper treinoExercicioMapper, AlunoRepository alunoRepository, TreinoExercicioRepository treinoExercicioRepository) {
        this.alunoMapper = alunoMapper;
        this.treinoExercicioMapper = treinoExercicioMapper;
        this.alunoRepository = alunoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
    }


    public AlunoTreino toEntity(AlunoTreinoRequestPostDTO dto) {
        if (dto == null) return null;

        AlunoTreino alunoTreino = new AlunoTreino();

        Aluno aluno = alunoRepository.findById(dto.alunosId())
                .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno com o ID " + dto.alunosId() + " não encontrado"));

        TreinoExercicio treinoExercicio = treinoExercicioRepository.findById(dto.treinosExerciciosId())
                .orElseThrow(() -> new ApiExceptions.BadRequestException("Treino Exercicio com o ID " + dto.treinosExerciciosId() + " não encontrado"));

        alunoTreino.setAlunos(aluno);
        alunoTreino.setTreinosExercicios(treinoExercicio);
//        alunoTreino.setDataHorarioInicio(dto.dataHorarioInicio());
//        alunoTreino.setDataHorarioFim(dto.dataHorarioFim());
        alunoTreino.setDiasSemana(dto.diasSemana());
//        alunoTreino.setPeriodoAvaliacao(dto.periodoAvaliacao());
        alunoTreino.setDataVencimento(dto.dataVencimento());

        return alunoTreino;
    }

    public AlunoTreinoResponseGetDTO toResponseDTO(AlunoTreino alunoTreino){
        if (alunoTreino == null) return null;

        return new AlunoTreinoResponseGetDTO(
                alunoTreino.getId(),
                alunoMapper.toResponseDTO(alunoTreino.getAlunos()),
                treinoExercicioMapper.toResponseDTO(alunoTreino.getTreinosExercicios()),
//                alunoTreino.getDataHorarioInicio(),
//                alunoTreino.getDataHorarioFim(),
                alunoTreino.getDiasSemana(),
//                alunoTreino.getPeriodoAvaliacao(),
                alunoTreino.getDataVencimento()
        );
    }

}
