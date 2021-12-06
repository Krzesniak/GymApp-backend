package pl.krzesniak.gymapp.dto;

import lombok.Data;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;

import java.util.UUID;

@Data
public class DietDayCreateRequest {

    private UUID breakfast;
    private UUID secondBreakfast;
    private UUID mainCourse;
    private UUID dinner;
    private UUID snacks;
}
