package com.melpy.perpus.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull
    @Email
    @Size(max = 50)
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;
    @NotNull
    @Size(max = 16)
    @Schema(description = "User's password", example = "password123")
    private String password;
}
