package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPatchDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPostDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponseGetDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponsePatchDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.AlunoGetPerfilDetalhesDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.AnamneseGetPerfilDetalhesDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Anamnese;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

@Component
public class AnamneseMapper {

    private final AlunoMapper alunoMapper;

    public AnamneseMapper(AlunoMapper alunoMapper) {
        this.alunoMapper = alunoMapper;
    }

    public Anamnese toEntity(AnamneseRequestPostDTO dto, Aluno aluno) {
        Anamnese anamnese = new Anamnese();
        anamnese.setAluno(aluno);
        anamnese.setObjetivoTreino(dto.objetivoTreino());
        anamnese.setLesao(dto.lesao());
        anamnese.setLesaoDescricao(dto.lesaoDescricao());

        FrequenciaTreinoEnum frequenciaTreino = FrequenciaTreinoEnum.fromValor(dto.frequenciaTreino());
        anamnese.setFrequenciaTreino(frequenciaTreino);

        anamnese.setExperiencia(dto.experiencia());
        anamnese.setExperienciaDescricao(dto.experienciaDescricao());
        anamnese.setDesconforto(dto.desconforto());
        anamnese.setDesconfortoDescricao(dto.desconfortoDescricao());
        anamnese.setFumante(dto.fumante());
        anamnese.setProteses(dto.proteses());
        anamnese.setProtesesDescricao(dto.protesesDescricao());
        anamnese.setDoencaMetabolica(dto.doencaMetabolica());
        anamnese.setDoencaMetabolicaDescricao(dto.doencaMetabolicaDescricao());
        anamnese.setDeficiencia(dto.deficiencia());
        anamnese.setDeficienciaDescricao(dto.deficienciaDescricao());
        return anamnese;
    }

    public AnamneseResponseGetDTO toResponseDTO(Anamnese entity) {

        return new AnamneseResponseGetDTO(
                entity.getId(),
                alunoMapper.toResponseDTO(entity.getAluno()),
                entity.getObjetivoTreino(),
                entity.getLesao(),
                entity.getLesaoDescricao(),
                entity.getFrequenciaTreino(),
                entity.getExperiencia(),
                entity.getExperienciaDescricao(),
                entity.getDesconforto(),
                entity.getDesconfortoDescricao(),
                entity.getFumante(),
                entity.getProteses(),
                entity.getProtesesDescricao(),
                entity.getDoencaMetabolica(),
                entity.getDoencaMetabolicaDescricao(),
                entity.getDeficiencia(),
                entity.getDeficienciaDescricao()
        );
    }

    public AnamneseGetPerfilDetalhesDTO toResponsePerfilDetalhesDTO(Anamnese anamnese) {
        return new AnamneseGetPerfilDetalhesDTO(
                alunoMapper.toResponseDTO(anamnese.getAluno()),
                anamnese.getObjetivoTreino(),
                anamnese.getLesao(),
                anamnese.getLesaoDescricao(),
                anamnese.getFrequenciaTreino(),
                anamnese.getExperiencia(),
                anamnese.getExperienciaDescricao(),
                anamnese.getDesconforto(),
                anamnese.getDesconfortoDescricao(),
                anamnese.getFumante(),
                anamnese.getProteses(),
                anamnese.getProtesesDescricao(),
                anamnese.getDoencaMetabolica(),
                anamnese.getDoencaMetabolicaDescricao(),
                anamnese.getDeficiencia(),
                anamnese.getDeficienciaDescricao()
        );
    }

    public void updateAnamneseFromDto(AnamneseRequestPatchDTO dto, Anamnese entity) {
        if (dto.objetivoTreino() != null) entity.setObjetivoTreino(dto.objetivoTreino());
        if (dto.lesao() != null) entity.setLesao(dto.lesao());
        if (dto.lesaoDescricao() != null) entity.setLesaoDescricao(dto.lesaoDescricao());
        if (dto.frequenciaTreino() != null) entity.setFrequenciaTreino(dto.frequenciaTreino());
        if (dto.experiencia() != null) entity.setExperiencia(dto.experiencia());
        if (dto.experienciaDescricao() != null) entity.setExperienciaDescricao(dto.experienciaDescricao());
        if (dto.desconforto() != null) entity.setDesconforto(dto.desconforto());
        if (dto.desconfortoDescricao() != null) entity.setDesconfortoDescricao(dto.desconfortoDescricao());
        if (dto.fumante() != null) entity.setFumante(dto.fumante());
        if (dto.proteses() != null) entity.setProteses(dto.proteses());
        if (dto.protesesDescricao() != null) entity.setProtesesDescricao(dto.protesesDescricao());
        if (dto.doencaMetabolica() != null) entity.setDoencaMetabolica(dto.doencaMetabolica());
        if (dto.doencaMetabolicaDescricao() != null) entity.setDoencaMetabolicaDescricao(dto.doencaMetabolicaDescricao());
        if (dto.deficiencia() != null) entity.setDeficiencia(dto.deficiencia());
        if (dto.deficienciaDescricao() != null) entity.setDeficienciaDescricao(dto.deficienciaDescricao());
    }

    public AnamneseResponsePatchDTO toDto(Anamnese entity) {
        return new AnamneseResponsePatchDTO(
                entity.getId(),
                alunoMapper.toResponseDTO(entity.getAluno()),
                entity.getObjetivoTreino(),
                entity.getLesao(),
                entity.getLesaoDescricao(),
                entity.getFrequenciaTreino(),
                entity.getExperiencia(),
                entity.getExperienciaDescricao(),
                entity.getDesconforto(),
                entity.getDesconfortoDescricao(),
                entity.getFumante(),
                entity.getProteses(),
                entity.getProtesesDescricao(),
                entity.getDoencaMetabolica(),
                entity.getDoencaMetabolicaDescricao(),
                entity.getDeficiencia(),
                entity.getDeficienciaDescricao()
        );
    }
}
