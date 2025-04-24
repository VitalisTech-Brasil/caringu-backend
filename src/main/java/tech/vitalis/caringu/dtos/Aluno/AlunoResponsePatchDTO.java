package tech.vitalis.caringu.dtos.Aluno;

import com.fasterxml.jackson.annotation.JsonInclude;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AlunoResponsePatchDTO(
        Optional<String> nome,
        Optional<String> email,
        Optional<String> celular,
        Optional<String> urlFotoPerfil,
        Optional<LocalDate> dataNascimento,
        Optional<GeneroEnum> genero,

        Optional<Double> peso,
        Optional<Double> altura,
        Optional<NivelAtividadeEnum> nivelAtividade,
        Optional<NivelExperienciaEnum> nivelExperiencia
) {
}
