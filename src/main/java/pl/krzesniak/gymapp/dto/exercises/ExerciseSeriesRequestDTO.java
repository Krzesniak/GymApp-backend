package pl.krzesniak.gymapp.dto.exercises;

import lombok.Data;
import pl.krzesniak.gymapp.dto.ResultDTO;

import java.util.List;

@Data
public class ExerciseSeriesRequestDTO {

    private String name;
    private List<ResultDTO> series;

}
