package tech.vitalis.caringu.infrastructure.web.anamnese;

import tech.vitalis.caringu.core.domain.valueObject.FrequenciaTreinoEnum;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;

public record AnamneseResponse(
        Integer id,
        AlunoResponseGetDTO aluno,
        String objetivoTreino,
        Boolean lesao,
        String lesaoDescricao,
        FrequenciaTreinoEnum frequenciaTreino,
        Boolean experiencia,
        String experienciaDescricao,
        Boolean desconforto,
        String desconfortoDescricao,
        Boolean fumante,
        Boolean proteses,
        String protesesDescricao,
        Boolean doencaMetabolica,
        String doencaMetabolicaDescricao,
        Boolean deficiencia,
        String deficienciaDescricao
){
}
