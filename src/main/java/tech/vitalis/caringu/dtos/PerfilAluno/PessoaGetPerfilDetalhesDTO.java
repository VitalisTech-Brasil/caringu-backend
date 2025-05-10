package tech.vitalis.caringu.dtos.PerfilAluno;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record PessoaGetPerfilDetalhesDTO(
        String nome,
        String email,
        String celular,
        String urlFotoPerfil,
        LocalDate dataNascimento,
        GeneroEnum genero
) {
}
