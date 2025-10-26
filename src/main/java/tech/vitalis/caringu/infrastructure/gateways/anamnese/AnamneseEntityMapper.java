package tech.vitalis.caringu.infrastructure.gateways.anamnese;

import tech.vitalis.caringu.core.domain.entity.Anamnese;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.infrastructure.persistence.anamnese.AnamneseEntity;

public class AnamneseEntityMapper {
    public AnamneseEntity toEntity(Anamnese anamneseDomainObj){
        return new AnamneseEntity(
                anamneseDomainObj.aluno(),
                anamneseDomainObj.objetivoTreino(),
                anamneseDomainObj.lesao(),
                anamneseDomainObj.lesaoDescricao(),
                anamneseDomainObj.frequenciaTreino(),
                anamneseDomainObj.experiencia(),
                anamneseDomainObj.experienciaDescricao(),
                anamneseDomainObj.desconforto(),
                anamneseDomainObj.desconfortoDescricao(),
                anamneseDomainObj.fumante(),
                anamneseDomainObj.proteses(),
                anamneseDomainObj.protesesDescricao(),
                anamneseDomainObj.doencaMetabolica(),
                anamneseDomainObj.doencaMetabolicaDescricao(),
                anamneseDomainObj.deficiencia(),
                anamneseDomainObj.deficienciaDescricao()

        );
    }

    public Anamnese toDomainObj(AnamneseEntity anamneseEntity){
        return new Anamnese(
                anamneseEntity.getId(),
                anamneseEntity.getAluno(),
                anamneseEntity.getObjetivoTreino(),
                anamneseEntity.getLesao(),
                anamneseEntity.getLesaoDescricao(),
                anamneseEntity.getFrequenciaTreino(),
                anamneseEntity.getExperiencia(),
                anamneseEntity.getExperienciaDescricao(),
                anamneseEntity.getDesconforto(),
                anamneseEntity.getDesconfortoDescricao(),
                anamneseEntity.getFumante(),
                anamneseEntity.getProteses(),
                anamneseEntity.getProtesesDescricao(),
                anamneseEntity.getDoencaMetabolica(),
                anamneseEntity.getDoencaMetabolicaDescricao(),
                anamneseEntity.getDeficiencia(),
                anamneseEntity.getDeficienciaDescricao()
        );
    }

    public Anamnese toResponsePerfilDetalhesDTOSemAnamnese(Aluno aluno){
        return new Anamnese(
                null,
                aluno, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null
        );
    }

    public void updateAnamnese(Anamnese domain, AnamneseEntity anamnese){
        if (domain.objetivoTreino() != null) anamnese.setObjetivoTreino(domain.objetivoTreino());
        if (domain.lesao() != null) anamnese.setLesao(domain.lesao());
        if (domain.lesaoDescricao() != null) anamnese.setLesaoDescricao(domain.lesaoDescricao());
        if (domain.frequenciaTreino() != null) anamnese.setFrequenciaTreino(domain.frequenciaTreino());
        if (domain.experiencia() != null) anamnese.setExperiencia(domain.experiencia());
        if (domain.experienciaDescricao() != null) anamnese.setExperienciaDescricao(domain.experienciaDescricao());
        if (domain.desconforto() != null) anamnese.setDesconforto(domain.desconforto());
        if (domain.desconfortoDescricao() != null) anamnese.setDesconfortoDescricao(domain.desconfortoDescricao());
        if (domain.fumante() != null) anamnese.setFumante(domain.fumante());
        if (domain.proteses() != null) anamnese.setProteses(domain.proteses());
        if (domain.protesesDescricao() != null) anamnese.setProtesesDescricao(domain.protesesDescricao());
        if (domain.doencaMetabolica() != null) anamnese.setDoencaMetabolica(domain.doencaMetabolica());
        if (domain.doencaMetabolicaDescricao() != null) anamnese.setDoencaMetabolicaDescricao(domain.doencaMetabolicaDescricao());
        if (domain.deficiencia() != null) anamnese.setDeficiencia(domain.deficiencia());
        if (domain.deficienciaDescricao() != null) anamnese.setDeficienciaDescricao(domain.deficienciaDescricao());
    }

}
