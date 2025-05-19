package tech.vitalis.caringu.dtos.Cidade;

import tech.vitalis.caringu.dtos.Estado.EstadoResponseGetDTO;

public record CidadeResponseGetDTO(
        Integer id,
        String nome,
        EstadoResponseGetDTO estado
) {
}
