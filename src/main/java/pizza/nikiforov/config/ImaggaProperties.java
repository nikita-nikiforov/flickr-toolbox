package pizza.nikiforov.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "imagga")
public class ImaggaProperties {
    private String apiKey;
    private String apiSecret;
}
