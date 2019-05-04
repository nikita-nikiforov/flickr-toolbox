package pizza.nikiforov.service.flickr;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PeopleInterface peopleInterface;
    private final UserService userService;


    public List<Photo> getUserPublicPhotos(String nsid) {
        List<Photo> publicPhotos = new ArrayList<>();
        Set<String> extras = Stream.of("tags, url_l").collect(Collectors.toSet());
        try {
            publicPhotos.addAll(peopleInterface.getPublicPhotos(nsid, extras, 300, 1));
            publicPhotos.addAll(peopleInterface.getPublicPhotos(nsid, extras, 300, 2));
            log.info("Received publicPhotos: {}", publicPhotos);
        } catch (FlickrException e) {
            log.error("Cannot get user's photos: {}", e);
        }
        return publicPhotos;
    }
}
