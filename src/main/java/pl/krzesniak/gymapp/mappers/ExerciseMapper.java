package pl.krzesniak.gymapp.mappers;

import liquibase.pro.packaged.E;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.krzesniak.gymapp.dto.ExerciseDTO;
import pl.krzesniak.gymapp.dto.ExerciseFilterDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseHeaderDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.Exercise;

import java.util.List;

@Mapper(config = CentralConfig.class)
public interface ExerciseMapper {

    ExerciseDTO mapToExerciseDTO(Exercise exercise);

    @Mapping(target = "id", ignore = true)
    Exercise mapToExerciseEntity(ExerciseDTO exerciseDTO);

    @Mapping(target = "favourite", ignore = true)
    ExerciseHeaderDTO mapToExerciseHeader(Exercise exercise);

    ExerciseIdWithNameDTO mapToExerciseIdWithNameDTO(Exercise exercise);

    @Mapping(target = "exerciseName", source = "name")
    @Mapping(target = "exerciseID", source = "id")
    ExerciseFilterDTO mapToExeciseFilterDTO(Exercise exercise);


    default ExerciseWithTotalPagesDTO mapToExerciseHeaderWithPageNumberDTO(List<ExerciseHeaderDTO> exercises, int totalPages){
        return new ExerciseWithTotalPagesDTO(exercises, totalPages);
    }
}
