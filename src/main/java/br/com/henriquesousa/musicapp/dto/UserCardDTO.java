package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCardDTO {
    private UUID userUuid;
    private Long cardId;
    private int box;
}
