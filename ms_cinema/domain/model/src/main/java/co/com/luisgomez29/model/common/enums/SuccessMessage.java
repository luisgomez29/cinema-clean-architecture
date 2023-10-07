package co.com.luisgomez29.model.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    GET_SECRET("Secret obtained successfully"),
    SUCCESSFUL_UPGRADE("Successful upgrade");

    private final String value;
}
