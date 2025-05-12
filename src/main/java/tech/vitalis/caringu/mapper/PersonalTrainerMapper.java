package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPatchDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerEspecialidade;
import tech.vitalis.caringu.exception.Especialidade.EspecialidadeNaoEncontrada;
import tech.vitalis.caringu.repository.EspecialidadeRepository;

import java.util.List;

@Component
public class PersonalTrainerMapper {

    private final EspecialidadeRepository especialidadeRepository;

    public PersonalTrainerMapper(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public PersonalTrainer toEntity(PersonalTrainerRequestPostDTO dto) {
        PersonalTrainer pt = new PersonalTrainer();

        pt.setNome(dto.nome());
        pt.setEmail(dto.email());
        pt.setSenha(dto.senha());
        pt.setCelular(dto.celular());
        pt.setUrlFotoPerfil(dto.urlFotoPerfil());
        pt.setDataNascimento(dto.dataNascimento());
        pt.setGenero(dto.genero());
        pt.setCref(dto.cref());
        pt.setExperiencia(dto.experiencia());

        pt.setEspecialidades(
                dto.especialidadesIds().stream()
                        .map(id -> {
                            Especialidade especialidade = especialidadeRepository.findById(id)
                                    .orElseThrow(() -> new EspecialidadeNaoEncontrada("Especialidade ID " + id + " não encontrada"));
                            return new PersonalTrainerEspecialidade(pt, especialidade);
                        })
                        .toList()
        );

        return pt;
    }

    public PersonalTrainer toEntity(PersonalTrainerRequestPatchDTO dto) {
        PersonalTrainer pt = new PersonalTrainer();

        pt.setNome(dto.nome());
        pt.setEmail(dto.email());
        pt.setCelular(dto.celular());
        pt.setUrlFotoPerfil(dto.urlFotoPerfil());
        pt.setDataNascimento(dto.dataNascimento());
        pt.setGenero(dto.genero());

        pt.setCref(dto.cref());
        pt.setExperiencia(dto.experiencia());

        if (dto.especialidadesIds() != null) {
            pt.setEspecialidades(
                    dto.especialidadesIds().stream()
                            .map(id -> {
                                Especialidade especialidade = especialidadeRepository.findById(id)
                                        .orElseThrow(() -> new EspecialidadeNaoEncontrada("Especialidade ID " + id + " não encontrada"));
                                return new PersonalTrainerEspecialidade(pt, especialidade);
                            })
                            .toList()
            );
        }

        return pt;
    }

    public PersonalTrainerResponseGetDTO toResponseDTO(PersonalTrainer personalTrainer) {
        List<String> especialidadesIds = personalTrainer.getEspecialidades().stream()
                .map(pte -> pte.getEspecialidade().getNome())
                .toList();

        return new PersonalTrainerResponseGetDTO(
                personalTrainer.getId(),
                personalTrainer.getNome(),
                personalTrainer.getEmail(),
                personalTrainer.getCelular(),
                personalTrainer.getUrlFotoPerfil(),
                personalTrainer.getDataNascimento(),
                personalTrainer.getGenero(),
                personalTrainer.getCref(),
                especialidadesIds,
                personalTrainer.getExperiencia(),
                personalTrainer.getDataCadastro()
        );
    }

    public PersonalTrainer responseToEntity(PersonalTrainerResponseGetDTO responseDTO) {
        PersonalTrainer pt = new PersonalTrainer();

        pt.setId(responseDTO.id());
        pt.setNome(responseDTO.nome());
        pt.setEmail(responseDTO.email());
        pt.setCelular(responseDTO.celular());
        pt.setUrlFotoPerfil(responseDTO.urlFotoPerfil());
        pt.setDataNascimento(responseDTO.dataNascimento());
        pt.setGenero(responseDTO.genero());

        pt.setCref(responseDTO.cref());
        pt.setExperiencia(responseDTO.experiencia());

        if (responseDTO.especialidades() != null) {
            pt.setEspecialidades(
                    responseDTO.especialidades().stream()
                            .map(id -> {
                                Especialidade especialidade = especialidadeRepository.findById(Integer.valueOf(id))
                                        .orElseThrow(() -> new EspecialidadeNaoEncontrada("Especialidade ID " + id + " não encontrada"));
                                return new PersonalTrainerEspecialidade(pt, especialidade);
                            })
                            .toList()
            );
        }

        return pt;
    }

}
