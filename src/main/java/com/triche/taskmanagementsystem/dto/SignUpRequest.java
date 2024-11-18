package com.triche.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Signup request")
public class SignUpRequest {

    @Schema(description = "Username", example = "Jane Doe")
    @Size(min = 2, max = 50, message = "Username should contain from 2 to 50 symbols")
    @NotBlank(message = "Username should not be blank")
    private String username;

    @Schema(description = "Email address", example = "email@example.com")
    @Size(min = 5, max = 135, message = "Email address should be in range of 5 to 255 symbols")
    @NotBlank(message = "Email address should not be blank")
    @Email(message = "Email address should be in format user@example.com")
    private String email;

    @Schema(description = "Password", example = "my_very_secret_password")
    @Size(min = 8, max = 16, message = "Password length should in range from 8 to 16 symbols")
    private String password;
}
