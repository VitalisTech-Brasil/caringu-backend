package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.ExecucaoExercicio.AtualizarStatusExecucaoExercicioRequestDTO;
import tech.vitalis.caringu.entity.ExecucaoExercicio;
import tech.vitalis.caringu.exception.ExecucaoExercicio.ExecucaoExercicioNaoEncontradaException;
import tech.vitalis.caringu.repository.ExecucaoExercicioRepository;

@Service
public class ExecucaoExercicioService {

    public final ExecucaoExercicioRepository execucaoExercicioRepository;

    public ExecucaoExercicioService(ExecucaoExercicioRepository execucaoExercicioRepository) {
        this.execucaoExercicioRepository = execucaoExercicioRepository;
    }

    @Transactional
    public void atualizarStatusExecucao(Integer idExecucaoExercicio, AtualizarStatusExecucaoExercicioRequestDTO payload) {
        ExecucaoExercicio execucao = execucaoExercicioRepository.findById(idExecucaoExercicio)
                .orElseThrow(() -> new ExecucaoExercicioNaoEncontradaException("Execução de exercício não encontrada com o ID informado."));

        execucao.setFinalizado(payload.finalizado());
        execucaoExercicioRepository.save(execucao);
    }
}
