package pizza.nikiforov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pizza.nikiforov.service.flickr.AuthService;
import pizza.nikiforov.service.flickr.TokenService;

@Log4j2
@Service
@RequiredArgsConstructor
public class FlickrService {
    private final TokenService tokenService;
    private final AuthService authService;
}
