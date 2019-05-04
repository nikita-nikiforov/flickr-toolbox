package pizza.nikiforov.flickr.service;

import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth1Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
@Service
@RequiredArgsConstructor
@Setter
@Getter
public class TokenService {
    private final AuthInterface authInterface;

    @Value("${flickr.token}")
    private String token;
    @Value("${flickr.secret-token}")
    private String secretToken;

    // TODO
    public void askTokensFromConsole() {
        OAuth1RequestToken requestToken = authInterface.getRequestToken();
        String authorizationUrl = authInterface.getAuthorizationUrl(requestToken, Permission.DELETE);
        log.info("Give access: {}", authorizationUrl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String tokenKey = "";

        try {
            tokenKey = bufferedReader.readLine();
        } catch (IOException e) {
            log.error("Cannot read token key from user input: {}", e);
        }

        OAuth1Token accessToken = authInterface.getAccessToken(requestToken, tokenKey);
        setToken(accessToken.getToken());
        setSecretToken(accessToken.getTokenSecret());
    }
}
