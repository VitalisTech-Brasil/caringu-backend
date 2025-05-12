package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Plano.PlanoRequisicaoRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Plano;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlanoMapper {
    public List<PlanoRespostaRecord> PlanoListToResponseList(List<Plano> planoList){
        List<PlanoRespostaRecord> listaDePlanoRespostaRecord = new ArrayList<>();
        for (Plano plano : planoList) {
            listaDePlanoRespostaRecord.add(toResponseRecord(plano));
        }
        return listaDePlanoRespostaRecord;
    }

    public PlanoRespostaRecord toResponseRecord(Plano plano) {
        return new PlanoRespostaRecord(plano.getId(), plano.getNome(), plano.getPeriodo(), plano.getQuantidadeAulas(), plano.getValorAulas());
    }

    public Plano requestToEntity(PlanoRequisicaoRecord planoRequisicaoRecord, PersonalTrainer personalTrainer  ){
        Plano plano = new Plano();
        plano.setNome(planoRequisicaoRecord.nome());
        plano.setPeriodo(planoRequisicaoRecord.periodo());
        plano.setPersonalTrainer(personalTrainer);
        plano.setQuantidadeAulas(planoRequisicaoRecord.quantidadeAulas());
        plano.setValorAulas(planoRequisicaoRecord.valorAulas());
        return plano;
    }


}
