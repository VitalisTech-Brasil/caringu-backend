package tech.vitalis.caringu.service.SingleSignOn;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class GoogleTokenVerifierService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierService(
            @Value("${google.client.id}") String clientId
    ) {
        this.clientId = clientId;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public Optional<GoogleIdToken.Payload> verify(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                if (Boolean.TRUE.equals(payload.getEmailVerified())) {
                    return Optional.of(payload);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // loga erro
        }
        return Optional.empty();
    }

    public Optional<GoogleIdToken.Payload> exchangeCodeForPayload(String code) {
        try {
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            String REDIRECT_URI = "postmessage";

            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    jsonFactory,
                    "https://oauth2.googleapis.com/token",
                    clientId,
                    clientSecret,
                    code,
                    REDIRECT_URI
            ).execute();

            String idTokenString = tokenResponse.getIdToken();
            GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, idTokenString);

            if (Boolean.TRUE.equals(idToken.getPayload().getEmailVerified())) {
                return Optional.of(idToken.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
        }

        return Optional.empty();
    }
}
