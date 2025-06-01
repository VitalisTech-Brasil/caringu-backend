package tech.vitalis.caringu.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPatchDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerComBairroCidadeResponseGetDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.exception.Especialidade.EspecialidadeNaoEncontrada;
import tech.vitalis.caringu.repository.EspecialidadeRepository;

import java.util.List;

@Component
public class PersonalTrainerMapper {


    @Autowired
    private Environment env;

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
        List<EspecialidadeResponseGetDTO> especialidadesDTO  = personalTrainer.getEspecialidades().stream()
                .map(pte -> new EspecialidadeResponseGetDTO(
                        pte.getEspecialidade().getId(),
                        pte.getEspecialidade().getNome()
                ))

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
                especialidadesDTO,
                personalTrainer.getExperiencia(),
                personalTrainer.getDataCadastro()
        );
    }

    public PersonalTrainerComBairroCidadeResponseGetDTO toResponseDTO(PersonalTrainer personalTrainer, Bairro bairro, Cidade cidade) {
        List<EspecialidadeResponseGetDTO> especialidadesDTO  = personalTrainer.getEspecialidades().stream()
                .map(pte -> new EspecialidadeResponseGetDTO(
                        pte.getEspecialidade().getId(),
                        pte.getEspecialidade().getNome()
                ))

                .toList();

        String urlFoto = personalTrainer.getUrlFotoPerfil();
        if (urlFoto != null && !urlFoto.startsWith("http") && !env.acceptsProfiles(Profiles.of("prod"))) {
            urlFoto = "http://localhost:8080/pessoas/fotos-perfil/" + urlFoto;
        }


        return new PersonalTrainerComBairroCidadeResponseGetDTO(
                personalTrainer.getId(),
                personalTrainer.getNome(),
                personalTrainer.getEmail(),
                personalTrainer.getCelular(),
                urlFoto,
                personalTrainer.getDataNascimento(),
                personalTrainer.getGenero(),
                personalTrainer.getCref(),
                especialidadesDTO,
                personalTrainer.getExperiencia(),
                personalTrainer.getDataCadastro(),
                bairro.getId(),
                bairro.getNome(),
                cidade.getId(),
                cidade.getNome()
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
            List<PersonalTrainerEspecialidade> especialidades = responseDTO.especialidades().stream()
                    .map(dto -> {
                        Especialidade especialidade = especialidadeRepository.findById(dto.id())
                                .orElseThrow(() -> new EspecialidadeNaoEncontrada("Especialidade com ID " + dto.id() + " não encontrada"));
                        return new PersonalTrainerEspecialidade(pt, especialidade);
                    })
                    .toList();

            pt.setEspecialidades(especialidades);
        }

        return pt;
    }

}
