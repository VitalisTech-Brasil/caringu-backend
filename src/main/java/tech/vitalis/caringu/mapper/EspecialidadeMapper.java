package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Especialidade;

@Component
public class EspecialidadeMapper {
    public EspecialidadeResponseGetDTO toDTO(Especialidade especialidade) {
        return new EspecialidadeResponseGetDTO(
                especialidade.getId(),
                especialidade.getNome()
        );
    }

    public Especialidade toEntity(EspecialidadeResponseGetDTO dto) {
        Especialidade especialidade = new Especialidade();
        especialidade.setId(dto.id());
        especialidade.setNome(dto.nome());
        return especialidade;
    }

}
