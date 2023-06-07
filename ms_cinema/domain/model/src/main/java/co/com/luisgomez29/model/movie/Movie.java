package co.com.luisgomez29.model.movie;

import co.com.luisgomez29.model.country.Country;
import co.com.luisgomez29.model.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Movie {
    private int id;
    private Genre genre;
    private Country country;
    private String name;
    private float duration;
    private String description;
}
