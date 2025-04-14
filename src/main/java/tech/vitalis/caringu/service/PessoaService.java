package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Pessoa.RespostaPessoaDTO;
import tech.vitalis.caringu.dtos.Pessoa.CriacaoPessoaDTO;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    public RespostaPessoaDTO cadastrar(CriacaoPessoaDTO pessoaDto) {
        if (pessoaRepository.existsByEmail(pessoaDto.getEmail())) {
            throw new ApiExceptions.ConflictException("O e-mail já está cadastrado.");
        }

        validarSenha(pessoaDto.getSenha());

        Pessoa novoPessoa = pessoaMapper.toEntity(pessoaDto);
        Pessoa pessoaSalvo = pessoaRepository.save(novoPessoa);
        return pessoaMapper.toDTO(pessoaSalvo);
    }

    public List<RespostaPessoaDTO> listarTodos() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        if (pessoas.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return pessoas.stream()
                .map(pessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RespostaPessoaDTO buscarPorId(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return pessoaMapper.toDTO(pessoa);
    }

    public RespostaPessoaDTO atualizar(Integer id, CriacaoPessoaDTO pessoaDto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        pessoaMapper.updatePessoaFromDto(pessoaDto, pessoaExistente);
        Pessoa pessoaAtualizado = pessoaRepository.save(pessoaExistente);
        return pessoaMapper.toDTO(pessoaAtualizado);
    }

    public RespostaPessoaDTO editarInfoPessoa(Integer id, CriacaoPessoaDTO pessoaDto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        boolean atualizado = false;

        if (pessoaDto.getNome() != null && !pessoaDto.getNome().isEmpty()) {
            pessoaExistente.setNome(pessoaDto.getNome());
            atualizado = true;
        }
        if (pessoaDto.getEmail() != null && !pessoaDto.getEmail().isEmpty()) {
            pessoaExistente.setEmail(pessoaDto.getEmail());
            atualizado = true;
        }
        if (pessoaDto.getSenha() != null && !pessoaDto.getSenha().isEmpty()) {
            validarSenha(pessoaDto.getSenha());
            pessoaExistente.setSenha(pessoaDto.getSenha());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Pessoa pessoaAtualizado = pessoaRepository.save(pessoaExistente);
        return pessoaMapper.toDTO(pessoaAtualizado);
    }

    public void removerPessoa(Integer id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        pessoaRepository.deleteById(id);
    }

    private void validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\])$";

        if (!Pattern.matches(regex, senha)) {
            throw new ApiExceptions.BadRequestException("A senha incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
        }
    }
}
