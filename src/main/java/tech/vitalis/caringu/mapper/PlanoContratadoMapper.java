package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.entity.PlanoContratado;

@Component
public class PlanoContratadoMapper {
    public PlanoContratadoRespostaRecord toResponseRecord(PlanoContratado planoContratado) {
        return new PlanoContratadoRespostaRecord(planoContratado.getAluno(), planoContratado.getPlano(), planoContratado.getStatus(), planoContratado.getDataContratacao(), planoContratado.getDataFim());
    }
}
