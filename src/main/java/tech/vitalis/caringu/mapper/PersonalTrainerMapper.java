package tech.vitalis.caringu.mapper;

import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;

public class PersonalTrainerMapper {

    public static PersonalTrainer toEntity(PersonalTrainerRequestPostDTO requestDTO) {
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

    public static PersonalTrainerResponseGetDTO toResponseDTO(PersonalTrainer personalTrainer) {

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


}
