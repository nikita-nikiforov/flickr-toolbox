package pizza.nikiforov.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuth1Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pizza.nikiforov.config.FlickrProperties;
import pizza.nikiforov.model.Tag;
import pizza.nikiforov.model.TagValue;
import pizza.nikiforov.model.TagsResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class FlickrService {
    private final ImaggaService imaggaService;
    private final FlickrProperties flickrProperties;

    private Flickr flickr;
    private AuthInterface authInterface;
    private PeopleInterface peopleInterface;
    private PhotosInterface photosInterface;


    @Value("${flickr.token}")
    private String token;
    @Value("${flickr.secret-token}")
    private String secretToken;


    private String nsid;


    private PhotoList<Photo> publicPhotos = new PhotoList<>();

    public void initFlickr(){
        flickr = new Flickr(flickrProperties.getApiKey(), flickrProperties.getSecret(), new REST());
        authInterface = flickr.getAuthInterface();
        peopleInterface = flickr.getPeopleInterface();
        photosInterface= flickr.getPhotosInterface();

//        authUser();
        getNsid();
        getUserPhotos();

        processPhotos();

    }


    private void authUser() {
        OAuth1RequestToken requestToken = authInterface.getRequestToken();
        String authorizationUrl = authInterface.getAuthorizationUrl(requestToken, Permission.DELETE);
        log.info("Give access: {}", authorizationUrl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String tokenKey = "";

        try {
            tokenKey = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OAuth1Token accessToken = authInterface.getAccessToken(requestToken, tokenKey);
        token = accessToken.getToken();
        secretToken = accessToken.getTokenSecret();
        try {
            Auth auth = authInterface.checkToken(token, secretToken);
            RequestContext.getRequestContext().setAuth(auth);
        } catch (FlickrException e) {
            e.printStackTrace();
        }
    }

    private void getNsid() {
        try {
            Auth auth = authInterface.checkToken(token, secretToken);
            RequestContext.getRequestContext().setAuth(auth);
            nsid = auth.getUser().getId();
            log.info("Received user_id: {}", nsid);
        } catch (FlickrException e) {
            log.error("Cannot get user's nsid: {}", e);
        }
    }

    private void getUserPhotos() {
        Set<String> extras = Stream.of("tags, url_l").collect(Collectors.toSet());
        try {
            publicPhotos.addAll(peopleInterface.getPublicPhotos(nsid, extras, 300, 1));
            publicPhotos.addAll(peopleInterface.getPublicPhotos(nsid, extras, 300, 2));
            log.info("Received publicPhotos: {}", publicPhotos);
        } catch (FlickrException e) {
            log.error("Cannot get user's photos: {}", e);
        }
    }

    private void processPhotos() {
        publicPhotos.stream()
                .forEach(photo -> {
                    String photoUrl = photo.getMediumUrl();
                    TagsResponse response = imaggaService.getImaggaTagsResponse(photoUrl);
                    log.info("Image: {}", photoUrl);
                    List<TagValue> imaggaTags = response.getResult().getTags()
                            .stream()
                            .filter(tag -> tag.getConfidence() > 25.)
                            .map(Tag::getTag)
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
            e.printStackTrace();
        }
    }
}
