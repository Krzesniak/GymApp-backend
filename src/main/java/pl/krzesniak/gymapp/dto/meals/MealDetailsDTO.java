package pl.krzesniak.gymapp.dto.meals;

import lombok.Data;
import pl.krzesniak.gymapp.dto.IngredientDetailsDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class MealDetailsDTO {

    private UUID id;
    private String name;
    private String urlImage;
    private String mealDifficulty;
    private MealNutrientsDTO mealNutrient;
    private String type;
    private Map<String, IngredientDetailsDTO> ingredients;
    private List<RecipeStepsDTO> recipeSteps;


}
