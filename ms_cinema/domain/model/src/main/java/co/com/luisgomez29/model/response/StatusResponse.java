package co.com.luisgomez29.model.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class StatusResponse<T> {
    private String description;
    private T before;
    private T actual;
}

