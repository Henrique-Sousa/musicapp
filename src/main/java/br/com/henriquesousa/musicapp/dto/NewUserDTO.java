package br.com.henriquesousa.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // cria getters e setters
public class NewUserDTO {
    private String name;
    private String userName;
}
