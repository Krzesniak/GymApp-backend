package pl.krzesniak.gymapp.entities.ids;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.UUID;

@Data
public class MealIngredientID implements Serializable {

    @Column(name = "MEAL_ID")
    private UUID mealID;
    @Column(name = "INGREDIENT_ID")
    private UUID ingredientID;
}
