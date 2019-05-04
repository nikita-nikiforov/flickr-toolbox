package pizza.nikiforov.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TagWrapper {
    private double confidence;
    private TagValue tag;
}
