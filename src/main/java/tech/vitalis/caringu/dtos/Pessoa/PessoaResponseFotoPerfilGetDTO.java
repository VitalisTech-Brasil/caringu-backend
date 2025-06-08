package tech.vitalis.caringu.dtos.Pessoa;

public record PessoaResponseFotoPerfilGetDTO(
        Integer id,
        String nome,
        String email,
        String urlFotoPerfil
) {
}
