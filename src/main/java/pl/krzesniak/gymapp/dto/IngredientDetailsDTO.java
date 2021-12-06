package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientDetailsDTO {
    private String amount;
    private String overall;
}
