package com.unibague.magno.application.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response DTO containing user information retrieved from Google OAuth authentication.
 * Used to display the authenticated user's basic profile data.
 */
@Value
@Builder
public class GoogleInfoResponse {
    String name;
    String email;
    String picture;
    Long userId;
}
