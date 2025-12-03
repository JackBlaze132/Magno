package com.unibague.magno.application.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GoogleInfoResponse {
    String name;
    String email;
    String picture;
    Long userId;
}
