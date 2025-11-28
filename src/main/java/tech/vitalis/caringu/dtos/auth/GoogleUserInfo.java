package tech.vitalis.caringu.dtos.auth;

public record GoogleUserInfo(
        String email,
        String nome,
        String foto,
        String sub
) {
}


