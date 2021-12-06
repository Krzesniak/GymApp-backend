package pl.krzesniak.gymapp.dto.meals;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeStepsDTO {

    private Integer rowNumber;
    private String description;
}
