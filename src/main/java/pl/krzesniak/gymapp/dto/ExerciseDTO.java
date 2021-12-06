package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {

    private String name;
    private String urlMovie;
    private String urlImage;
    private String exerciseType;
    private String exerciseDifficulty;
    private List<String> hints;
    private List<String> steps;
}
