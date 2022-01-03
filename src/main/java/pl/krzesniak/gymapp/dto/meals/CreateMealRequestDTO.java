package pl.krzesniak.gymapp.dto.meals;

import lombok.Data;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;

import java.time.LocalTime;
import java.util.List;

@Data
public class CreateMealRequestDTO {
    private String name;
    private LocalTime time;
    private MealDifficulty mealDifficulty;
    private MealType mealType;
    private String urlImage;
    private Integer fat;
    private Integer fiber;
    private Integer carbohydrate;
    private Integer calories;
    private Integer protein;
    private List<CreateRecipeStepsDTO> recipeSteps;
    private List<CreateIngredientDetailsDTO> mealIngredients;

}
