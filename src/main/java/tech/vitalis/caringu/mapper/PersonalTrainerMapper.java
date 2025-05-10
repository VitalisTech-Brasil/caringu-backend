package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPatchDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

@Component
public class PersonalTrainerMapper {

    public PersonalTrainer toEntity(PersonalTrainerRequestPostDTO requestDTO) {
        PersonalTrainer personalTrainer = new PersonalTrainer();

        personalTrainer.setNome(requestDTO.nome());
        personalTrainer.setEmail(requestDTO.email());
        personalTrainer.setSenha(requestDTO.senha());
        personalTrainer.setCelular(requestDTO.celular());
        personalTrainer.setUrlFotoPerfil(requestDTO.urlFotoPerfil());
        personalTrainer.setDataNascimento(requestDTO.dataNascimento());
        personalTrainer.setGenero(requestDTO.genero());

        personalTrainer.setCref(requestDTO.cref());
        personalTrainer.setEspecialidade(requestDTO.especialidade());
        personalTrainer.setExperiencia(requestDTO.experiencia());

        return personalTrainer;
    }

    public PersonalTrainer toEntity(PersonalTrainerRequestPatchDTO dto) {
        PersonalTrainer personalTrainer = new PersonalTrainer();

        personalTrainer.setNome(dto.nome());
        personalTrainer.setEmail(dto.email());
        personalTrainer.setCelular(dto.celular());
        personalTrainer.setUrlFotoPerfil(dto.urlFotoPerfil());
        personalTrainer.setDataNascimento(dto.dataNascimento());
        personalTrainer.setGenero(dto.genero());

        personalTrainer.setCref(dto.cref());
        personalTrainer.setEspecialidade(dto.especialidade());
        personalTrainer.setExperiencia(dto.experiencia());

        return personalTrainer;
    }

    public PersonalTrainerResponseGetDTO toResponseDTO(PersonalTrainer personalTrainer) {
        return new PersonalTrainerResponseGetDTO(
                personalTrainer.getId(),
                personalTrainer.getNome(),
                personalTrainer.getEmail(),
                personalTrainer.getCelular(),
                personalTrainer.getUrlFotoPerfil(),
                personalTrainer.getDataNascimento(),
                personalTrainer.getGenero(),
                personalTrainer.getCref(),
                personalTrainer.getEspecialidade(),
                personalTrainer.getExperiencia(),
                personalTrainer.getDataCadastro()
        );
    }

    public PersonalTrainer responseToEntity(PersonalTrainerResponseGetDTO responseDTO) {
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(responseDTO.id());
        personalTrainer.setNome(responseDTO.nome());
        personalTrainer.setEmail(responseDTO.email());
        personalTrainer.setCelular(responseDTO.celular());
        personalTrainer.setUrlFotoPerfil(responseDTO.urlFotoPerfil());
        personalTrainer.setDataNascimento(responseDTO.dataNascimento());
        personalTrainer.setGenero(responseDTO.genero());

        personalTrainer.setCref(responseDTO.cref());
        personalTrainer.setEspecialidade(responseDTO.especialidade());
        personalTrainer.setExperiencia(responseDTO.experiencia());

        return personalTrainer;
    }

}
