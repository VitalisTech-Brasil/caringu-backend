package tech.vitalis.caringu.dtos.Pessoa;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PessoaResponseGetDTO(
        Integer id,
        String nome,
        String email,
        String celular,
        String urlFotoPerfil,
        LocalDate dataNascimento,
        GeneroEnum genero,
        LocalDateTime dataCadastro
) {


}
