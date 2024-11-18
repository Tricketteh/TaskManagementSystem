package com.triche.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response with token")
public class JwtAuthenticationResponse {
    @Schema(description = "Token", example = "euJhbGcoOiJIUzUxMiJ8.eyJzdWIiOiJhZD1pbiIsImV4cCA6MTYyMjUwNj...")
    private String token;
}
