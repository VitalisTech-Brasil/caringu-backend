package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerBairro;

@Component
public class PersonalTrainerBairroMapper {

    public PersonalTrainerBairro toEntity(PersonalTrainerBairroRequestPostDTO dto, PersonalTrainer personal, Bairro bairro) {
        PersonalTrainerBairro entity = new PersonalTrainerBairro();
        entity.setPersonalTrainer(personal);
        entity.setBairro(bairro);
        return entity;
    }

    public PersonalTrainerBairroResponseGetDTO toResponseDTO(PersonalTrainerBairro entity) {
        return new PersonalTrainerBairroResponseGetDTO(
                entity.getId(),
                entity.getPersonalTrainer().getId(),
                entity.getPersonalTrainer().getNome(),
                entity.getBairro().getId(),
                entity.getBairro().getNome()
        );
    }
}
