package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
@Schema(title = "ExistingUser", name="Existing", description = "existing user dto")
public class ExistingUserDTO {
    @Schema(description = "the UUID of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    @NotBlank(message = "uuid-required")
    private UUID uuid;
    @Schema(description = "the name of the user", example = "John Smith")
    @NotBlank(message = "name-required")
    private String name;
    @Schema(description = "the username of the user", example = "john_smith")
    @NotBlank(message = "username-required")
    private String userName;
}
