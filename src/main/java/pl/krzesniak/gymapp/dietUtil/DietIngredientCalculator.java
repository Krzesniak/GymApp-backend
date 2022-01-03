package pl.krzesniak.gymapp.dietUtil;

import lombok.RequiredArgsConstructor;
import pl.krzesniak.gymapp.dto.DietSumIngredientsDTO;
import pl.krzesniak.gymapp.entities.diet.Diet;

@RequiredArgsConstructor
public class DietIngredientCalculator {

    private final Diet diet;

    public DietSumIngredientsDTO getSumOfIngredients(){
        return new DietSumIngredientsDTO(getSumOfCalories(), getSumOfProteins(),
                getSumOfCarbohydrates(), getSumOfFat(), getSumOfFiber());
    }

    public int getSumOfCalories(){
        int sum = 0;
        if(diet.getBreakfast() != null ) sum += diet.getBreakfast().getMealNutrient().getCalories();
        if(diet.getSecondBreakfast() != null ) sum += diet.getSecondBreakfast().getMealNutrient().getCalories();
        if(diet.getMainCourse() != null ) sum += diet.getMainCourse().getMealNutrient().getCalories();
        if(diet.getDinner() != null ) sum += diet.getDinner().getMealNutrient().getCalories();
        if(diet.getSnacks() != null ) sum +=  diet.getSnacks().getMealNutrient().getCalories();
        return sum;
    }

    public int getSumOfProteins(){
        int sum = 0;
        if(diet.getBreakfast() != null ) sum += diet.getBreakfast().getMealNutrient().getProtein();
        if(diet.getSecondBreakfast() != null ) sum += diet.getSecondBreakfast().getMealNutrient().getProtein();
        if(diet.getMainCourse() != null ) sum += diet.getMainCourse().getMealNutrient().getProtein();
        if(diet.getDinner() != null ) sum += diet.getDinner().getMealNutrient().getProtein();
        if(diet.getSnacks() != null ) sum +=  diet.getSnacks().getMealNutrient().getProtein();
        return sum;
    }

    public int getSumOfCarbohydrates(){
        int sum = 0;
        if(diet.getBreakfast() != null ) sum += diet.getBreakfast().getMealNutrient().getCarbohydrate();
        if(diet.getSecondBreakfast() != null ) sum += diet.getSecondBreakfast().getMealNutrient().getCarbohydrate();
        if(diet.getMainCourse() != null ) sum += diet.getMainCourse().getMealNutrient().getCarbohydrate();
        if(diet.getDinner() != null ) sum += diet.getDinner().getMealNutrient().getCarbohydrate();
        if(diet.getSnacks() != null ) sum +=  diet.getSnacks().getMealNutrient().getCarbohydrate();
        return sum;
    }

    public int getSumOfFat(){
        int sum = 0;
        if(diet.getBreakfast() != null ) sum += diet.getBreakfast().getMealNutrient().getFat();
        if(diet.getSecondBreakfast() != null ) sum += diet.getSecondBreakfast().getMealNutrient().getFat();
        if(diet.getMainCourse() != null ) sum += diet.getMainCourse().getMealNutrient().getFat();
        if(diet.getDinner() != null ) sum += diet.getDinner().getMealNutrient().getFat();
        if(diet.getSnacks() != null ) sum +=  diet.getSnacks().getMealNutrient().getFat();
        return sum;
    }

    public int getSumOfFiber(){
        int sum = 0;
        if(diet.getBreakfast() != null ) sum += diet.getBreakfast().getMealNutrient().getFiber();
        if(diet.getSecondBreakfast() != null ) sum += diet.getSecondBreakfast().getMealNutrient().getFiber();
        if(diet.getMainCourse() != null ) sum += diet.getMainCourse().getMealNutrient().getFiber();
        if(diet.getDinner() != null ) sum += diet.getDinner().getMealNutrient().getFiber();
        if(diet.getSnacks() != null ) sum +=  diet.getSnacks().getMealNutrient().getFiber();
        return sum;
    }
}
