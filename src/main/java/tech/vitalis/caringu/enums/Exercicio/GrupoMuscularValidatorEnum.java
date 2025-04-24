package tech.vitalis.caringu.enums.Exercicio;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.exception.ApiExceptions;

import java.lang.reflect.Array;
import java.util.Arrays;

@Component
public class GrupoMuscularValidatorEnum {

    public void validar(String grupoMuscular){
        try{
            GrupoMuscularEnum.valueOf(grupoMuscular.toUpperCase());
        }catch (IllegalArgumentException  | NullPointerException e){
            throw new ApiExceptions.BadRequestException("Grupo Muscular inválido. Valores válidos:" +
                    Arrays.toString(GrupoMuscularEnum.values()));
        }
    }
}
