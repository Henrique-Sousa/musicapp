package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
public class ExistingUserDTO {
    private UUID uuid;
    private String name;
    private String userName;
}
