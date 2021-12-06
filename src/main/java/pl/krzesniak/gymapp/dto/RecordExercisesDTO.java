package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class RecordExercisesDTO {

    private Integer position;
    private String exerciseName;
    private Double weight;
    private LocalTime duration;
    private Integer repetitions;
    private UUID trainingID;
    private String trainingName;

}
