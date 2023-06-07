package co.com.luisgomez29.model.actor;

import co.com.luisgomez29.model.country.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Actor {
    private int id;
    private Country country;
    private String firstName;
    private String lastName;
    private String email;
}
