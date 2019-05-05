package pizza.nikiforov.geotag.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeojsonFeature {
    private GeojsonGeometry geometry;
    private String type;
    private GeojsonFlickrProperties properties;
}
