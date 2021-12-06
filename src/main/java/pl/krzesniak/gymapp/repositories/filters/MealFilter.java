package pl.krzesniak.gymapp.repositories.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;

@Data
@AllArgsConstructor
public class MealFilter {
    private String searchString;
    private MealDifficulty mealDifficulty;
    private MealType mealType;
}
