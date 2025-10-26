package tech.vitalis.caringu.infrastructure.gateways.anamnese;

import tech.vitalis.caringu.core.application.gateways.anamnese.AnamneseGateway;
import tech.vitalis.caringu.core.domain.entity.Anamnese;
import tech.vitalis.caringu.infrastructure.persistence.anamnese.AnamneseEntity;
import tech.vitalis.caringu.infrastructure.persistence.anamnese.AnamneseRepository;

import java.util.Optional;

public class AnamneseRepositoryGateway implements AnamneseGateway {
    private final AnamneseRepository anamneseRepository;
    private final AnamneseEntityMapper anamneseMapper;

    public AnamneseRepositoryGateway(AnamneseRepository anamneseRepository, AnamneseEntityMapper anamneseMapper) {
        this.anamneseRepository = anamneseRepository;
        this.anamneseMapper = anamneseMapper;
    }

    @Override
    public Anamnese createAnamnese(Anamnese anamneseDomainObj){
        AnamneseEntity anamneseEntity = anamneseMapper.toEntity(anamneseDomainObj);
        AnamneseEntity saveObj = anamneseRepository.save(anamneseEntity);
        return anamneseMapper.toDomainObj(saveObj);
    }

    @Override
    public Optional<Anamnese> getAnamneseByAlunoId(Integer alunoId){
        return anamneseRepository.findByAlunoId(alunoId)
                .map(anamneseMapper::toDomainObj);
    }

    @Override
    public Optional<Anamnese> getAnamneseById(Integer anamneseId){
        return anamneseRepository.findById(anamneseId)
                .map(anamneseMapper::toDomainObj);
    }

    @Override
    public Integer countAnamnesesPendentes(Integer personalId){
        return anamneseRepository.countAnamnesesPendentesByPersonalId(personalId);
    }

    @Override
    public Anamnese patchAnamnese(Integer anamneseId, Anamnese anamneseObj) {
        AnamneseEntity anamneseExistente = anamneseRepository.findById(anamneseId)
                .orElseThrow(() -> new RuntimeException("Anamnese não encontrada"));

        anamneseMapper.updateAnamnese(anamneseObj, anamneseExistente);
        anamneseRepository.save(anamneseExistente);

        return anamneseMapper.toDomainObj(anamneseExistente);
    }

}
