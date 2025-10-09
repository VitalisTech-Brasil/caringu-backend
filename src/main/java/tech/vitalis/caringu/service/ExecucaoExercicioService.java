package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.repository.ExecucaoExercicioRepository;

@Service
public class ExecucaoExercicioService {

    public final ExecucaoExercicioRepository execucaoExercicioRepository;

    public ExecucaoExercicioService(ExecucaoExercicioRepository execucaoExercicioRepository) {
        this.execucaoExercicioRepository = execucaoExercicioRepository;
    }
}
