package pizza.nikiforov.flickr.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthInterface authInterface;
    private final TokenService tokenService;

    @PostConstruct
    public void authenticateFromProperties(){
        String token = tokenService.getToken();
        String secretToken = tokenService.getSecretToken();
        if (token != null && secretToken != null) {
            setAuthInContext(token, secretToken);
            log.info("Authenticated with token {} and secretToken {}", token, secretToken);
        } else {
            log.info("Auth token and secret token haven't been set in application.properties.");
        }
    }

    public void setAuthInContext(String token, String secretToken) {
        try {
            Auth auth = authInterface.checkToken(token, secretToken);
            RequestContext.getRequestContext().setAuth(auth);
        } catch (FlickrException e) {
            log.error("Cannot set Auth into RequestContext: {}", e);
        }
    }


}
