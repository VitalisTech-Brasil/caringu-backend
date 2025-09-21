package tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho;

import java.util.List;

public record AulasRascunhoResponseDTO(
        boolean temRascunhos,
        List<AulaRascunhoResponseGetDTO> aulas
) {
}
