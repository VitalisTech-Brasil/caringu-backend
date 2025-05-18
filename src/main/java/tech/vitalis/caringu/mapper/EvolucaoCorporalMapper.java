package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.EvolucaoCorporal;

@Component
public class EvolucaoCorporalMapper {

    private final AlunoMapper alunoMapper;

    public EvolucaoCorporalMapper(AlunoMapper alunoMapper) {
        this.alunoMapper = alunoMapper;
    }

    public EvolucaoCorporal toEntity(EvolucaoCorporalRequestPostDTO dto, Aluno aluno) {
        EvolucaoCorporal ec = new EvolucaoCorporal();
        ec.setTipo(dto.tipo());
        ec.setUrlFotoShape(dto.urlFotoShape());
        ec.setDataEnvio(dto.dataEnvio());
        ec.setPeriodoAvaliacao(dto.periodoAvaliacao());
        ec.setAluno(aluno);
        return ec;
    }

    public EvolucaoCorporalResponseGetDTO toResponseDTO(EvolucaoCorporal ec) {
        return new EvolucaoCorporalResponseGetDTO(
                ec.getId(),
                ec.getTipo(),
                ec.getUrlFotoShape(),
                ec.getDataEnvio(),
                ec.getPeriodoAvaliacao(),
                alunoMapper.toResponseDTO(ec.getAluno())
        );
    }
}
