package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // cria getters e setters
@EqualsAndHashCode(callSuper=false)
@Schema(title = "ExistingCard", name="ExistingCard", description = "existing card dto")
public class ExistingCardDTO extends NewCardDTO {
    @Schema(description = "the UUID of the card", example = "dc45c20f-08fc-4170-8e9e-0605aa39fb41")
    @NotNull(message = "UUID-required")
    private UUID uuid;

    public ExistingCardDTO(UUID uuid, String question, String answer) {
        super(question, answer);
        this.uuid = uuid;
    }

}
