package tech.vitalis.caringu.service.SingleSignOn;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class GoogleTokenVerifierService {

    private static final String CLIENT_ID = "1012386320815-eumjbp8dnnbqne6nhdpivd9efc7mcr17.apps.googleusercontent.com";

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierService() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    public Optional<GoogleIdToken.Payload> verify(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                return Optional.of(idToken.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace(); // loga erro
        }
        return Optional.empty();
    }
}
