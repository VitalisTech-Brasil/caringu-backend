package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPatchDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPostDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesResponseGetDto;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.mapper.NotificacoesMapper;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.strategy.Notificacoes.NotificacoesEnumValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class NotificacoesService {

    private final NotificacoesRepository notificacoesRepository;
    private final NotificacoesMapper notificacoesMapper;
    private final PessoaMapper pessoaMapper;
    private final PessoaRepository pessoaRepository;

    public NotificacoesService(NotificacoesRepository notificacoesRepository, NotificacoesMapper notificacoesMapper, PessoaMapper pessoaMapper, PessoaRepository pessoaRepository) {
        this.notificacoesRepository = notificacoesRepository;
        this.notificacoesMapper = notificacoesMapper;
        this.pessoaMapper = pessoaMapper;
        this.pessoaRepository = pessoaRepository;
    }

    public NotificacoesResponseGetDto cadastrar(NotificacoesRequestPostDto notificacoesDto){
        validarEnums(Map.of(
                new NotificacoesEnumValidator(), notificacoesDto.tipo()
        ));

        Pessoa pessoa = pessoaRepository.findById(notificacoesDto.pessoaId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Pessoa com o ID" + notificacoesDto.pessoaId() + "não encontrado."));

        Notificacoes novaNotificacao = notificacoesMapper.toEntity(notificacoesDto);
        novaNotificacao.setPessoa(pessoa);

        Notificacoes notificacoesSalvo = notificacoesRepository.save(novaNotificacao);

        return notificacoesMapper.toResponseDTO(notificacoesSalvo);
    }

    public NotificacoesResponseGetDto buscarPorId(Integer id){
        Notificacoes notificacoes = notificacoesRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Notificação com ID" + id + "não encontrado"));

        return notificacoesMapper.toResponseDTO(notificacoes);
    }

    public List<NotificacoesResponseGetDto> buscarPorPessoaId(Integer pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ApiExceptions.BadRequestException(
                        "Pessoa com o ID " + pessoaId + " não foi encontrada."
                ));

        List<Notificacoes> notificacoes = notificacoesRepository.findByPessoa_Id(pessoaId);

        if (notificacoes.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException(
                    "Nenhuma notificação encontrada para a pessoa com ID " + pessoaId
            );
        }

        return notificacoes.stream()
                .map(notificacoesMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<NotificacoesResponseGetDto> listarTodos(){
        return notificacoesRepository.findAll()
                .stream()
                .map(notificacoesMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public NotificacoesResponseGetDto atualizar(Integer id, NotificacoesRequestPatchDto notificacoesDto){
        validarEnums(Map.of(
                new NotificacoesEnumValidator(), notificacoesDto.tipo()
        ));

        Notificacoes notificacoesExistente = notificacoesRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Notificação com ID" + id + "não encontrado"));

        notificacoesExistente.setTipo(notificacoesDto.tipo());
        notificacoesExistente.setTitulo(notificacoesDto.titulo());
        notificacoesExistente.setVisualizada(notificacoesDto.visualizada());
        notificacoesExistente.setDataCriacao(notificacoesDto.dataCriacao());

        Notificacoes notificacoesAtualizada = notificacoesRepository.save(notificacoesExistente);
        return notificacoesMapper.toResponseDTO(notificacoesAtualizada);
    }

    public void remover(Integer id){
        Notificacoes notificacoesExistente = notificacoesRepository.findById(id).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Notificação com ID" + id + "não encontrado"));

        notificacoesRepository.deleteById(id);
    }

    public void removerAssociacaoComPessoa(Integer id){
        Notificacoes notificacoesExistente = notificacoesRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Notificação com ID" + id + "não encontrado"));

        notificacoesExistente.setPessoa(null);
        notificacoesRepository.save(notificacoesExistente);
    }

    @Transactional
    public void atualizarVisualizada(Integer id, boolean visualizada){
        Notificacoes notificacoesVisualizada = notificacoesRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Notificação não encontrada"));

        notificacoesVisualizada.setVisualizada(visualizada);
    }

    public List<NotificacoesResponseGetDto> buscarPorPessoaIdENaoVisualzaTreinoVencimento(Integer pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ApiExceptions.BadRequestException(
                        "Pessoa com o ID " + pessoaId + " não foi encontrada."
                ));

        List<Notificacoes> notificacoes = notificacoesRepository
                .findByPessoaAndTipoAndVisualizadaFalse(pessoa, TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);

        if (notificacoes.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException(
                    "Nenhuma notificação não visualizada encontrada para a pessoa com ID " + pessoaId
            );
        }

        return notificacoes.stream()
                .map(notificacoesMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<NotificacoesResponseGetDto> buscarPorPessoaIdENaoVisualza(Integer pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ApiExceptions.BadRequestException(
                        "Pessoa com o ID " + pessoaId + " não foi encontrada."
                ));

        List<Notificacoes> notificacoes = notificacoesRepository
                .findByPessoaAndVisualizadaFalse(pessoa);

        if (notificacoes.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException(
                    "Nenhuma notificação não visualizada encontrada para a pessoa com ID " + pessoaId
            );
        }

        return notificacoes.stream()
                .map(notificacoesMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void marcarTodasComoVisualizadasPorPessoaId(Integer pessoaId){
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ApiExceptions.BadRequestException(
                        "Pessoa com o ID " + pessoaId + " não foi encontrada."
                ));

        List<Notificacoes> notificacoes = notificacoesRepository.findByPessoaAndVisualizadaFalse(pessoa);

        if (notificacoes.isEmpty()){
            throw new ApiExceptions.ResourceNotFoundException(
                    "Nenhuma notificação não visualizada encontrada para a pessoa com ID " + pessoaId
            );
        }

        for (Notificacoes notificacao : notificacoes){
            notificacao.setVisualizada(true);
        }

        notificacoesRepository.saveAll(notificacoes);
    }
}
