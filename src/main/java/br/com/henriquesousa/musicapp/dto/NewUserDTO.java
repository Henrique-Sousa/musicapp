package br.com.henriquesousa.musicapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
public class NewUserDTO {
    @NotBlank(message = "name-required")
    private String name;
    @NotBlank(message = "username-required")
    private String userName;
}
