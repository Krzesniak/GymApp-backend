package pl.krzesniak.gymapp.entities.training;

import lombok.*;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;
import pl.krzesniak.gymapp.entities.training.Exercise;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "EXERCISE_HINT")
public class ExerciseHints extends AbstractDefaultEntity {
    private String hint;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exercise exercise;
}
