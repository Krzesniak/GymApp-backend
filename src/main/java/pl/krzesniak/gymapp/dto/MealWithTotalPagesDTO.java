package pl.krzesniak.gymapp.dto;

import lombok.Data;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;

import java.util.List;

@Data
public class MealWithTotalPagesDTO {

    private List<MealHeaderDTO> meals;
    private Integer totalPages;
}
