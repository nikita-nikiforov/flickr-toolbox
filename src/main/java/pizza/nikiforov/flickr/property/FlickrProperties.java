package pizza.nikiforov.flickr.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "flickr")
public class FlickrProperties {
    private String apiKey;
    private String secret;
}
