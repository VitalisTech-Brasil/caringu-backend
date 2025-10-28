package tech.vitalis.caringu.core.adapter.planoContratado;

import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPagamentoPendenteResponse;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPendenteRequest;
import tech.vitalis.caringu.infrastructure.web.planoContratado.PlanoContratadoResponse;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;

public class PlanoContratadoDTOMapper {
    private final AlunoMapper alunoMapper;
    private final PlanoMapper planoMapper;

    public PlanoContratadoDTOMapper(AlunoMapper alunoMapper, PlanoMapper planoMapper) {
        this.alunoMapper = alunoMapper;
        this.planoMapper = planoMapper;
    }

    public PlanoContratadoResponse toResponse(PlanoContratado domain){
        return new PlanoContratadoResponse(
            domain.id(),
            alunoMapper.toResponseDTO(domain.aluno()),
            planoMapper.toResponseRecord(domain.plano()),
            domain.status(),
            domain.dataContratacao(),
            domain.dataFim()
        );
    }

    public PlanoContratadoResponse toResponseContratarPlano(PlanoContratadoEntity domain){
        return new PlanoContratadoResponse(
                domain.getId(),
                alunoMapper.toResponseDTO(domain.getAluno()),
                planoMapper.toResponseRecord(domain.getPlano()),
                domain.getStatus(),
                domain.getDataContratacao(),
                domain.getDataFim()
        );
    }

    public GetPlanoContratadoPagamentoPendenteResponse toPagamentoPendenteResponseDTO(PlanoContratado domain) {
        return new GetPlanoContratadoPagamentoPendenteResponse(
                domain.id(),
                domain.plano().getId(),
                alunoMapper.toResponseDTO(domain.aluno()),
                domain.status(),
                domain.dataContratacao(),
                domain.dataFim()
        );
    }

    public GetPlanoContratadoPendenteRequest toPendenteResponseDTO(PlanoContratado domain) {
        return new GetPlanoContratadoPendenteRequest(
                domain.id(),
                domain.aluno().getNome(),
                domain.aluno().getCelular(),
                domain.plano().getNome(),
                domain.plano().getPeriodo(),
                domain.plano().getQuantidadeAulas(),
                domain.plano().getValorAulas(),
                domain.status()
        );
    }
}
