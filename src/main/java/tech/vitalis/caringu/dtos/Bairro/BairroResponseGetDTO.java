package tech.vitalis.caringu.dtos.Bairro;

import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;

public record BairroResponseGetDTO(
        Integer id,
        String nome,
        CidadeResponseGetDTO cidade
) {
}
