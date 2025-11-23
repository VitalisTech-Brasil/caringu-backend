package tech.vitalis.caringu.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.*;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerComBairroCidadeResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.exception.Especialidade.EspecialidadeNaoEncontrada;
import tech.vitalis.caringu.repository.EspecialidadeRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.Duration;
import java.util.List;

@Component
public class PersonalTrainerMapper {


    @Autowired
    private Environment env;

    private final ArmazenamentoService armazenamentoInterface;
    private final EspecialidadeRepository especialidadeRepository;

    public PersonalTrainerMapper(ArmazenamentoService armazenamentoInterface, EspecialidadeRepository especialidadeRepository) {
        this.armazenamentoInterface = armazenamentoInterface;
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
        List<EspecialidadeResponseGetDTO> especialidadesDTO = personalTrainer.getEspecialidades().stream()
                .map(pte -> new EspecialidadeResponseGetDTO(
                        pte.getEspecialidade().getId(),
                        pte.getEspecialidade().getNome()
                ))

                .toList();

        String urlFotoTemporaria = personalTrainer.getUrlFotoPerfil() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(personalTrainer.getUrlFotoPerfil(), Duration.ofMinutes(5))
                : null;

        return new PersonalTrainerResponseGetDTO(
                personalTrainer.getId(),
                personalTrainer.getNome(),
                personalTrainer.getEmail(),
                personalTrainer.getCelular(),
                urlFotoTemporaria,
                personalTrainer.getDataNascimento(),
                personalTrainer.getGenero(),
                personalTrainer.getCref(),
                especialidadesDTO,
                personalTrainer.getExperiencia(),
                personalTrainer.getDataCadastro()
        );
    }

    public PersonalTrainerComBairroCidadeResponseGetDTO toResponseDTO(PersonalTrainer personalTrainer, Bairro bairro, Cidade cidade) {
        List<EspecialidadeResponseGetDTO> especialidadesDTO = personalTrainer.getEspecialidades().stream()
                .map(pte -> new EspecialidadeResponseGetDTO(
                        pte.getEspecialidade().getId(),
                        pte.getEspecialidade().getNome()
                ))

                .toList();

        String urlFoto = personalTrainer.getUrlFotoPerfil();

        if (urlFoto != null && !urlFoto.startsWith("http")) {
            if (env.acceptsProfiles(Profiles.of("prod"))) {
                // produção: assume S3 e gera URL pré-assinada
                urlFoto = armazenamentoInterface.gerarUrlPreAssinada(urlFoto, Duration.ofMinutes(5));
            } else if (env.acceptsProfiles((Profiles.of("dev", "dev-with-redis"))) && !urlFoto.startsWith("https://lh3")) {
                // dev/local: monta URL para servir via endpoint local
                urlFoto = "http://localhost:8080/pessoas/fotos-perfil/" + urlFoto;
            }
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

    public PersonalTrainerDisponivelResponseDTO toResponse(
            PersonalTrainerInfoBasicaDTO basico,
            List<String> especialidades,
            List<PlanoResumoDTO> planos
    ) {
        String urlFoto = basico.urlFotoPerfil();

        if (urlFoto != null) {
            if (env.acceptsProfiles(Profiles.of("prod"))) {
                // produção: gera URL pré-assinada
                urlFoto = armazenamentoInterface.gerarUrlPreAssinada(urlFoto, Duration.ofMinutes(5));
            } else if (env.acceptsProfiles(Profiles.of("dev", "dev-with-redis")) && !urlFoto.startsWith("https://lh3")) {
                // dev/local: monta URL para servir via endpoint local
                urlFoto = "http://localhost:8080/pessoas/fotos-perfil/" + urlFoto;
            }
        }

        return new PersonalTrainerDisponivelResponseDTO(
                basico.id(),
                basico.nomePersonal(),
                basico.email(),
                basico.celular(),
                basico.experiencia(),
                urlFoto,
                basico.genero(),
                especialidades,
                planos,
                basico.bairro(),
                basico.cidade(),
                basico.mediaEstrela(),
                basico.quantidadeAvaliacao()
        );
    }

}
