package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // cria getters e setters
@EqualsAndHashCode(callSuper=false)
@Schema(title = "ExistingUser", name="Existing", description = "existing user dto")
public class ExistingUserDTO extends NewUserDTO {
    @Schema(description = "the UUID of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    @NotBlank(message = "uuid-required")
    private UUID uuid;

    public ExistingUserDTO(UUID uuid, String name, String userName) {
        super(name, userName); // Call the parent's constructor
        this.uuid = uuid;
    }
}

