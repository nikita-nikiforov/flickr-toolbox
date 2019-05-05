package pizza.nikiforov.geotag.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class GeojsonGeometry {
    private String type;
    private List<Float> coordinates;
}
