package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieChartExerciseTypeDTO {

    private String name;
    private int y;
}
