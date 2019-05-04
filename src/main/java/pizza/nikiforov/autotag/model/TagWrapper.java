package pizza.nikiforov.autotag.model;

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
