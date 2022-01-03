package pl.krzesniak.gymapp.entities.diet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeSteps extends AbstractDefaultEntity {

    private Integer rowNumber;

    private String instructionStep;
}
