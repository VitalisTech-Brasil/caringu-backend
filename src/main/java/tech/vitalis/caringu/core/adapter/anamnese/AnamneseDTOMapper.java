package tech.vitalis.caringu.core.adapter.anamnese;

import tech.vitalis.caringu.core.domain.entity.Anamnese;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.infrastructure.web.anamnese.CreateAnamneseRequest;
import tech.vitalis.caringu.infrastructure.web.anamnese.AnamneseResponse;
import tech.vitalis.caringu.infrastructure.web.anamnese.PatchAnamneseRequest;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;

public class AnamneseDTOMapper {

    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;

    public AnamneseDTOMapper(AlunoMapper alunoMapper, AlunoRepository alunoRepository) {
        this.alunoMapper = alunoMapper;
        this.alunoRepository = alunoRepository;
    }

    public AnamneseResponse toResponse(Anamnese anamnese){
        return new AnamneseResponse(
                anamnese.id(),
                alunoMapper.toResponseDTO(anamnese.aluno()),
                anamnese.objetivoTreino(),
                anamnese.lesao(),
                anamnese.lesaoDescricao(),
                anamnese.frequenciaTreino(),
                anamnese.experiencia(),
                anamnese.experienciaDescricao(),
                anamnese.desconforto(),
                anamnese.desconfortoDescricao(),
                anamnese.fumante(),
                anamnese.proteses(),
                anamnese.protesesDescricao(),
                anamnese.doencaMetabolica(),
                anamnese.doencaMetabolicaDescricao(),
                anamnese.deficiencia(),
                anamnese.deficienciaDescricao()
        );
    }

    public Anamnese toEntity(CreateAnamneseRequest request){
        Aluno aluno = alunoRepository.findById(request.aluno())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + request.aluno()));

        return new Anamnese(
                null,
                aluno,
                request.objetivoTreino(),
                request.lesao(),
                request.lesaoDescricao(),
                request.frequenciaTreino(),
                request.experiencia(),
                request.experienciaDescricao(),
                request.desconforto(),
                request.desconfortoDescricao(),
                request.fumante(),
                request.proteses(),
                request.protesesDescricao(),
                request.doencaMetabolica(),
                request.doencaMetabolicaDescricao(),
                request.deficiencia(),
                request.deficienciaDescricao()
        );
    }

    public Anamnese toAnamneseFromPatch(PatchAnamneseRequest request){
        return new Anamnese(
                null,
                null,
                request.objetivoTreino(),
                request.lesao(),
                request.lesaoDescricao(),
                request.frequenciaTreino(),
                request.experiencia(),
                request.experienciaDescricao(),
                request.desconforto(),
                request.desconfortoDescricao(),
                request.fumante(),
                request.proteses(),
                request.protesesDescricao(),
                request.doencaMetabolica(),
                request.doencaMetabolicaDescricao(),
                request.deficiencia(),
                request.deficienciaDescricao()
        );
    }
}
