package br.com.henriquesousa.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardRequestDTO {
    private String question;
    private String answer;
}
