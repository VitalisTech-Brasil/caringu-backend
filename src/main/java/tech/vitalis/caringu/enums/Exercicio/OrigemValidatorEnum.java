package tech.vitalis.caringu.enums.Exercicio;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.exception.ApiExceptions;

import java.util.Arrays;

@Component
public class OrigemValidatorEnum {

    public void validar(String origem){
        try{
            OrigemEnum.valueOf(origem.toUpperCase());
        }catch (IllegalArgumentException  | NullPointerException e){
            throw new ApiExceptions.BadRequestException("Origem inválida. Valores válidos:" +
                    Arrays.toString(OrigemEnum.values()));
        }
    }
}
