package tech.vitalis.caringu.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.AlunoRequestPatchDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponsePatchDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.strategy.Aluno.*;
import tech.vitalis.caringu.strategy.Pessoa.GeneroEnumValidationStrategy;
import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    public AlunoService(AlunoRepository alunoRepository, PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.alunoRepository = alunoRepository;
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AlunoResponseGetDTO> listar() {
        List<Aluno> listaAlunos = alunoRepository.findAll();
        List<AlunoResponseGetDTO> listaRespostaAlunos = new ArrayList<>();

        for (Aluno aluno : listaAlunos) {
            AlunoResponseGetDTO respostaDTO = AlunoMapper.toResponseDTO(aluno);
            listaRespostaAlunos.add(respostaDTO);
        }

        return listaRespostaAlunos;
    }

    public AlunoResponseGetDTO buscarPorId(Integer id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + id));
        return AlunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseGetDTO cadastrar(Aluno aluno) {

        validarEnums(Map.of(
            new GeneroEnumValidationStrategy(), aluno.getGenero(),
            new NivelAtividadeEnumValidationStrategy(), aluno.getNivelAtividade(),
            new NivelExperienciaEnumValidationStrategy(), aluno.getNivelExperiencia()
        ));

        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

        if (!Pattern.matches(regex, aluno.getSenha())) {
            throw new SenhaInvalidaException("A senha incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
        }

        if (pessoaRepository.existsByEmail(aluno.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já existe!");
        }

        String senhaCriptografada = passwordEncoder.encode(aluno.getSenha());
        aluno.setSenha(senhaCriptografada);

        alunoRepository.save(aluno);
        return AlunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseGetDTO atualizar(Integer id, Aluno novoAluno) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + id));

        if (novoAluno.getSenha() != null) {
            String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";
            if (!Pattern.matches(regex, novoAluno.getSenha())) {
                throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial.");
            }
        }

        // Fazer um mapper para isso depois:

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
        return AlunoMapper.toResponseDTO(alunoExistente);
    }

    public AlunoResponsePatchDTO atualizarParcial(Integer id, AlunoRequestPatchDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + id));

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
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + id));

        alunoRepository.delete(aluno);
    }
}
