package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.repository.ExercicioRepository;

import java.util.List;

@Service
public class ExercicioService {
    private final ExercicioRepository exercicioRepository;

    public ExercicioService(ExercicioRepository  exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    public Exercicio salvarExercicio(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public List<Exercicio> pegarExercicios() {
        return exercicioRepository.findAll();
    }
}
