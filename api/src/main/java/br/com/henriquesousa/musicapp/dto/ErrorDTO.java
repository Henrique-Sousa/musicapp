package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(title = "Error", name="Error", description = "an error occurred")
public class ErrorDTO {
    @Schema(description = "the code of the error")
    String code;

    @Schema(description = "a boolean indicating error")
    boolean error = true;
}
