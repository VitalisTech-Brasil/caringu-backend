package tech.vitalis.caringu.infrastructure.di.anamnese;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.vitalis.caringu.core.application.gateways.anamnese.AnamneseGateway;
import tech.vitalis.caringu.core.application.usecases.anamnese.*;
import tech.vitalis.caringu.infrastructure.gateways.anamnese.AnamneseEntityMapper;
import tech.vitalis.caringu.infrastructure.gateways.anamnese.AnamneseRepositoryGateway;
import tech.vitalis.caringu.infrastructure.persistence.anamnese.AnamneseRepository;
import tech.vitalis.caringu.core.adapter.anamnese.AnamneseDTOMapper;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;

@Configuration
public class AnamneseConfig {

    @Bean
    AnamneseUseCase anamneseUseCase(AnamneseGateway anamneseGateway, AlunoRepository alunoRepository, AnamneseEntityMapper anamneseMapper, PersonalTrainerRepository personalTrainerRepository){
        return new AnamneseUseCase(anamneseGateway, alunoRepository, anamneseMapper, personalTrainerRepository);
    }


    @Bean
    AnamneseGateway anamneseGateway( AnamneseRepository anamneseRepository, AnamneseEntityMapper anamneseEntityMapper){
        return new AnamneseRepositoryGateway(anamneseRepository, anamneseEntityMapper);
    }

    @Bean
    AnamneseEntityMapper anamneseEntityMapper(){
        return new AnamneseEntityMapper();
    }

    @Bean
    AnamneseDTOMapper anamneseDTOMapper(AlunoMapper aluno, AlunoRepository repository){
        return new AnamneseDTOMapper(aluno, repository);
    }

}
