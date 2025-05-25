package tech.vitalis.caringu.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PersonalTrainer.*;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerEspecialidade;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.PersonalTrainer.CrefJaExisteException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.repository.EspecialidadeRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.strategy.Pessoa.GeneroEnumValidationStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class PersonalTrainerService {

    private final PasswordEncoder passwordEncoder;
    private final PessoaRepository pessoaRepository;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final PersonalTrainerRepository personalTrainerRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final PreferenciaNotificacaoService preferenciaNotificacaoService;

    public PersonalTrainerService(PasswordEncoder passwordEncoder, PessoaRepository pessoaRepository,
                                  PersonalTrainerMapper personalTrainerMapper,
                                  PersonalTrainerRepository personalTrainerRepository,
                                  EspecialidadeRepository especialidadeRepository,
                                  PreferenciaNotificacaoService preferenciaNotificacaoService) {
        this.passwordEncoder = passwordEncoder;
        this.pessoaRepository = pessoaRepository;
        this.personalTrainerMapper = personalTrainerMapper;
        this.personalTrainerRepository = personalTrainerRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.preferenciaNotificacaoService = preferenciaNotificacaoService;
    }

    public List<PersonalTrainerResponseGetDTO> listar() {
        List<PersonalTrainer> listaPersonalTrainers = personalTrainerRepository.findAll();
        List<PersonalTrainerResponseGetDTO> listaRespostaPersonalTrainer = new ArrayList<>();

        for (PersonalTrainer personalTrainer : listaPersonalTrainers) {
            PersonalTrainerResponseGetDTO respostaDTO = personalTrainerMapper.toResponseDTO(personalTrainer);
            listaRespostaPersonalTrainer.add(respostaDTO);
        }

        return listaRespostaPersonalTrainer;
    }

    public PersonalTrainerResponseGetDTO buscarPorId(Integer id) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + id));
        return personalTrainerMapper.toResponseDTO(personalTrainer);
    }

    public List<PersonalTrainerDisponivelResponseDTO> listarPersonaisDisponiveis() {
        List<PersonalTrainerInfoBasicaDTO> basicos = personalTrainerRepository.buscarBasicos();

        List<Integer> ids = basicos.stream()
                .map(PersonalTrainerInfoBasicaDTO::id)
                .toList();

        // Mapeia as especialidades por ID
        Map<Integer, List<String>> especialidadesPorPersonal = especialidadeRepository.buscarNomesPorPersonalIds(ids)
                .stream()
                .collect(Collectors.groupingBy(
                        obj -> (Integer) obj[0],
                        Collectors.mapping(obj -> (String) obj[1], Collectors.toList())
                ));

        return basicos.stream()
                .map(p -> new PersonalTrainerDisponivelResponseDTO(
                        p.id(),
                        p.nomePersonal(),
                        p.email(),
                        p.celular(),
                        p.experiencia(),
                        especialidadesPorPersonal.getOrDefault(p.id(), List.of()),
                        p.bairro(),
                        p.cidade()
                ))
                .toList();
    }

    public PersonalTrainerResponseGetDTO cadastrar(PersonalTrainer personalTrainer) {

        validarEnums(Map.of(
                new GeneroEnumValidationStrategy(), personalTrainer.getGenero()
        ));

        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

        if (!Pattern.matches(regex, personalTrainer.getSenha())) {
            throw new SenhaInvalidaException("A senha deve incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
        }

        if (pessoaRepository.existsByEmail(personalTrainer.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já existe!");
        }

        if (personalTrainerRepository.existsByCref(personalTrainer.getCref())) {
            throw new CrefJaExisteException("Este CREF já está cadastrado!");
        }

        String senhaCriptografada = passwordEncoder.encode(personalTrainer.getSenha());
        personalTrainer.setSenha(senhaCriptografada);

        if (personalTrainer.getEspecialidades() != null && !personalTrainer.getEspecialidades().isEmpty()) {
            List<PersonalTrainerEspecialidade> especialidadesAssociadas = new ArrayList<>();
            for (PersonalTrainerEspecialidade especialidadePersonalTrainer : personalTrainer.getEspecialidades()) {

                Especialidade especialidade = especialidadeRepository.findById(especialidadePersonalTrainer.getEspecialidade().getId())
                        .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

                PersonalTrainerEspecialidade novaEspecialidade = new PersonalTrainerEspecialidade();
                novaEspecialidade.setPersonalTrainer(personalTrainer);
                novaEspecialidade.setEspecialidade(especialidade);

                especialidadesAssociadas.add(novaEspecialidade);
            }

            personalTrainer.setEspecialidades(especialidadesAssociadas);
        }

        PersonalTrainer salvo = personalTrainerRepository.save(personalTrainer);
        preferenciaNotificacaoService.criarPreferenciasPadrao(salvo);

        return personalTrainerMapper.toResponseDTO(salvo);
    }

    public Boolean crefExiste(String cref) {
        return personalTrainerRepository.existsByCref(cref);
    }

    public PersonalTrainerResponseGetDTO atualizar(Integer id, PersonalTrainer novoPersonalTrainer) {
        PersonalTrainer personalTrainerExistente = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Aluno não encontrado com ID: " + id));

        if (novoPersonalTrainer.getSenha() != null) {
            String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";
            if (!Pattern.matches(regex, novoPersonalTrainer.getSenha())) {
                throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial.");
            }
        }

        // Fazer um mapper para isso depois:

        personalTrainerExistente.setNome(novoPersonalTrainer.getNome());
        personalTrainerExistente.setEmail(novoPersonalTrainer.getEmail());
        personalTrainerExistente.setSenha(novoPersonalTrainer.getSenha());
        personalTrainerExistente.setCelular(novoPersonalTrainer.getCelular());
        personalTrainerExistente.setDataNascimento(novoPersonalTrainer.getDataNascimento());
        personalTrainerExistente.setGenero(novoPersonalTrainer.getGenero());
        personalTrainerExistente.setCref(novoPersonalTrainer.getCref());

        if (novoPersonalTrainer.getEspecialidades() != null) {
            personalTrainerExistente.getEspecialidades().clear();
            personalTrainerExistente.getEspecialidades().addAll(
                    novoPersonalTrainer.getEspecialidades()
            );
        }

        personalTrainerExistente.setExperiencia(novoPersonalTrainer.getExperiencia());

        personalTrainerRepository.save(personalTrainerExistente);
        return personalTrainerMapper.toResponseDTO(personalTrainerExistente);
    }

    public PersonalTrainerResponsePatchDTO atualizarParcial(Integer id, PersonalTrainerRequestPatchDTO dto) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + id));

        Optional<String> nome = Optional.ofNullable(dto.nome());
        Optional<String> email = Optional.ofNullable(dto.email());
        Optional<String> celular = Optional.ofNullable(dto.celular());
        Optional<String> urlFotoPerfil = Optional.ofNullable(dto.urlFotoPerfil());
        Optional<LocalDate> dataNascimento = Optional.ofNullable(dto.dataNascimento());
        Optional<GeneroEnum> genero = Optional.ofNullable(dto.genero());

        Optional<String> cref = Optional.ofNullable(dto.cref());
        Optional<List<Integer>> especialidadesIds = Optional.ofNullable(dto.especialidadesIds());
        Optional<Integer> experiencia = Optional.ofNullable(dto.experiencia());

        nome.ifPresent(personalTrainer::setNome);
        email.ifPresent(personalTrainer::setEmail);
        celular.ifPresent(personalTrainer::setCelular);
        urlFotoPerfil.ifPresent(personalTrainer::setUrlFotoPerfil);
        dataNascimento.ifPresent(personalTrainer::setDataNascimento);
        genero.ifPresent(personalTrainer::setGenero);

        cref.ifPresent(personalTrainer::setCref);
        Optional<List<String>> especialidadesNomes = especialidadesIds.map(ids ->
                ids.stream()
                        .map(idEspec -> especialidadeRepository.findById(idEspec)
                                .map(Especialidade::getNome)
                                .orElse("Desconhecida"))
                        .toList()
        );
        experiencia.ifPresent(personalTrainer::setExperiencia);

        personalTrainerRepository.save(personalTrainer);

        return new PersonalTrainerResponsePatchDTO(
                nome,
                email,
                celular,
                urlFotoPerfil,
                dataNascimento,
                genero,
                cref,
                especialidadesNomes,
                experiencia
        );
    }

    public void deletar(Integer id) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Aluno não encontrado com ID: " + id));

        personalTrainerRepository.delete(personalTrainer);
    }

    public void removerEspecialidade(Integer idPersonal, Integer idEspecialidade) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(idPersonal)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado"));

        boolean removido = personalTrainer.getEspecialidades()
                .removeIf(pe -> pe.getEspecialidade().getId().equals(idEspecialidade));

        if (!removido) {
            throw new RuntimeException("Especialidade com ID " + idEspecialidade + " não encontrada para este personal.");
        }

        personalTrainerRepository.save(personalTrainer);
    }
}
