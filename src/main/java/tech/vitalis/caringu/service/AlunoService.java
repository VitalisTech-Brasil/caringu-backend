package tech.vitalis.caringu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.strategy.Aluno.*;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;
import tech.vitalis.caringu.strategy.Pessoa.GeneroEnumValidationStrategy;
import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class AlunoService {

    private static final String REGEX_SENHA_FORTE = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

    private final PasswordEncoder passwordEncoder;
    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;
    private final PessoaRepository pessoaRepository;

    public AlunoService(PasswordEncoder passwordEncoder,
                        AlunoMapper alunoMapper,

                        AlunoRepository alunoRepository,
                        PessoaRepository pessoaRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.alunoMapper = alunoMapper;
        this.alunoRepository = alunoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<AlunoResponseGetDTO> listar() {
        List<Aluno> listaAlunos = alunoRepository.findAll();
        List<AlunoResponseGetDTO> listaRespostaAlunos = new ArrayList<>();

        for (Aluno aluno : listaAlunos) {
            AlunoResponseGetDTO respostaDTO = alunoMapper.toResponseDTO(aluno);
            listaRespostaAlunos.add(respostaDTO);
        }

        return listaRespostaAlunos;
    }

    public Page<AlunoResponseGetDTO> listarPaginado(Pageable pageable){
        Page<Aluno> page = alunoRepository.findAll(pageable);

        return page.map(alunoMapper::toResponseDTO);
    }

    public List<AlunoDetalhadoComTreinosDTO> buscarAlunosDetalhados(Integer idPersonal) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<AlunoDetalhadoResponseDTO> dadosBrutos = alunoRepository.buscarDetalhesPorPersonal(idPersonal, startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX));

        return alunoMapper.consolidarPorAluno(dadosBrutos);
    }

    public Page<AlunoDetalhadoComTreinosDTO> buscarAlunosDetalhadosPaginado(Integer idPersonal, Pageable pageable) {
        // buscar tudo, sem paginação para pegar todos os registros (ou um número grande)

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        List<AlunoDetalhadoResponseDTO> dadosBrutos = alunoRepository.buscarDetalhesPorPersonal(idPersonal, startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX));

        // consolidar agrupando treinos por aluno
        List<AlunoDetalhadoComTreinosDTO> listaConsolidada = alunoMapper.consolidarPorAluno(dadosBrutos);

        // paginar na lista consolidada em memória
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), listaConsolidada.size());

        List<AlunoDetalhadoComTreinosDTO> subList = listaConsolidada.subList(start, end);

        return new PageImpl<>(subList, pageable, listaConsolidada.size());
    }


    public AlunoResponseGetDTO buscarPorId(Integer id) {
        Aluno aluno = buscarAlunoOuLancarExcecao(id);
      
        return alunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseGetDTO cadastrar(Aluno aluno) {

        validarSenha(aluno.getSenha());
        validarEnumsAluno(aluno);

        if (pessoaRepository.existsByEmail(aluno.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já existe!");
        }

        String senhaCriptografada = passwordEncoder.encode(aluno.getSenha());
        aluno.setSenha(senhaCriptografada);

        alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseGetDTO atualizar(Integer alunoId, Aluno novoAluno) {
        Aluno alunoExistente = buscarAlunoOuLancarExcecao(alunoId);

        validarSenha(novoAluno.getSenha());

        alunoExistente.setNome(novoAluno.getNome());
        alunoExistente.setEmail(novoAluno.getEmail());
        alunoExistente.setSenha(novoAluno.getSenha());
        alunoExistente.setCelular(novoAluno.getCelular());
        alunoExistente.setDataNascimento(novoAluno.getDataNascimento());
        alunoExistente.setGenero(novoAluno.getGenero());
        alunoExistente.setPeso(novoAluno.getPeso());
        alunoExistente.setAltura(novoAluno.getAltura());
        alunoExistente.setNivelAtividade(novoAluno.getNivelAtividade());
        alunoExistente.setNivelExperiencia(novoAluno.getNivelExperiencia());

        alunoRepository.save(alunoExistente);
        return alunoMapper.toResponseDTO(alunoExistente);
    }

    public AlunoResponsePatchDadosFisicosDTO atualizarDadosFisicos(Integer alunoId, AlunoRequestPatchDadosFisicosDTO dto) {
        Aluno aluno = buscarAlunoOuLancarExcecao(alunoId);

        if (dto.peso() != null) {
            aluno.setPeso(dto.peso());
        }
        if (dto.altura() != null) {
            aluno.setAltura(dto.altura());
        }
        if (dto.nivelAtividade() != null) {
            aluno.setNivelAtividade(dto.nivelAtividade());
        }
        if (dto.nivelExperiencia() != null) {
            aluno.setNivelExperiencia(dto.nivelExperiencia());
        }

        alunoRepository.save(aluno);
        return alunoMapper.toResponseDadosFisicosDTO(aluno);
    }

    public AlunoResponsePatchDTO atualizarParcial(Integer alunoId, AlunoRequestPatchDTO dto) {
        Aluno aluno = buscarAlunoOuLancarExcecao(alunoId);

        Optional<String> nome = Optional.ofNullable(dto.nome());
        Optional<String> email = Optional.ofNullable(dto.email());
        Optional<String> celular = Optional.ofNullable(dto.celular());
        Optional<String> urlFotoPerfil = Optional.ofNullable(dto.urlFotoPerfil());
        Optional<LocalDate> dataNascimento = Optional.ofNullable(dto.dataNascimento());
        Optional<GeneroEnum> genero = Optional.ofNullable(dto.genero());

        Optional<Double> peso = Optional.ofNullable(dto.peso());
        Optional<Double> altura = Optional.ofNullable(dto.altura());
        Optional<NivelAtividadeEnum> nivelAtividade = Optional.ofNullable(dto.nivelAtividade());
        Optional<NivelExperienciaEnum> nivelExperiencia = Optional.ofNullable(dto.nivelExperiencia());

        nome.ifPresent(aluno::setNome);
        email.ifPresent(aluno::setEmail);
        celular.ifPresent(aluno::setCelular);
        urlFotoPerfil.ifPresent(aluno::setUrlFotoPerfil);
        dataNascimento.ifPresent(aluno::setDataNascimento);
        genero.ifPresent(aluno::setGenero);

        peso.ifPresent(aluno::setPeso);
        altura.ifPresent(aluno::setAltura);
        nivelAtividade.ifPresent(aluno::setNivelAtividade);
        nivelExperiencia.ifPresent(aluno::setNivelExperiencia);

        alunoRepository.save(aluno);

        return new AlunoResponsePatchDTO(
                nome,
                email,
                celular,
                urlFotoPerfil,
                dataNascimento,
                genero,
                peso,
                altura,
                nivelAtividade,
                nivelExperiencia
        );
    }

    public void deletar(Integer id) {
        Aluno aluno = buscarAlunoOuLancarExcecao(id);

        alunoRepository.delete(aluno);
    }

    private void validarSenha(String senha) {
        if (!Pattern.matches(REGEX_SENHA_FORTE, senha)) {
            throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial.");
        }
    }

    private void validarEnumsAluno(Aluno aluno) {
        Map<EnumValidationStrategy, Enum<?>> enums = new HashMap<>();
        enums.put(new GeneroEnumValidationStrategy(), aluno.getGenero());

        if (aluno.getNivelAtividade() != null) {
            enums.put(new NivelAtividadeEnumValidationStrategy(), aluno.getNivelAtividade());
        }
        if (aluno.getNivelExperiencia() != null) {
            enums.put(new NivelExperienciaEnumValidationStrategy(), aluno.getNivelExperiencia());
        }

        validarEnums(enums);
    }

    public Aluno buscarAlunoOuLancarExcecao(Integer alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + alunoId));
    }

    public AlunoResponseGetDTO buscarAlunoPorEmail(String email) {
        Pessoa pessoa = pessoaRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Aluno não encontrado com EMAIL: " + email));

        if (pessoa instanceof Aluno aluno) {
            return alunoMapper.toResponseDTO(aluno);
        }

        throw new ApiExceptions.ResourceNotFoundException("Pessoa encontrada com EMAIL: " + email + " não é um aluno.");
    }

    public List<AlunoResponseGetDTO> buscarAlunosPorEmail(String email) {
        List<Pessoa> pessoas = pessoaRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Aluno não encontrado com EMAIL: " + email));
        List<AlunoResponseGetDTO> listaRespostaAlunos = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Aluno aluno) {
                AlunoResponseGetDTO alunoDto = alunoMapper.toResponseDTO(aluno);
                listaRespostaAlunos.add(alunoDto);
            }
        }

        return listaRespostaAlunos;
    }
}
