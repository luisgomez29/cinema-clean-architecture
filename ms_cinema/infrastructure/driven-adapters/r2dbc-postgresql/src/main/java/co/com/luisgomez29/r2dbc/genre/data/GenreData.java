package co.com.luisgomez29.r2dbc.genre.data;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("genre")
public class GenreData {
    @Id
    private int id;
    private String name;
    private String description;
}
