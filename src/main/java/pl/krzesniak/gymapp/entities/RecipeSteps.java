package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeSteps extends AbstractDefaultEntity{

    @Column(name ="MEAL_ID")
    private UUID meal;

    private Integer rowNumber;

    private String instructionStep;
}
