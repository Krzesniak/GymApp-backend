package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Exercise extends AbstractDefaultEntity {

    private String name;
    private String urlMovie;
    private String urlImage;

    @Column(name = "DIFFICULTY")
    @Enumerated(EnumType.STRING)
    private ExerciseDifficulty exerciseDifficulty;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @ElementCollection
    @CollectionTable(
            name = "EXERCISE_HINT",
            joinColumns = @JoinColumn(name = "EXERCISE_ID")
    )
    @Column(name = "HINT")
    private Set<String> hints = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "EXERCISE_STEP",
            joinColumns = @JoinColumn(name = "EXERCISE_ID")
    )
    @Column(name = "EXERCISE_STEP")
    private Set<String> steps = new HashSet<>();

}
