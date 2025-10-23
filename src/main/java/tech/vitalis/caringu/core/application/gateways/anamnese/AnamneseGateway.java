package tech.vitalis.caringu.core.application.gateways.anamnese;

import tech.vitalis.caringu.core.domain.entity.Anamnese;

import java.util.Optional;

public interface AnamneseGateway {
    Anamnese createAnamnese(Anamnese anamnese);
    Optional<Anamnese> getAnamneseByAlunoId(Integer alunoId);
    Integer countAnamnesesPendentes(Integer personalId);
    Anamnese patchAnamnese(Integer anamneseId, Anamnese anamnese);
    Optional<Anamnese> getAnamneseById(Integer id);
}
