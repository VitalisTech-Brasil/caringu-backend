package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.entity.ExecucaoExercicio;

@Component
public class ExecucaoExercicioMapper {

    // Cria execução "em branco" a partir de AulaTreinoExercicio
    public ExecucaoExercicio toNovaExecucao(AulaTreinoExercicio aulaTreinoExercicio) {
        ExecucaoExercicio exec = new ExecucaoExercicio();
        exec.setAulaTreinoExercicio(aulaTreinoExercicio);
        exec.setCargaExecutada(aulaTreinoExercicio.getCarga());
        exec.setRepeticoesExecutadas(null);
        exec.setSeriesExecutadas(null);
        exec.setDescansoExecutado(null);
        exec.setFinalizado(false);
        return exec;
    }
}
