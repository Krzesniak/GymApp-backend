package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DietSumIngredientsDTO {
    private int calories;
    private int protein;
    private int carbohydrate;
    private int fat;
    private int fiber;
}
