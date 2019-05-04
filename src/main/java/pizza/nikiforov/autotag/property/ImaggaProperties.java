package pizza.nikiforov.autotag.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "imagga")
public class ImaggaProperties {
    private String apiKey;
    private String apiSecret;
}
