package pl.krzesniak.gymapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.krzesniak.gymapp.dto.ExerciseDTO;
import pl.krzesniak.gymapp.dto.ExerciseFilterDTO;
import pl.krzesniak.gymapp.dto.ExerciseNotVerifiedDTO;
import pl.krzesniak.gymapp.dto.exercises.*;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.entities.training.ExerciseHints;
import pl.krzesniak.gymapp.entities.training.ExerciseSteps;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(config = CentralConfig.class)
public interface ExerciseMapper {

    default List<String> mapExerciseSteps(Set<ExerciseSteps> value){
      return value.stream()
                .sorted(Comparator.comparingInt(ExerciseSteps::getStepNumber))
                .map(ExerciseSteps::getExerciseStep)
                .collect(Collectors.toList());
    }

    default List<String> map(Set<ExerciseHints> value){
        return value.stream()
                .map(ExerciseHints::getHint)
                .collect(Collectors.toList());
    }


    default Exercise mapToExerciseEntity(CreateExerciseDTO exerciseDTO, String createdBy, LocalDate createdDate){
        Exercise exercise = new Exercise();
        exercise.setExerciseType(exerciseDTO.getExerciseType());
        exercise.setExerciseDifficulty(exerciseDTO.getExerciseDifficulty());
        exercise.setCreatedBy(createdBy);
        exercise.setCreatedDate(createdDate);
        exercise.setEnabled(false);
        exercise.setName(exerciseDTO.getName());
        exercise.setUrlImage(exerciseDTO.getUrlImage());
        exercise.setUrlMovie(exerciseDTO.getUrlMovie());
        Set<ExerciseSteps> exerciseStepsSet = new HashSet<>();
        for(int i=0; i<exerciseDTO.getSteps().size(); i++){
            ExerciseSteps exerciseSteps = new ExerciseSteps();
            exerciseSteps.setExercise(exercise);
            exerciseSteps.setExerciseStep(exerciseDTO.getSteps().get(i).getName());
            exerciseSteps.setStepNumber(i+1);
            exerciseStepsSet.add(exerciseSteps);
        }
        exercise.setSteps(exerciseStepsSet);
        Set<ExerciseHints> hints = exerciseDTO.getHints()
                .stream()
                .map(step -> {
                    ExerciseHints exerciseHint = new ExerciseHints();
                    exerciseHint.setExercise(exercise);
                    exerciseHint.setHint(step.getName());
                    return exerciseHint;
                })
                .collect(Collectors.toSet());
        exercise.setHints(hints);
        return exercise;
    }




    @Mapping(target = "favourite", ignore = true)
    ExerciseHeaderDTO mapToExerciseHeader(Exercise exercise);

    ExerciseIdWithNameDTO mapToExerciseIdWithNameDTO(Exercise exercise);

    @Mapping(target = "exerciseName", source = "name")
    @Mapping(target = "exerciseID", source = "id")
    ExerciseFilterDTO mapToExeciseFilterDTO(Exercise exercise);


    default ExerciseWithTotalPagesDTO mapToExerciseHeaderWithPageNumberDTO(List<ExerciseHeaderDTO> exercises, int totalPages){
        return new ExerciseWithTotalPagesDTO(exercises, totalPages);
    }

    ExerciseDTO mapToExerciseDTO(Exercise exercise);

    @Mapping(target = "exerciseID", source = "id")
    ExerciseNotVerifiedDTO mapToExerciseNotVerifiedDTO(Exercise exercise);
}
