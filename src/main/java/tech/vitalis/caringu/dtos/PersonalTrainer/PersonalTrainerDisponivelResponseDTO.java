package tech.vitalis.caringu.dtos.PersonalTrainer;

import tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.util.List;

public record PersonalTrainerDisponivelResponseDTO(
        Integer id,
        String nomePersonal,
        String email,
        String celular,
        Integer experiencia,
        String urlFotoPerfil,
        GeneroEnum genero,
        List<String> especialidades,
        List<PlanoResumoDTO> planos,
        String bairro,
        String cidade
) {
}
