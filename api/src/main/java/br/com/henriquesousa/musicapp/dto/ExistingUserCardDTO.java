package br.com.henriquesousa.musicapp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExistingUserCardDTO {
    private UUID uuid;
    private UUID userUuid;
    private UUID cardUuid;
    private int box;
}
