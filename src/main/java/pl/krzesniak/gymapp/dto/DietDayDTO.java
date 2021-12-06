package pl.krzesniak.gymapp.dto;

import lombok.Data;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;

@Data
public class DietDayDTO {

    private MealDetailsDTO breakfast;
    private MealDetailsDTO secondBreakfast;
    private MealDetailsDTO mainCourse;
    private MealDetailsDTO dinner;
    private MealDetailsDTO snacks;

}
