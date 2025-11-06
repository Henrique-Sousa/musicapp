package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExistingCardDTO {
    private UUID uuid;
    private String question;
    private String answer;
}
