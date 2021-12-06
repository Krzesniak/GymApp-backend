package pl.krzesniak.gymapp.mappers;

import org.hibernate.annotations.Source;
import org.hibernate.annotations.Target;
import org.mapstruct.*;
import pl.krzesniak.gymapp.dto.ExerciseResultDTO;
import pl.krzesniak.gymapp.dto.ResultDTO;
import pl.krzesniak.gymapp.dto.TrainingDTO;
import pl.krzesniak.gymapp.dto.TrainingDetailsDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingExerciseHeaderDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingRequestDTO;
import pl.krzesniak.gymapp.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Mapper(config = CentralConfig.class)
@DecoratedWith(TrainingMapperDecorator.class)
public interface TrainingMapper {


    @Mapping(target = "title", source = "name")
    TrainingDTO mapToTrainingDTO(Training training);

    List<TrainingDTO> mapToTrainingDTO(List<Training> trainings);

    @Mapping(target = "exerciseResults", source = "trainingExercises", qualifiedByName = "mapExercisesResults")
    TrainingDetailsDTO mapToTrainingDetailsDTO(Training training, List<ExerciseInTraining> trainingExercises);

    ResultDTO mapToResultDTO(ExerciseInTraining exerciseInTraining);
    List<ResultDTO> mapToResultDTO(List<ExerciseInTraining> exerciseInTraining);


    @Named("mapExercisesResults")
    default List<ExerciseResultDTO> mapExercisesResults(List<ExerciseInTraining> trainingExercises){
        Map<TrainingExerciseHeaderDTO, List<ResultDTO>> results = trainingExercises.stream()
                .collect(groupingBy(element -> new TrainingExerciseHeaderDTO(element.getExercise().getId(),
                        element.getExercise().getName(), element.getExercise().getUrlImage()), collectingAndThen(toList(), this::mapToResultDTO
                )));
        return results.entrySet()
                .stream()
                .map(keyValue -> {
                    ExerciseResultDTO exerciseResultDTO = new ExerciseResultDTO();
                    exerciseResultDTO.setId(keyValue.getKey().getId());
                    exerciseResultDTO.setName(keyValue.getKey().getName());
                    exerciseResultDTO.setUrlImage(keyValue.getKey().getUrlImage());
                    exerciseResultDTO.setResults(keyValue.getValue());
                    return exerciseResultDTO;
                })
                .collect(Collectors.toList());
    }

    @Mapping(target = "user", source = "user")
    @Mapping(target = "results", ignore = true)
    @Mapping(target = "id", ignore = true)
    Training mapToUpdatedTraining(@MappingTarget Training training, User user, List<Exercise> exercises, TrainingRequestDTO trainingDTO);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "results", ignore = true)
    Training mapToCreatedTraining(TrainingRequestDTO trainingDTO, User user, List<Exercise> exercises);

}
