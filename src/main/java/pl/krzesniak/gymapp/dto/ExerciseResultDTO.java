package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ExerciseResultDTO {

    private UUID id;
    private String name;
    private String urlImage;
    private List<ResultDTO> results;
}
