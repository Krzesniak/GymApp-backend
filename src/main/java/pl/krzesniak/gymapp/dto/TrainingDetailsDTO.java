package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class TrainingDetailsDTO {

    private UUID id;
    private LocalDate date;
    private String duration;
    private String name;
    private String trainingType;
    private String description;
    private String note;
    private String urlImage;
 //   private Map<TrainingExerciseHeaderDTO, List<ResultDTO>> exerciseNameToResult;
    private List<ExerciseResultDTO> exerciseResults;
}
