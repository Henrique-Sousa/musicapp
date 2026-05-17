package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // cria getters e setters
@EqualsAndHashCode(callSuper=false)
@Schema(title = "UserRegister", name="UserRegister", description = "dto for registering a new user")
public class UserRegisterDTO extends NewUserDTO {
    @Schema(description = "the password for the user", example = "12345678")
    @NotBlank(message = "password-required")
    private String password; 

    public UserRegisterDTO(String name, String userName, String password) {
        super(name, userName);
        this.password = password;
    }
}
