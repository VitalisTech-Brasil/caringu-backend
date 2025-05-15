package tech.vitalis.caringu.entity.Converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

@Converter(autoApply = true)
public class FrequenciaTreinoEnumConverter implements AttributeConverter<FrequenciaTreinoEnum, String> {

    @Override
    public String convertToDatabaseColumn(FrequenciaTreinoEnum atributo) {
        return atributo != null ? atributo.getValor() : null;
    }

    @Override
    public FrequenciaTreinoEnum convertToEntityAttribute(String valor) {
        return valor != null ? FrequenciaTreinoEnum.fromValor(valor) : null;
    }
}