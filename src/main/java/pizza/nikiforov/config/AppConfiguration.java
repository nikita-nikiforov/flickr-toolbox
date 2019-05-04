package pizza.nikiforov.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.PhotosInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({FlickrProperties.class, ImaggaProperties.class})
public class AppConfiguration {
    private final FlickrProperties flickrProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Flickr flickr() {
        return new Flickr(flickrProperties.getApiKey(), flickrProperties.getSecret(), new REST());
    }

    @Bean
    public PeopleInterface peopleInterface() {
        return flickr().getPeopleInterface();
    }

    @Bean
    public PhotosInterface photosInterface() {
        return flickr().getPhotosInterface();
    }

    @Bean
    public AuthInterface authInterface() {
        return flickr().getAuthInterface();
    }

}
