package tech.vitalis.caringu.core.application.gateways.planoContratado;

import tech.vitalis.caringu.core.domain.entity.PlanoContratado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlanoContratadoGateway {
    PlanoContratado createPlanoContratado(PlanoContratado planoContratado);
    Optional<PlanoContratado> getPlanoContratadoById(Integer id);
    PlanoContratado update(PlanoContratado planoContratado);

    List<PlanoContratado> listSolicitacoesPendentes(Integer personalId);
    Integer countPlanosVencendoAte(LocalDate dataLimite, Integer personalId);
    List<PlanoContratado> findPorAlunoIdEStatus(Integer alunoId);
}
