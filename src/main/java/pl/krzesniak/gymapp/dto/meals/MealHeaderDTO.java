package pl.krzesniak.gymapp.dto.meals;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class MealHeaderDTO {

    private UUID id;
    private String name;
    private String urlImage;
    private String mealDifficulty;
    private Integer calories;
    private String type;
}
