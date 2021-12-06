package pl.krzesniak.gymapp.mappers;

import org.mapstruct.*;
import pl.krzesniak.gymapp.dto.DietDayDTO;
import pl.krzesniak.gymapp.dto.IngredientDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.dto.meals.MealNutrientsDTO;
import pl.krzesniak.gymapp.dto.meals.RecipeStepsDTO;
import pl.krzesniak.gymapp.entities.Diet;
import pl.krzesniak.gymapp.entities.Meal;
import pl.krzesniak.gymapp.entities.MealIngredient;
import pl.krzesniak.gymapp.entities.MealNutrient;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(config = CentralConfig.class)
public interface MealMapper {

    @Mapping(target = "calories", source = "mealNutrient.calories")
    MealHeaderDTO mapToDietHeader(Meal meal);

    default MealWithTotalPagesDTO mapToMealWithTotalPagesDTO(List<Meal> meal, Integer totalPages) {
        MealWithTotalPagesDTO mealWithTotalPagesDTO = new MealWithTotalPagesDTO();
        List<MealHeaderDTO> meals = meal.stream()
                .map(this::mapToDietHeader)
                .collect(Collectors.toList());
        mealWithTotalPagesDTO.setTotalPages(totalPages);
        mealWithTotalPagesDTO.setMeals(meals);
        return mealWithTotalPagesDTO;
    }

    MealNutrientsDTO mapToMealNutrientsDTO(MealNutrient mealNutrient);

    @ValueMappings({
            @ValueMapping( source = "MAIN_COURSE", target = "OBIAD"),
            @ValueMapping( source = "BREAKFAST", target = "ŚNIADANIE"),
            @ValueMapping( source = "DINNER", target = "KOLACJA"),
            @ValueMapping( source = "STARTER", target = "PRZEKĄSKA"),
    })
    String mapMealTypeToPolish(MealType mealType);

    @ValueMappings({
            @ValueMapping( source = "EASY", target = "Łatwe"),
            @ValueMapping( source = "MEDIUM", target = "ŚREDNIE"),
            @ValueMapping( source = "HARD", target = "TRUDNE")
    })
    String mapMealDifficultyToPolish(MealDifficulty mealDifficulty);


    @Mapping(target = "ingredients", source = "meal", qualifiedByName = "mapIngredients")
    @Mapping(target = "recipeSteps", source = "meal", qualifiedByName = "recipeSteps")
    MealDetailsDTO mapToMealDetails(Meal meal);

    @Named("mapIngredients")
    default Map<String, IngredientDetailsDTO> mapToIngredients(Meal meal) {
        return meal.getMealIngredients().stream()
                .collect(Collectors.toMap(
                        element -> element.getIngredient().getName(),
                        element -> new IngredientDetailsDTO(element.getPreciseAmount() + " " + element.getMeasureUnit(), element.getAmountOverall())
                ));
    }

    DietDayDTO mapToDietDayDTO(Diet diet);

    @Named("recipeSteps")
    default List<RecipeStepsDTO> mapToRecipeStepsDTO(Meal meal) {
        return meal.getRecipeSteps().stream()
                .map(recipeStep -> new RecipeStepsDTO(recipeStep.getRowNumber(), recipeStep.getInstructionStep()))
                .sorted(Comparator.comparing(RecipeStepsDTO::getRowNumber))
                .collect(Collectors.toList());
    }

}
