package tech.vitalis.caringu.enums.TreinoExercicio;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.exception.ApiExceptions;

import java.util.Arrays;

@Component
public class GrauDificuldadeValidatorEnum {

    public void validar(String grauDificuldade){
        try{
            GrauDificuldadeEnum.valueOf(grauDificuldade.toUpperCase());
        }catch (IllegalArgumentException  | NullPointerException e){
            throw new ApiExceptions.BadRequestException("Grau de Dificuldade inválido. Valores válidos:" +
                    Arrays.toString(GrauDificuldadeEnum.values()));
        }
    }
}
