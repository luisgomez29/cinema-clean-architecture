package co.com.luisgomez29.model.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Genre {
    private int id;
    private String name;
    private String description;
}
