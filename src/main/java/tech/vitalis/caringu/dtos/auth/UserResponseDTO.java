package tech.vitalis.caringu.dtos.auth;

import java.util.List;

public record UserResponseDTO(
        Integer id,
        String email,
        String nome,
        String foto,
        List<String> perfis,
        boolean perfilCompleto
) {
}
