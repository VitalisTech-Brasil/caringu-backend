package tech.vitalis.caringu.service;

import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PersonalTrainer.*;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.AtualizarBairroDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.CriarBairroDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerComBairroCidadeResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.Bairro.BairroNaoEncontradoException;
import tech.vitalis.caringu.exception.Cidade.CidadeNaoEncontradaException;
import tech.vitalis.caringu.exception.PersonalTrainer.CrefJaExisteException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.repository.*;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;
import tech.vitalis.caringu.strategy.Pessoa.GeneroEnumValidationStrategy;

import org.springframework.data.domain.Pageable;

import java.time.Duration;
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

    @Autowired
    private Environment env;
    private static final Logger logger = LoggerFactory.getLogger(PersonalTrainerService.class);

    private final PasswordEncoder passwordEncoder;
    private final PessoaRepository pessoaRepository;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final PersonalTrainerRepository personalTrainerRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final PreferenciaNotificacaoService preferenciaNotificacaoService;

    private final PersonalTrainerBairroRepository personalTrainerBairroRepository;
    private final BairroRepository bairroRepository;
    private final CidadeRepository cidadeRepository;
    private final PlanoRepository planoRepository;

    private final ArmazenamentoService armazenamentoInterface;

    public PersonalTrainerService(PasswordEncoder passwordEncoder, PessoaRepository pessoaRepository,
                                  PersonalTrainerMapper personalTrainerMapper, PersonalTrainerRepository personalTrainerRepository,
                                  EspecialidadeRepository especialidadeRepository, PreferenciaNotificacaoService preferenciaNotificacaoService,
                                  PersonalTrainerBairroRepository personalTrainerBairroRepository, BairroRepository bairroRepository,
                                  CidadeRepository cidadeRepository, PlanoRepository planoRepository,
                                  ArmazenamentoService armazenamentoInterface
    ) {
        this.passwordEncoder = passwordEncoder;
        this.pessoaRepository = pessoaRepository;
        this.personalTrainerMapper = personalTrainerMapper;
        this.personalTrainerRepository = personalTrainerRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.preferenciaNotificacaoService = preferenciaNotificacaoService;
        this.personalTrainerBairroRepository = personalTrainerBairroRepository;
        this.bairroRepository = bairroRepository;
        this.cidadeRepository = cidadeRepository;
        this.planoRepository = planoRepository;
        this.armazenamentoInterface = armazenamentoInterface;
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

    public Page<PersonalTrainerResponseGetDTO> listarPaginado(Pageable pageable){
        Page<PersonalTrainer> page = personalTrainerRepository.findAll(pageable);

        return page.map(personalTrainerMapper::toResponseDTO);
    }

    public PersonalTrainerResponseGetDTO buscarPorId(Integer id) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + id));

        return personalTrainerMapper.toResponseDTO(personalTrainer);
    }

    public PersonalTrainerComBairroCidadeResponseGetDTO buscarPersonalPorIdEBairroCidade(Integer id) {
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + id));

        Optional<PersonalTrainerBairro> personalBairroOptional =
                personalTrainerBairroRepository.findFirstByPersonalTrainerId(id);

        Bairro bairro = new Bairro();
        Cidade cidade = new Cidade();

        if (personalBairroOptional.isPresent()) {
            bairro = personalBairroOptional.get().getBairro();
            cidade = bairro.getCidade();
        }

        return personalTrainerMapper.toResponseDTO(personalTrainer, bairro, cidade);
    }

    public List<PersonalTrainerDisponivelResponseDTO> listarPersonaisDisponiveis() {
        List<PersonalTrainerInfoBasicaDTO> basicos = personalTrainerRepository.buscarBasicos();

        List<Integer> ids = basicos.stream()
                .map(PersonalTrainerInfoBasicaDTO::id)
                .toList();

        Map<Integer, List<String>> especialidadesPorPersonal = especialidadeRepository.buscarNomesPorPersonalIds(ids)
                .stream()
                .collect(Collectors.groupingBy(
                        obj -> (Integer) obj[0],
                        Collectors.mapping(obj -> (String) obj[1], Collectors.toList())
                ));

        List<PlanoResumoDTO> planos = planoRepository.findResumoByPersonalIds(ids);

        // Agrupa planos por personalId
        Map<Integer, List<PlanoResumoDTO>> planosPorPersonal = planos.stream()
                .collect(Collectors.groupingBy(PlanoResumoDTO::personalTrainerId));

        return basicos.stream()
                .map(p -> {

                    String urlFoto = p.urlFotoPerfil() != null
                            ? armazenamentoInterface.gerarUrlPreAssinada(p.urlFotoPerfil(), Duration.ofMinutes(5))
                            : null;

                    if (urlFoto != null && !urlFoto.startsWith("http") && !env.acceptsProfiles(Profiles.of("dev"))) {
                        urlFoto = "http://localhost:8080/pessoas/fotos-perfil/" + urlFoto;
                    }

                    // Arredondar média de estrelas para meio em meio (ex: 3.7 → 3.5, 3.8 → 4.0)
                    Double mediaEstrela = p.mediaEstrela() != null
                            ? Math.round(p.mediaEstrela() * 2) / 2.0
                            : 0.0;

                    return new PersonalTrainerDisponivelResponseDTO(
                            p.id(),
                            p.nomePersonal(),
                            p.email(),
                            p.celular(),
                            p.experiencia(),
                            urlFoto,
                            p.genero(),
                            especialidadesPorPersonal.getOrDefault(p.id(), List.of()),
                            planosPorPersonal.getOrDefault(p.id(), List.of()),
                            p.bairro(),
                            p.cidade(),
                            mediaEstrela,
                            p.quantidadeAvaliacao()
                    );
                })
                .toList();
    }

    public PersonalTrainerDisponivelResponseDTO buscarPersonalDisponivelPorId(Integer id) {
        // Buscar dados básicos do personal
        PersonalTrainerInfoBasicaDTO basico = personalTrainerRepository.buscarBasicoPorId(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal não encontrado"));

        // Buscar especialidades
        List<String> especialidades = especialidadeRepository.buscarNomesPorPersonalId(id);

        // Buscar planos
        List<PlanoResumoDTO> planos = planoRepository.findResumoByPersonalId(id);

        // Arredondar média de estrelas para meio em meio (ex: 3.7 → 3.5, 3.8 → 4.0)
        Double mediaEstrela = basico.mediaEstrela() != null
                ? Math.round(basico.mediaEstrela() * 2) / 2.0
                : 0.0;

        String urlFotoTemporaria = basico.urlFotoPerfil() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(basico.urlFotoPerfil(), Duration.ofMinutes(5))
                : null;

        // Montar e retornar DTO completo
        return new PersonalTrainerDisponivelResponseDTO(
                basico.id(),
                basico.nomePersonal(),
                basico.email(),
                basico.celular(),
                basico.experiencia(),
                urlFotoTemporaria,
                basico.genero(),
                especialidades,
                planos,
                basico.bairro(),
                basico.cidade(),
                mediaEstrela,
                basico.quantidadeAvaliacao()
        );
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

    @Transactional
    public void atualizarBairro(Integer personalId, AtualizarBairroDTO dto) {
        PersonalTrainer personal = personalTrainerRepository.findById(personalId)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal não encontrado"));

        Bairro bairro = bairroRepository.findById(dto.bairroId())
                .orElseThrow(() -> new BairroNaoEncontradoException("Bairro não encontrado"));

        String nomeAtualBairro = bairro.getNome();
        if (!nomeAtualBairro.equals(dto.novoNomeBairro())) {
            logger.info("Atualizando nome do bairro de '{}' para '{}'", nomeAtualBairro, dto.novoNomeBairro());
            bairro.setNome(dto.novoNomeBairro());
        }

        if (dto.cidadeId() != null && dto.novoNomeCidade() != null) {
            Cidade cidade = cidadeRepository.findById(dto.cidadeId())
                    .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrada"));

            String nomeAtualCidade = cidade.getNome();
            if (!nomeAtualCidade.equals(dto.novoNomeCidade())) {
                logger.info("Atualizando nome da cidade de '{}' para '{}'", nomeAtualCidade, dto.novoNomeCidade());
                cidade.setNome(dto.novoNomeCidade());
                cidadeRepository.save(cidade);
            }
        }

        bairroRepository.save(bairro);
    }

    @Transactional
    public void criarBairroEAssociar(Integer personalId, CriarBairroDTO dto) {
        PersonalTrainer personal = personalTrainerRepository.findById(personalId)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal não encontrado"));

        Cidade cidade;

        if (dto.cidadeId() != null) {
            cidade = cidadeRepository.findById(dto.cidadeId())
                    .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade não encontrada"));
        } else if (dto.nomeCidade() != null && !dto.nomeCidade().isBlank()) {
            cidade = new Cidade();
            cidade.setNome(dto.nomeCidade());
            cidade = cidadeRepository.save(cidade);
        } else {
            throw new IllegalArgumentException("É necessário informar o nome da cidade");
        }

        Bairro bairro = new Bairro();
        bairro.setNome(dto.nomeBairro());
        bairro.setCidade(cidade);
        bairro = bairroRepository.save(bairro);

        PersonalTrainerBairro associacao = new PersonalTrainerBairro();
        associacao.setPersonalTrainer(personal);
        associacao.setBairro(bairro);
        personalTrainerBairroRepository.save(associacao);
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
