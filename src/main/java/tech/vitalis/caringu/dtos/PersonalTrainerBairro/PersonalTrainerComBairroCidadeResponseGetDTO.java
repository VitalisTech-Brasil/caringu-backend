package tech.vitalis.caringu.dtos.PersonalTrainerBairro;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PersonalTrainerComBairroCidadeResponseGetDTO(
        Integer id,
        String nome,
        String email,
        String celular,
        String urlFotoPerfil,
        LocalDate dataNascimento,
        GeneroEnum genero,

        String cref,

        @Schema(description = "Nomes das especialidades do personal trainer")
        List<EspecialidadeResponseGetDTO> especialidades,

        Integer experiencia,
        LocalDateTime dataCadastro,
        Integer idBairro,
        String bairro,
        Integer idCidade,
        String cidade
) {
}
