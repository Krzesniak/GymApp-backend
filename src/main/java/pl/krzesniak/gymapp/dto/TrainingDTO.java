package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class TrainingDTO {

    private UUID id;
    private LocalDate date;
    private String duration;
    private String title;
}
