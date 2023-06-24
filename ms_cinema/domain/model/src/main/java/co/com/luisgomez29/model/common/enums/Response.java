package co.com.luisgomez29.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Response {
    SUCCESSFUL_UPGRADE("R0001", "Successful upgrade");

    private final String code;
    private final String description;
}