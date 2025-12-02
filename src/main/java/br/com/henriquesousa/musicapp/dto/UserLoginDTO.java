package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
@Schema(title = "UserLogin", name="UserLogin", description = "dto for logging the user in")
public class UserLoginDTO {
    @Schema(description = "the username of the user", example = "john_smith")
    @NotBlank(message = "username-required")
    private String userName;

    @Schema(description = "the password for the user", example = "12345678")
    @NotBlank(message = "password-required")
    private String password; 
}
