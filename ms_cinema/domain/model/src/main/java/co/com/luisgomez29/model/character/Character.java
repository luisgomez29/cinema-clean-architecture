package co.com.luisgomez29.model.character;

import co.com.luisgomez29.model.actor.Actor;
import co.com.luisgomez29.model.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Character {
    private int id;
    private Actor actor;
    private Movie movie;
    private String name;
    private String description;
}
