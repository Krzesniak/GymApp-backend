package pl.krzesniak.gymapp.mappers;

import org.mapstruct.*;
import pl.krzesniak.gymapp.dto.DietDayDTO;
import pl.krzesniak.gymapp.dto.IngredientDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.*;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.diet.*;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;

import java.time.LocalDate;
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

    default Meal mapToMealEntity(CreateMealRequestDTO createMealRequestDTO, List<Ingredient> ingredients, String name){
        Meal meal = new Meal();
        meal.setName(createMealRequestDTO.getName());
        meal.setMealDifficulty(createMealRequestDTO.getMealDifficulty());
        meal.setType(createMealRequestDTO.getMealType());
        meal.setUrlImage(createMealRequestDTO.getUrlImage());
        MealNutrient mealNutrient = new MealNutrient();
        mealNutrient.setCalories(createMealRequestDTO.getCalories());
        mealNutrient.setCarbohydrate(createMealRequestDTO.getCarbohydrate());
        mealNutrient.setFat(createMealRequestDTO.getFat());
        mealNutrient.setFiber(createMealRequestDTO.getFiber());
        mealNutrient.setProtein(createMealRequestDTO.getProtein());
        meal.setMealNutrient(mealNutrient);
        meal.setEnabled(false);
        meal.setCreatedDate(LocalDate.now());
        meal.setCreatedBy(name);
        Set<RecipeSteps> recipeSteps = new HashSet<>();
        for(int i=0; i<createMealRequestDTO.getRecipeSteps().size(); i++){
            RecipeSteps recipeStep = new RecipeSteps();
            recipeStep.setRowNumber(i+1);
            recipeStep.setInstructionStep(createMealRequestDTO.getRecipeSteps().get(i).getName());
            recipeSteps.add(recipeStep);
        }
        meal.setRecipeSteps(recipeSteps);
        Set<MealIngredient> mealIngredients = createMealRequestDTO.getMealIngredients().stream()
                .map(ingredient -> {
                    Ingredient foundIngredient = ingredients.stream()
                            .filter(ing -> ing.getName().equals(ingredient.getName()))
                            .findFirst()
                            .orElseThrow(UnsupportedOperationException::new);
                    MealIngredient mealIngredient = new MealIngredient();
                    mealIngredient.setMeal(meal);
                    mealIngredient.setIngredient(foundIngredient);
                    mealIngredient.setAmountOverall(ingredient.getAmountOverall());
                    mealIngredient.setMeasureUnit(ingredient.getMeasureUnit());
                    mealIngredient.setPreciseAmount(ingredient.getPreciseAmount());
                    MealIngredient.Id id = new MealIngredient.Id();
                    id.setIngredientID(foundIngredient.getId());
                    mealIngredient.setId(id);
                    return mealIngredient;
                })
                .collect(Collectors.toSet());

        meal.setMealIngredients(mealIngredients);
        return meal;
    }

    @Mapping(target = "mealID", source = "id")
    MealsNotVerifiedDTO mapToNotVerifiedMeal(Meal meal);
}
