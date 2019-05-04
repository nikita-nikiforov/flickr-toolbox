package pizza.nikiforov.autotag.service;

import com.flickr4java.flickr.photos.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pizza.nikiforov.flickr.service.PhotoService;
import pizza.nikiforov.flickr.service.TagService;
import pizza.nikiforov.flickr.service.UserService;

import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class AutotagService {
    private final UserService userService;
    private final PhotoService photoService;
    private final TagService tagService;

    public void start() {
        String userNsid = userService.getNsid();
        List<Photo> userPublicPhotos = photoService.getUserPublicPhotos(userNsid);
        tagService.addTagsFromImagga(userPublicPhotos);
    }
}
