package pl.krzesniak.gymapp.dto.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingsMadeAndToDoDTO {

    private int numberOfTrainingsDone;
    private int numberOfTrainingsToDo;
}
