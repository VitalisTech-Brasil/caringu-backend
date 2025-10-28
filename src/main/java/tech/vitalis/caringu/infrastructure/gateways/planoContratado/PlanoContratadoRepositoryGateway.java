package tech.vitalis.caringu.infrastructure.gateways.planoContratado;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.core.application.gateways.planoContratado.PlanoContratadoGateway;
import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPagamentoPendenteResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlanoContratadoRepositoryGateway implements PlanoContratadoGateway {
    private final PlanoContratadoRepository repository;
    private final PlanoContratadoEntityMapper mapper;

    public PlanoContratadoRepositoryGateway(PlanoContratadoRepository repository, PlanoContratadoEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PlanoContratado createPlanoContratado(PlanoContratado planoContratado) {
        PlanoContratadoEntity entity = mapper.toEntity(planoContratado);
        PlanoContratadoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<PlanoContratado> getPlanoContratadoById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public PlanoContratado update(PlanoContratado planoContratado) {
        PlanoContratadoEntity entity = mapper.toEntity(planoContratado);
        PlanoContratadoEntity updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public List<PlanoContratado> listSolicitacoesPendentes(Integer personalId) {
        return repository.listSolicitacoesPendentes(personalId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Integer countPlanosVencendoAte(LocalDate dataLimite, Integer personalId) {
        return repository.buscarDataFimIgualOuAntesDataLimite(dataLimite, personalId);
    }

    @Override
    public List<PlanoContratado> findPorAlunoIdEStatus(Integer alunoId) {
        return repository.findPorAlunoIdStatus(alunoId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /*
    @Override
    public List<PlanoContratado> findPorAlunoIdEStatus(Integer alunoId, StatusEnum status) {
        return repository.findByAlunoIdAndStatus(alunoId, status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
     */
}
