package co.com.luisgomez29.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class GenreDTO {

    @Size(max = 30)
    @NotNull
    @NotBlank
    String name;

    @Size(max = 255)
    String description;

}
