package tech.vitalis.caringu.dtos.PersonalTrainerBairro;

public record AtualizarBairroDTO(
        Integer bairroId,
        String novoNomeBairro,
        Integer cidadeId,
        String novoNomeCidade
) {
}
