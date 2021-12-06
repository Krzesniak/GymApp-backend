package pl.krzesniak.gymapp.dto.meals;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MealNutrientsDTO {
    private Integer calories;
    private Integer protein;
    private Integer carbohydrate;
    private Integer fat;
    private Integer fiber;
}
