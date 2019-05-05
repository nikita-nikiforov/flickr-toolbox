package pizza.nikiforov.flickr.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PeopleInterface peopleInterface;

    /**
     * Returns the first 600 user photos. You need to provide user's id and the set of extras — the data you want
     * to have with your photos. E.g., your extras can be of two values "tags" and "url_l" and the retrieved photos
     * will have their tags and large image url.
     * @param   nsid    user's id
     * @param   extras  set of extras
     */
    public List<Photo> getUserPublicPhotos(String nsid, Set<String> extras) {
        List<Photo> publicPhotos = new ArrayList<>();
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
