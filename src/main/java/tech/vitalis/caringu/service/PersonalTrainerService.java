package tech.vitalis.caringu.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPatchDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponsePatchDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.PersonalTrainer.CrefJaExisteException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PersonalTrainerService {

    private final PersonalTrainerRepository repository;
    private final PessoaRepository pessoaRepository;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonalTrainerRepository personalTrainerRepository;

    public PersonalTrainerService(PersonalTrainerRepository repository, PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, PersonalTrainerRepository personalTrainerRepository, PersonalTrainerMapper personalTrainerMapper) {
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
        this.personalTrainerRepository = personalTrainerRepository;
        this.personalTrainerMapper = personalTrainerMapper;
    }

    public List<PersonalTrainerResponseGetDTO> listar() {
        List<PersonalTrainer> listaPersonalTrainers = repository.findAll();
        List<PersonalTrainerResponseGetDTO> listaRespostaPersonalTrainer = new ArrayList<>();

        for (PersonalTrainer personalTrainer : listaPersonalTrainers) {
            PersonalTrainerResponseGetDTO respostaDTO = personalTrainerMapper.toResponseDTO(personalTrainer);
            listaRespostaPersonalTrainer.add(respostaDTO);
        }

        return listaRespostaPersonalTrainer;
    }

    public PersonalTrainerResponseGetDTO buscarPorId(Integer id) {
        PersonalTrainer personalTrainer = repository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + id));
        return personalTrainerMapper.toResponseDTO(personalTrainer);
    }

    public PersonalTrainerResponseGetDTO cadastrar(PersonalTrainer personalTrainer) {

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

        repository.save(personalTrainer);
        return personalTrainerMapper.toResponseDTO(personalTrainer);
    }

    public Boolean crefExiste(String cref) {
        return personalTrainerRepository.existsByCref(cref);
    }

    public PersonalTrainerResponseGetDTO atualizar(Integer id, PersonalTrainer novoPersonalTrainer) {
        PersonalTrainer personalTrainerExistente = repository.findById(id)
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
        personalTrainerExistente.setEspecialidade(novoPersonalTrainer.getEspecialidade());
        personalTrainerExistente.setExperiencia(novoPersonalTrainer.getExperiencia());

        repository.save(personalTrainerExistente);
        return personalTrainerMapper.toResponseDTO(personalTrainerExistente);
    }

    public PersonalTrainerResponsePatchDTO atualizarParcial(Integer id, PersonalTrainerRequestPatchDTO dto) {
        PersonalTrainer personalTrainer = repository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Aluno não encontrado com ID: " + id));

        Optional<String> nome = Optional.ofNullable(dto.nome());
        Optional<String> email = Optional.ofNullable(dto.email());
        Optional<String> celular = Optional.ofNullable(dto.celular());
        Optional<String> urlFotoPerfil = Optional.ofNullable(dto.urlFotoPerfil());
        Optional<LocalDate> dataNascimento = Optional.ofNullable(dto.dataNascimento());
        Optional<GeneroEnum> genero = Optional.ofNullable(dto.genero());

        Optional<String> cref = Optional.ofNullable(dto.cref());
        Optional<List<String>> especialidade = Optional.ofNullable(dto.especialidade());
        Optional<Integer> experiencia = Optional.ofNullable(dto.experiencia());

        nome.ifPresent(personalTrainer::setNome);
        email.ifPresent(personalTrainer::setEmail);
        celular.ifPresent(personalTrainer::setCelular);
        urlFotoPerfil.ifPresent(personalTrainer::setUrlFotoPerfil);
        dataNascimento.ifPresent(personalTrainer::setDataNascimento);
        genero.ifPresent(personalTrainer::setGenero);

        cref.ifPresent(personalTrainer::setCref);
        especialidade.ifPresent(personalTrainer::setEspecialidade);
        experiencia.ifPresent(personalTrainer::setExperiencia);

        repository.save(personalTrainer);

        return new PersonalTrainerResponsePatchDTO(
                nome,
                email,
                celular,
                urlFotoPerfil,
                dataNascimento,
                genero,
                cref,
                especialidade,
                experiencia
        );
    }

    public void deletar(Integer id) {
        PersonalTrainer personalTrainer = repository.findById(id)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Aluno não encontrado com ID: " + id));

        repository.delete(personalTrainer);
    }
}
