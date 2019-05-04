package pizza.nikiforov.service.flickr;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pizza.nikiforov.model.TagValue;
import pizza.nikiforov.model.TagWrapper;
import pizza.nikiforov.model.TagsResponse;
import pizza.nikiforov.service.autotag.ImaggaService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TagService {
    private final ImaggaService imaggaService;
    private final PhotosInterface photosInterface;

    public void addTagsFromImagga(List<Photo> photos) {
        photos.stream()
                .forEach(photo -> {
                    String photoUrl = photo.getMediumUrl();
                    TagsResponse response = imaggaService.getImaggaTagsResponse(photoUrl);
                    log.info("Image: {}", photoUrl);
                    List<TagValue> imaggaTags = response.getResult().getTags()
                            .stream()
                            .filter(tagWrapper -> tagWrapper.getConfidence() > 25.)
                            .map(TagWrapper::getTag)
                            .collect(Collectors.toList());
                    long numberOfImaggaTags = imaggaTags.stream()
                            .map(tag -> Arrays.asList(tag.getEn(), tag.getUk(), tag.getRu()))
                            .mapToLong(Collection::size)
                            .sum();
                    String[] tagsToAdd;
                    if (photo.getTags().size() + numberOfImaggaTags <= 75) {
                        tagsToAdd = imaggaTags.stream()
                                .map(tag -> Arrays.asList(tag.getEn(), tag.getRu(), tag.getUk()))
                                .flatMap(Collection::stream)
                                .toArray(String[]::new);
                        log.info("Left all the {} tags.", tagsToAdd.length);
                    } else {
                        tagsToAdd = imaggaTags.stream()
                                .map(TagValue::getEn)
                                .limit((long) (75 - photo.getTags().size()))
                                .toArray(String[]::new);
                        log.info("Left only English {} tags.", tagsToAdd.length);
                    }

                    addTagsToFlickrPhoto(photo, tagsToAdd);
                    sleep();
                });
    }

    private void addTagsToFlickrPhoto(Photo photo, String[] tags) {
        try {
            log.info("Current tags number: {}", photo.getTags().size());
            log.info("Adding {} tags: {}", tags.length, Arrays.asList(tags));
            photosInterface.addTags(photo.getId(), tags);
        } catch (FlickrException e) {
            log.error("Cannot add tags to Flickr: {}", e);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.warn("Thread has been interrupted: {}", e);
            Thread.currentThread().interrupt();
        }
    }
}
