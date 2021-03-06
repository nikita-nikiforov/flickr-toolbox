package pizza.nikiforov.flickr.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.people.PeopleInterface;
import com.flickr4java.flickr.photos.PhotosInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pizza.nikiforov.flickr.property.FlickrProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FlickrProperties.class)
public class FlickrConfiguration {
    private final FlickrProperties flickrProperties;

    @Bean
    public Flickr flickr() {
        return new Flickr(flickrProperties.getApiKey(), flickrProperties.getSecret(), new REST());
    }

    @Bean
    @DependsOn("flickr")
    public PeopleInterface peopleInterface() {
        return flickr().getPeopleInterface();
    }

    @Bean
    @DependsOn("flickr")
    public PhotosInterface photosInterface() {
        return flickr().getPhotosInterface();
    }

    @Bean
    @DependsOn("flickr")
    public AuthInterface authInterface() {
        return flickr().getAuthInterface();
    }
}
