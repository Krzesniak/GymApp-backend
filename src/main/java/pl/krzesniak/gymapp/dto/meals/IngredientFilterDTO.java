package pl.krzesniak.gymapp.dto.meals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientFilterDTO {
    private UUID ingredientID;
    private String ingredientName;
}
