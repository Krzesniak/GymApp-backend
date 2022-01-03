package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.dto.exercises.ExerciseHintAndStepDTO;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {

    private String name;
    private String urlMovie;
    private String urlImage;
    private ExerciseType exerciseType;
    private ExerciseDifficulty exerciseDifficulty;
    private List<String> hints;
    private List<String> steps;
}
