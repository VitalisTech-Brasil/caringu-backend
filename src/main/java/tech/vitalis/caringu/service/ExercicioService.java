package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.repository.ExercicioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExercicioService {

    @Autowired
    private final ExercicioRepository exercicioRepository;

    public ExercicioService(ExercicioRepository  exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    public ResponseEntity<Exercicio> salvarExercicio(Exercicio exercicio) {
        Exercicio novoExercicio = exercicioRepository.save(exercicio);
        return ResponseEntity.status(201).body(novoExercicio);
    }

    public ResponseEntity<List<Exercicio>> pegarExercicios() {
        if (exercicioRepository.count() == 0) {
            return ResponseEntity.status(404).build();
        }
        List<Exercicio> exercicios = exercicioRepository.findAll();
        return ResponseEntity.status(200).body(exercicios);
    }

    public ResponseEntity<Exercicio> atualizarExercicio(Long id, Exercicio exercicio) {
        if (!exercicioRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        if (exercicio.getNome() == null || exercicio.getGrupoMuscular() == null) {
            return ResponseEntity.status(400).body(null);
        }

        exercicio.setId(id);
        Exercicio exercicioAtualizado = exercicioRepository.save(exercicio);
        return ResponseEntity.ok(exercicioAtualizado);
    }

    public ResponseEntity<Exercicio> atualizarParteExercicio(Long id, Exercicio exercicio) {
        Optional<Exercicio> exercicioExistenteOpt = exercicioRepository.findById(id);

        if (exercicioExistenteOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Exercicio exercicioExistente = exercicioExistenteOpt.get();

        if (exercicio.getNome() != null) {
            exercicioExistente.setNome(exercicio.getNome());
        }

        if (exercicio.getGrupoMuscular() != null) {
            exercicioExistente.setGrupoMuscular(exercicio.getGrupoMuscular());
        }

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return ResponseEntity.status(200).body(exercicioAtualizado);
    }

    public ResponseEntity<Void> removerExercicio(@PathVariable Long id) {
        if (exercicioRepository.existsById(id)) {
            exercicioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
