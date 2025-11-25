package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(title = "NewCard", name="NewCard", description = "new card dto")
public class NewCardDTO {
    @Schema(description = "the question on the front side of the card", example = "fifth of C")
    @NotBlank(message = "question-required")
    private String question;

    @Schema(description = "the answer on the back side of the card", example = "G")
    @NotBlank(message = "answer-required")
    private String answer;
}
