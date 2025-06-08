package tech.vitalis.caringu.dtos.PersonalTrainerBairro;

import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.Cidade;

public record PersonalComBairroCidadeResponseDTO(
        Integer idBairro,
        Bairro bairro,
        Integer idCidade,
        Cidade cidade
) {
}
