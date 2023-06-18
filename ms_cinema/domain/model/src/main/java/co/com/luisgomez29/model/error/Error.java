package co.com.luisgomez29.model.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Error {

    private String domain;
    private String code;
    private String message;
    private String reason;

}
