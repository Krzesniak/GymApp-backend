package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseNotVerifiedDTO {

    private UUID exerciseID;
    private String urlImage;
    private LocalDate createdDate;
    private String createdBy;
    private String name;
}
