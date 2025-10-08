package tech.vitalis.caringu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoExercicio.ExerciciosPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioEditResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.*;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Treino.TreinoNaoEncontradoException;
import tech.vitalis.caringu.exception.generics.IdsRequisicaoIncompativeisException;
import tech.vitalis.caringu.mapper.TreinoExercicioMapper;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoRepository;
import tech.vitalis.caringu.strategy.TreinoExercio.GrauDificuldadeEnumValidator;
import tech.vitalis.caringu.strategy.TreinoExercio.OrigemTreinoExercicioEnumValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class TreinoExercicioService {

    private final TreinoExercicioRepository treinoExercicioRepository;
    private final TreinoExercicioMapper treinoExercicioMapper;
    private final TreinoRepository treinoRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoExercicioService(
            TreinoExercicioRepository treinoExercicioRepository,
            TreinoExercicioMapper treinoExercicioMapper,
            TreinoRepository treinoRepository, ExercicioRepository exercicioRepository
    ) {
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.treinoExercicioMapper = treinoExercicioMapper;
        this.treinoRepository = treinoRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public List<TreinoExercicioResumoDTO> listarPorPersonal(Integer personalId) {
        List<TreinoExercicioResumoModeloCruQuerySqlDTO> listaComValoresNaoTratados = treinoExercicioRepository.buscarTreinosExerciciosPorPersonal(personalId);

        Map<Integer, List<TreinoExercicioResumoModeloCruQuerySqlDTO>> agrupadoPorTreinoId = listaComValoresNaoTratados.stream()
                .collect(Collectors.groupingBy(TreinoExercicioResumoModeloCruQuerySqlDTO::treinoId));

        return agrupadoPorTreinoId.values().stream()
                .map(listaDeExercicioPorTreino -> {
                            TreinoExercicioResumoModeloCruQuerySqlDTO primeiroItem = listaDeExercicioPorTreino.getFirst();
                            return new TreinoExercicioResumoDTO(
                                    primeiroItem.treinoId(),
                                    primeiroItem.nomeTreino(),
                                    primeiroItem.grauDificuldade(),
                                    primeiroItem.origemTreinoExercicio(),
                                    primeiroItem.favorito(),
                                    listaDeExercicioPorTreino.size()
                            );
                        }
                ).toList();
    }

    public List<TreinoExercicioResumoDTO> listarPorAluno(Integer alunoId) {
        List<TreinoExercicioResumoModeloCruQuerySqlDTO> listaComValoresNaoTratados = treinoExercicioRepository.buscarTreinosExerciciosPorAluno(alunoId);

        Map<Integer, List<TreinoExercicioResumoModeloCruQuerySqlDTO>> agrupadoPorTreinoId = listaComValoresNaoTratados.stream()
                .collect(Collectors.groupingBy(TreinoExercicioResumoModeloCruQuerySqlDTO::treinoId));

        return agrupadoPorTreinoId.values().stream()
                .map(listaDeExercicioPorTreino -> {
                    TreinoExercicioResumoModeloCruQuerySqlDTO primeiroItem = listaDeExercicioPorTreino.getFirst();
                    return new TreinoExercicioResumoDTO(
                            primeiroItem.treinoId(),
                            primeiroItem.nomeTreino(),
                            primeiroItem.grauDificuldade(),
                            primeiroItem.origemTreinoExercicio(),
                            primeiroItem.favorito(),
                            listaDeExercicioPorTreino.size()
                    );
                })
                .toList();
    }

    public Page<TreinoExercicioResumoDTO> paginarPorAluno(Integer alunoId, Pageable pageable) {
        // 1. Buscar dados crus do repository
        List<TreinoExercicioResumoModeloCruQuerySqlDTO> listaNaoTratada =
                treinoExercicioRepository.buscarTreinosExerciciosPorAluno(alunoId);

        // 2. Agrupar por treinoId
        Map<Integer, List<TreinoExercicioResumoModeloCruQuerySqlDTO>> agrupadoPorTreinoId =
                listaNaoTratada.stream()
                        .collect(Collectors.groupingBy(TreinoExercicioResumoModeloCruQuerySqlDTO::treinoId));

        // 3. Transformar em DTO resumido
        List<TreinoExercicioResumoDTO> listaResumida = agrupadoPorTreinoId.values().stream()
                .map(treinoExercicioMapper::toResumoDTO)
                .toList();

        // 4. Paginação manual
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listaResumida.size());

        List<TreinoExercicioResumoDTO> sublista = (start > end) ? List.of() : listaResumida.subList(start, end);

        return new PageImpl<>(sublista, pageable, listaResumida.size());
    }

    public List<ExerciciosPorTreinoResponseDTO> buscarExerciciosPorTreino(Integer treinoId, Integer alunoId) {

        return treinoExercicioRepository.buscarExerciciosPorTreino(treinoId, alunoId);
    }

    public List<TreinoExercicioEditResponseGetDTO> buscarInfosEditTreinoExercicio(Integer idPersonal, Integer idTreino) {
        boolean treinoExiste = treinoRepository.existsById(idTreino);

        if (!treinoExiste) {
            throw new TreinoNaoEncontradoException("Treino com ID " + idTreino + " não encontrado");
        }

        return treinoExercicioRepository.buscarInfosEditTreinoExercicio(idPersonal, idTreino);
    }

    public List<TreinoExercicioResponseGetDto> cadastrarComVariosExercicios(TreinoExercicioAssociacaoRequestDTO treinosDto) {

        Treino treinoExistente = treinoRepository.findById(treinosDto.idTreino()).
                orElseThrow(() -> new TreinoNaoEncontradoException("Treino com o ID " + treinosDto.idTreino() + " não encontrado."));

        List<TreinoExercicio> treinoExercicios = new ArrayList<>();

        for (TreinoExercicioRequestPostDto dto : treinosDto.exercicios()) {
            validarEnums(Map.of(
                    new OrigemTreinoExercicioEnumValidator(), dto.origemTreinoExercicio(),
                    new GrauDificuldadeEnumValidator(), dto.grauDificuldade()
            ));

            Exercicio exercicioExistente = exercicioRepository.findById(dto.exercicioId()).
                    orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + dto.exercicioId() + " não encontrado."));

            boolean jaAssociado = treinoExercicioRepository.existsByTreino_IdAndExercicio_Id(treinosDto.idTreino(), dto.exercicioId());
            if (jaAssociado) {
                throw new ApiExceptions.BadRequestException("Exercício com o ID " + dto.exercicioId() + " já está associado ao treino com ID " + treinosDto.idTreino());
            }

            TreinoExercicio novoTreino = treinoExercicioMapper.toEntity(dto);
            novoTreino.setTreino(treinoExistente);
            novoTreino.setExercicio(exercicioExistente);

            treinoExistente.setGrauDificuldade(dto.grauDificuldade());
            treinoExistente.setFavorito(false);
            treinoExistente.setOrigem(dto.origemTreinoExercicio());

            treinoExercicios.add(novoTreino);
        }

        List<TreinoExercicio> salvos = treinoExercicioRepository.saveAll(treinoExercicios);

        return salvos.stream()
                .map(treinoExercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TreinoExercicioResponseGetDto> atualizarComVariosExercicios(Integer idTreino, TreinoExercicioAssociacaoRequestDTO dto) {

        Treino treinoExistente = treinoRepository.findById(idTreino).
                orElseThrow(() -> new TreinoNaoEncontradoException("Treino com o ID " + idTreino + " não encontrado."));

        // Por segurança, verifica se o treinoId da requisição bate com o path param
        if (!idTreino.equals(dto.idTreino())) {
            throw new IdsRequisicaoIncompativeisException("ID do treino na URL e no corpo da requisição não conferem.");
        }

        List<TreinoExercicio> treinoExerciciosExistentes = treinoExercicioRepository.findByTreino_Id(idTreino);

        Map<Integer, TreinoExercicio> exerciciosExistentes = treinoExerciciosExistentes.stream()
                .collect(Collectors.toMap(treinoExercicio -> treinoExercicio.getExercicio().getId(), treinoExercicio -> treinoExercicio));

        List<TreinoExercicio> treinoExerciciosParaSalvar = new ArrayList<>();

        for (TreinoExercicioRequestPostDto exercicioDto : dto.exercicios()) {
            validarEnums(Map.of(
                    new OrigemTreinoExercicioEnumValidator(), exercicioDto.origemTreinoExercicio(),
                    new GrauDificuldadeEnumValidator(), exercicioDto.grauDificuldade()
            ));

            Exercicio exercicioExistente = exercicioRepository.findById(exercicioDto.exercicioId()).
                    orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + exercicioDto.exercicioId() + " não encontrado."));

            if (exerciciosExistentes.containsKey(exercicioDto.exercicioId())) {
                TreinoExercicio treinoExercicioAtual = exerciciosExistentes.get(exercicioDto.exercicioId());
                treinoExercicioAtual.setCarga(exercicioDto.carga());
                treinoExercicioAtual.setRepeticoes(exercicioDto.repeticoes());
                treinoExercicioAtual.setSeries(exercicioDto.series());
                treinoExercicioAtual.setDescanso(exercicioDto.descanso());
                treinoExercicioAtual.setDataModificacao(exercicioDto.dataHoraModificacao());
                treinoExistente.setOrigem(exercicioDto.origemTreinoExercicio());
                treinoExistente.setGrauDificuldade(exercicioDto.grauDificuldade());

                treinoExerciciosParaSalvar.add(treinoExercicioAtual);
            } else {
                TreinoExercicio novoTreinoExercicio = treinoExercicioMapper.toEntity(exercicioDto);
                novoTreinoExercicio.setTreino(treinoExistente);
                novoTreinoExercicio.setExercicio(exercicioExistente);
                treinoExerciciosParaSalvar.add(novoTreinoExercicio);
            }
        }
        Set<Integer> novosIds = dto.exercicios().stream()
                .map(TreinoExercicioRequestPostDto::exercicioId)
                .collect(Collectors.toSet());

        List<TreinoExercicio> paraRemover = treinoExerciciosExistentes.stream()
                .filter(te -> !novosIds.contains(te.getExercicio().getId()))
                .collect(Collectors.toList());

        treinoExercicioRepository.deleteAll(paraRemover);

        List<TreinoExercicio> treinoExerciciosSalvos = treinoExercicioRepository.saveAll(treinoExerciciosParaSalvar);

        return treinoExerciciosSalvos.stream()
                .map(treinoExercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
