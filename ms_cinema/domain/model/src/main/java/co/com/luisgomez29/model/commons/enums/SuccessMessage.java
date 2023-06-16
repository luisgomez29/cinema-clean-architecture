package co.com.luisgomez29.model.commons.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    GET_SECRET("Secret obtained successfully");

    private final String message;
}
