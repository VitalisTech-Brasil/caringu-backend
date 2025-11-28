package tech.vitalis.caringu.service.SingleSignOn;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.auth.GoogleUserInfo;

import java.util.Optional;

@Component
public class GoogleAuthStrategy {

    private final GoogleTokenVerifierService tokenVerifierService;

    public GoogleAuthStrategy(GoogleTokenVerifierService tokenVerifierService) {
        this.tokenVerifierService = tokenVerifierService;
    }

    public Optional<GoogleUserInfo> validateAuthorizationCode(String codigo) {
        Optional<Payload> optionalPayload = tokenVerifierService.exchangeCodeForPayload(codigo);

        if (optionalPayload.isEmpty()) {
            return Optional.empty();
        }

        Payload payload = optionalPayload.get();

        String email = payload.getEmail();
        String nome = (String) payload.get("name");
        String foto = (String) payload.get("picture");
        String sub = payload.getSubject();

        return Optional.of(new GoogleUserInfo(email, nome, foto, sub));
    }
}


