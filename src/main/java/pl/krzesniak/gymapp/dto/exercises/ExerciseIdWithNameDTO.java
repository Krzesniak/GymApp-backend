package pl.krzesniak.gymapp.dto.exercises;

import lombok.Data;

import java.util.UUID;

@Data
public class ExerciseIdWithNameDTO {
    private UUID id;
    private String name;
}
