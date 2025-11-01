package tech.vitalis.caringu.infrastructure.di.planoContratado;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.vitalis.caringu.core.adapter.planoContratado.PlanoContratadoDTOMapper;
import tech.vitalis.caringu.core.application.gateways.planoContratado.PlanoContratadoGateway;
import tech.vitalis.caringu.core.application.usecases.planoContratado.PlanoContratadoUseCase;
import tech.vitalis.caringu.infrastructure.gateways.planoContratado.PlanoContratadoEntityMapper;
import tech.vitalis.caringu.infrastructure.gateways.planoContratado.PlanoContratadoRepositoryGateway;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

@Configuration
public class PlanoContratadoConfig {

    @Bean
    PlanoContratadoUseCase planoContratadoUseCase(PlanoContratadoGateway planoContratadoGateway, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, NotificacoesRepository notificacoesRepository, AlunoRepository alunoRepository){
        return new PlanoContratadoUseCase(planoContratadoGateway, preferenciaNotificacaoRepository,notificacoesRepository, alunoRepository);
    }

    @Bean
    PlanoContratadoGateway planoContratadoGateway(PlanoContratadoRepository planoContratadoRepository, PlanoContratadoEntityMapper planoContratadoEntityMapper){
        return new PlanoContratadoRepositoryGateway(planoContratadoRepository, planoContratadoEntityMapper);
    }

    @Bean
    PlanoContratadoEntityMapper planoContratadoEntityMapper(){
        return new PlanoContratadoEntityMapper();
    }

    @Bean
    PlanoContratadoDTOMapper planoContratadoDTOMapper(AlunoMapper alunoMapper, PlanoMapper planoMapper){
        return new PlanoContratadoDTOMapper(alunoMapper, planoMapper);
    }
}

