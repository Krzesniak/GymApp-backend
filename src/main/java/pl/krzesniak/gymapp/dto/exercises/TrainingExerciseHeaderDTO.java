package pl.krzesniak.gymapp.dto.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingExerciseHeaderDTO {

    private UUID id;
    private String name;
    private String urlImage;
}
