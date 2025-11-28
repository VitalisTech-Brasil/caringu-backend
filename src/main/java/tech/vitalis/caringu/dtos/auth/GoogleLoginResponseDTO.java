package tech.vitalis.caringu.dtos.auth;

public record GoogleLoginResponseDTO(
        Boolean sucesso,
        String token,
        UserResponseDTO usuario,
        Boolean precisaCadastro,
        String email,
        String nome,
        String foto,
        Boolean precisaCompletarPerfil,
        String erro
) {

    public static GoogleLoginResponseDTO sucesso(String token,
                                                 UserResponseDTO usuario,
                                                 boolean precisaCompletarPerfil) {
        return new GoogleLoginResponseDTO(
                true,
                token,
                usuario,
                false,
                null,
                null,
                null,
                precisaCompletarPerfil,
                null
        );
    }

    public static GoogleLoginResponseDTO precisaCadastro(GoogleUserInfo googleUserInfo) {
        return new GoogleLoginResponseDTO(
                false,
                null,
                null,
                true,
                googleUserInfo.email(),
                googleUserInfo.nome(),
                googleUserInfo.foto(),
                false,
                null
        );
    }

    public static GoogleLoginResponseDTO tokenGoogleInvalido() {
        return new GoogleLoginResponseDTO(
                false,
                null,
                null,
                false,
                null,
                null,
                null,
                false,
                "INVALID_GOOGLE_TOKEN"
        );
    }
}
