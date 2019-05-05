package pizza.nikiforov.geotag.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class GeojsonFlickrProperties {
    private String url;
    private String title;
    private String photoUrl;

    public GeojsonFlickrProperties(String url, String title, String photoUrl) {
        this.url = url;
        this.title = title;
        this.photoUrl = photoUrl;
    }
}
