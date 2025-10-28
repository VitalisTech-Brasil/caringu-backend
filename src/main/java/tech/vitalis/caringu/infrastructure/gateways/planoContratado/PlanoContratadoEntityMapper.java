package tech.vitalis.caringu.infrastructure.gateways.planoContratado;

import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPagamentoPendenteResponse;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPendenteRequest;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;

public class PlanoContratadoEntityMapper {

    public PlanoContratadoEntity toEntity(PlanoContratado domainObj){
        return new PlanoContratadoEntity(
                domainObj.id(),
                domainObj.aluno(),
                domainObj.plano(),
                StatusEnum.valueOf(domainObj.status().name()),
                domainObj.dataContratacao(),
                domainObj.dataFim()
        );
    }


    public PlanoContratado toDomain(PlanoContratadoEntity entity) {
        return new PlanoContratado(
                entity.getId(),
                entity.getAluno(),
                entity.getPlano(),
                StatusEnum.valueOf(entity.getStatus().name()),
                entity.getDataContratacao(),
                entity.getDataFim()
        );
    }
}
