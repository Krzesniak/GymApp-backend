package pl.krzesniak.gymapp.dto;

import lombok.Builder;
import lombok.Data;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import java.util.UUID;

@Data
@Builder
public class ExerciseFilterDTO {

    private String exerciseName;
    private UUID exerciseID;
    private ExerciseType exerciseType;
    private ExerciseDifficulty exerciseDifficulty;
}
