package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoFinalizado.AtualizarDataFimDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO;
import tech.vitalis.caringu.entity.TreinoFinalizado;
import tech.vitalis.caringu.exception.TreinoFinalizado.TreinoFinalizadoNaoEncontradoException;
import tech.vitalis.caringu.repository.TreinoFinalizadoRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TreinoFinalizadoService {

    private final TreinoFinalizadoRepository repository;

    public TreinoFinalizadoService(TreinoFinalizadoRepository repository) {
        this.repository = repository;
    }

    public List<TreinoIdentificacaoFinalizadoResponseDTO> listarPorPersonal(Integer personalId) {
        return repository.findAllTreinosByPersonalId(personalId);
    }

    public void atualizarDataHorarioFim(Integer idTreinoFinalizado, AtualizarDataFimDTO dto) {
        TreinoFinalizado treino = repository.findById(idTreinoFinalizado)
                .orElseThrow(() -> new TreinoFinalizadoNaoEncontradoException("Treino não encontrado com id: " + idTreinoFinalizado));

        treino.setDataHorarioFim(dto.dataHorarioFim());
        repository.save(treino);
    }
}