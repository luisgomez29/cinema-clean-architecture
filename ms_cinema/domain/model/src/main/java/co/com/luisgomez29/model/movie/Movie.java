package co.com.luisgomez29.model.movie;

import co.com.luisgomez29.model.genre.Genre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class Movie {
    private Integer id;
    private Genre genre;
    private String name;
    private String country;
    private Float duration;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
