package pizza.nikiforov.geotag.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureWrapper {
    private String type;
    private List<GeojsonFeature> features;
}
