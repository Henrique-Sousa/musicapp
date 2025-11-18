package br.com.henriquesousa.musicapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(title = "Success", name="Success", description = "tell the user the operation was done with success")
public class SuccessDTO {
    @Schema(description = "a boolean indicating success", example = "true")
    private boolean success = true;
}
