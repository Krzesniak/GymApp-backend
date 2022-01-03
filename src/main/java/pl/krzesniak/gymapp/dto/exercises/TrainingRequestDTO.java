package pl.krzesniak.gymapp.dto.exercises;

import lombok.Data;
import pl.krzesniak.gymapp.dto.ExerciseResultDTO;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class TrainingRequestDTO {
    private LocalDate date;
    private LocalTime hour;
    private LocalTime duration;
    private String name;
    private String trainingType;
    private String description;
    private String note;
    private List<ExerciseSeriesRequestDTO> exerciseResults;
}
