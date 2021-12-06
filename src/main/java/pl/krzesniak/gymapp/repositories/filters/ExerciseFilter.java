package pl.krzesniak.gymapp.repositories.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

@Data
@AllArgsConstructor
public class ExerciseFilter {
    private String searchString;
    private ExerciseDifficulty exerciseDifficulty;
    private ExerciseType exerciseType;
}
