package pl.krzesniak.gymapp.dietUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.krzesniak.gymapp.entities.diet.Diet;
import pl.krzesniak.gymapp.entities.diet.Meal;
import pl.krzesniak.gymapp.entities.diet.MealIngredient;
import pl.krzesniak.gymapp.entities.diet.MealNutrient;

import static org.junit.jupiter.api.Assertions.*;

class DietIngredientCalculatorTest {

    private DietIngredientCalculator dietIngredientCalculator;

    @BeforeEach
    void setUpDiet(){
        Diet diet = new Diet();
        Meal meal1 = new Meal();
        MealNutrient mealNutrients = new MealNutrient();
        mealNutrients.setCalories(30);
        mealNutrients.setCarbohydrate(50);
        mealNutrients.setFat(40);
        mealNutrients.setProtein(60);
        mealNutrients.setFiber(60);
        meal1.setMealNutrient(mealNutrients);
        Meal meal2 = new Meal();
        MealNutrient mealNutrient2 = new MealNutrient();
        mealNutrient2.setCalories(70);
        mealNutrient2.setCarbohydrate(150);
        mealNutrient2.setFat(70);
        mealNutrient2.setProtein(120);
        mealNutrient2.setFiber(80);
        meal2.setMealNutrient(mealNutrient2);
        diet.setBreakfast(meal1);
        diet.setSecondBreakfast(meal2);
        diet.setDinner(meal1);
        diet.setMainCourse(meal2);
        diet.setSnacks(meal1);
        dietIngredientCalculator = new DietIngredientCalculator(diet);
    }

    @Test
    void shouldReturnSumOfCalories() {
        assertEquals(260, dietIngredientCalculator.getSumOfFat());
    }

    @Test
    void shouldReturnSumOfProteins() {
        assertEquals(420, dietIngredientCalculator.getSumOfProteins());
    }

    @Test
    void shouldReturnSumOfCarbohydrates() {
        assertEquals(450, dietIngredientCalculator.getSumOfCarbohydrates());
    }

    @Test
    void shouldReturnSumOfFat() {
        assertEquals(260, dietIngredientCalculator.getSumOfFat());
    }

    @Test
    void shouldReturnSumOfFiber() {
        assertEquals(340, dietIngredientCalculator.getSumOfFiber());
    }
}