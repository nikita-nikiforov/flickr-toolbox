package pizza.nikiforov.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TagsResult {
    private List<TagWrapper> tags;
}
