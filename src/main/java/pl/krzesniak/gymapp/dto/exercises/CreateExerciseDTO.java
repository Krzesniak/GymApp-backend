package pl.krzesniak.gymapp.dto.exercises;

import lombok.Data;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import java.util.List;

@Data
public class CreateExerciseDTO {
    private String name;
    private String urlMovie;
    private String urlImage;
    private ExerciseType exerciseType;
    private ExerciseDifficulty exerciseDifficulty;
    private List<ExerciseHintAndStepDTO> hints;
    private List<ExerciseHintAndStepDTO> steps;
}
