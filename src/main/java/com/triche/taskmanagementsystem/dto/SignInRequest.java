package com.triche.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Sign in request")
public class SignInRequest {

    @Schema(description = "Username", example = "Jane Doe")
    @NotBlank(message = "Username should not be blank")
    @Size(min = 5, max = 50, message = "Username should contain 5 to 50 symbols")
    private String username;


    @Schema(description = "Password", example = "my_very_secret_passport")
    @Size(min = 8, max = 16, message = "Password length should be in range from 8 to 16 symbols")
    @NotBlank(message = "Password should not be blank")
    private String password;
}
