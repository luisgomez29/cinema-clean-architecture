package co.com.luisgomez29.model.genre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Genre {
    private Integer id;
    private String name;
    private String description;
}
