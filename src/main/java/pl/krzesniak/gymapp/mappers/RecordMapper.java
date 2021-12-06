package pl.krzesniak.gymapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.krzesniak.gymapp.dto.RecordExercisesDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.entities.ExerciseInTraining;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(config = CentralConfig.class)
public interface RecordMapper {

    @Mapping(target = "position", ignore = true)
   default List<RecordExercisesDTO> mapToRecordExerciseDTO(Map<String, Optional<ExerciseInTraining>> exerciseMap){
      return exerciseMap.entrySet().stream()
              .filter(element -> element.getValue().isPresent())
               .map(element -> {
                   ExerciseInTraining exerciseInTraining = element.getValue().get();
                   RecordExercisesDTO recordExercisesDTO = new RecordExercisesDTO();
                   recordExercisesDTO.setExerciseName(element.getKey());
                   recordExercisesDTO.setTrainingID(exerciseInTraining.getTraining().getId());
                   recordExercisesDTO.setTrainingName(exerciseInTraining.getTraining().getName());
                   recordExercisesDTO.setWeight(exerciseInTraining.getWeight());
                   recordExercisesDTO.setRepetitions(exerciseInTraining.getRepetitions());
                   recordExercisesDTO.setDuration(exerciseInTraining.getDuration());
                   return recordExercisesDTO;
               })
               .collect(Collectors.toList());
   }
}
