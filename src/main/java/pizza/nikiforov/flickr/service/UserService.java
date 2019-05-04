package pizza.nikiforov.flickr.service;

import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pizza.nikiforov.flickr.exception.FlickrToolboxException;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    public String getNsid() {
        Auth auth = RequestContext.getRequestContext().getAuth();
        if (auth.getUser() != null) {
            String nsid = auth.getUser().getId();
            log.info("Received user_id: {}", nsid);
            return nsid;
        } else {
            throw new FlickrToolboxException("Cannot get Nsid, because Auth isn't set in the RequestContext.");
        }
    }
}
