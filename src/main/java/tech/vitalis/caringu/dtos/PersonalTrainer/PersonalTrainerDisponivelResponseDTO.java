package tech.vitalis.caringu.dtos.PersonalTrainer;

import java.util.List;

public record PersonalTrainerDisponivelResponseDTO(
        Integer id,
        String nomePersonal,
        String email,
        String celular,
        Integer experiencia,
        List<String> especialidades,
        String bairro,
        String cidade
) {
}
