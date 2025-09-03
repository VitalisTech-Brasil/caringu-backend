package tech.vitalis.caringu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.mapper.AlunoTreinoExercicioMapper;
import tech.vitalis.caringu.repository.AlunoTreinoExercicioRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlunoTreinoExercicioService {

    private final AlunoTreinoExercicioRepository alunoTreinoExercicioRepository;
    private final AlunoTreinoExercicioMapper alunoTreinoExercicioMapper;

    public AlunoTreinoExercicioService(
            AlunoTreinoExercicioRepository alunoTreinoExercicioRepository,
            AlunoTreinoExercicioMapper alunoTreinoExercicioMapper
    ) {
        this.alunoTreinoExercicioRepository = alunoTreinoExercicioRepository;
        this.alunoTreinoExercicioMapper = alunoTreinoExercicioMapper;
    }

    public List<TreinoExercicioResumoDTO> listarPorAluno(Integer alunoId) {
        List<TreinoExercicioResumoModeloCruQuerySqlDTO> listaComValoresNaoTratados = alunoTreinoExercicioRepository.buscarTreinosExerciciosPorAluno(alunoId);

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
                alunoTreinoExercicioRepository.buscarTreinosExerciciosPorAluno(alunoId);

        // 2. Agrupar por treinoId
        Map<Integer, List<TreinoExercicioResumoModeloCruQuerySqlDTO>> agrupadoPorTreinoId =
                listaNaoTratada.stream()
                        .collect(Collectors.groupingBy(TreinoExercicioResumoModeloCruQuerySqlDTO::treinoId));

        // 3. Transformar em DTO resumido
        List<TreinoExercicioResumoDTO> listaResumida = agrupadoPorTreinoId.values().stream()
                .map(alunoTreinoExercicioMapper::toResumoDTO)
                .toList();

        // 4. Paginação manual
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listaResumida.size());

        List<TreinoExercicioResumoDTO> sublista = (start > end) ? List.of() : listaResumida.subList(start, end);

        return new PageImpl<>(sublista, pageable, listaResumida.size());
    }
}
