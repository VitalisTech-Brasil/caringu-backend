package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;

@Component
public class PlanoContratadoMapper {
    public PlanoContratadoRespostaRecord toResponseRecord(PlanoContratadoEntity planoContratado) {
        return new PlanoContratadoRespostaRecord(planoContratado.getAluno(), planoContratado.getPlano(), planoContratado.getStatus(), planoContratado.getDataContratacao(), planoContratado.getDataFim());
    }
}
