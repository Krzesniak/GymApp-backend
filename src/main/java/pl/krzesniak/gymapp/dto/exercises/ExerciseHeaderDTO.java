package pl.krzesniak.gymapp.dto.exercises;

import lombok.Data;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;

import java.util.UUID;

@Data
public class ExerciseHeaderDTO {

    private UUID id;
    private boolean favourite;
    private String name;
    private String urlImage;
    private ExerciseDifficulty exerciseDifficulty;
}
