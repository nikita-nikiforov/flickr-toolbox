package pizza.nikiforov.autotag.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TagsResponse {
    private TagsResult result;
    private TagStatus status;
}
