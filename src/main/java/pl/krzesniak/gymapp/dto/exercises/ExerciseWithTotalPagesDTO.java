package pl.krzesniak.gymapp.dto.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExerciseWithTotalPagesDTO {

    private List<ExerciseHeaderDTO> exercises;
    private Integer totalPages;
}
