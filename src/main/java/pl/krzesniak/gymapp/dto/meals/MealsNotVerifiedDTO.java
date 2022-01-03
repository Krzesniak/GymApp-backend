package pl.krzesniak.gymapp.dto.meals;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MealsNotVerifiedDTO {
    private UUID mealID;
    private String urlImage;
    private LocalDate createdDate;
    private String createdBy;
    private String name;
}
