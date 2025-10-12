package tech.vitalis.caringu.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoRequestDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoResponseDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Avaliacao;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AvaliacaoMapper {
    @Autowired
    PersonalTrainerMapper personalTrainerMapper;
    @Autowired
    AlunoMapper alunoMapper;

    public AvaliacaoResponseDTO toDto(Avaliacao avaliacao) {
        return new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getNota(),
                personalTrainerMapper.toResponseDTO(avaliacao.getPersonalTrainer()),
                alunoMapper.toResponseDTO(avaliacao.getAluno()),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao()
        );
    }

    public List<AvaliacaoResponseDTO> toDto(List<Avaliacao> avaliacoes){
        return avaliacoes.stream().map(this::toDto).toList();
    }

    public Avaliacao toEntity(AvaliacaoRequestDTO avaliacaoRequestDTO, Aluno aluno, PersonalTrainer personalTrainer) {
        return new Avaliacao(
                personalTrainer,
                aluno,
                avaliacaoRequestDTO.nota(),
                avaliacaoRequestDTO.comentario(),
                LocalDateTime.now()
        );
    }


}
