package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
@Schema(title = "NewUser", name="New", description = "new user dto")
public class NewUserDTO {
    @Schema(description = "the name of the user", example = "John Smith")
    @NotBlank(message = "name-required")
    private String name;

    @Schema(description = "the username of the user", example = "john_smith")
    @NotBlank(message = "username-required")
    private String userName;
}
