package pl.krzesniak.gymapp.record;

import org.springframework.stereotype.Component;
import pl.krzesniak.gymapp.dto.records.MaximumRecordsDTO;
import pl.krzesniak.gymapp.entities.training.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.training.Training;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RecordMaximumFinder {

    public MaximumRecordsDTO getMaximumNumberOfExercisesInTraining(List<ExerciseInTraining> exercises){
        final var maximumRecordsDTO = new MaximumRecordsDTO();
        final var trainings = exercises.stream()
                .map(ExerciseInTraining::getTraining)
                .distinct()
                .collect(Collectors.toList());
        int maximumNumberOfExcersises = 0;
        maximumRecordsDTO.setValue(maximumNumberOfExcersises);
        for(var training: trainings){
             int numberOfExercsisesInTraining = (int) training.getResults().stream()
                    .map(ExerciseInTraining::getExercise)
                    .distinct()
                    .count();
            if(numberOfExercsisesInTraining > maximumNumberOfExcersises){
                maximumNumberOfExcersises =  numberOfExercsisesInTraining;
                maximumRecordsDTO.setTrainingID(training.getId());
                maximumRecordsDTO.setValue(maximumNumberOfExcersises);
            }
        }
        return maximumRecordsDTO;
    }
    public MaximumRecordsDTO getMaximumSeriesNumberInTraining(List<ExerciseInTraining> exercises){

        ExerciseInTraining exerciseInTraining = exercises.stream()
                .max(Comparator.comparing(ExerciseInTraining::getSeriesNumber))
                .orElse(createEmptyExerciseInTraining());
        return new MaximumRecordsDTO(exerciseInTraining.getTraining().getId(), exerciseInTraining.getSeriesNumber());
    }
    public MaximumRecordsDTO getMaximumDurationOfTraining(List<ExerciseInTraining> exercises){
        Training training = exercises.stream()
                .map(ExerciseInTraining::getTraining)
                .filter(exercise -> Objects.nonNull(exercise.getDuration()))
                .max(Comparator.comparing(Training::getDuration))
                .orElse(createEmptyTraining());
        return new MaximumRecordsDTO(training.getId(), training.getDuration());
    }

    public MaximumRecordsDTO getTheMostOftenExercise(List<ExerciseInTraining> exercises) {
         return exercises.stream()
                .collect(Collectors.groupingBy(ExerciseInTraining::getExercise, Collectors.counting()))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                 .map(element -> new MaximumRecordsDTO(null, element.getKey().getName()))
                 .orElse(new MaximumRecordsDTO(null, "Brak"));
    }
    private Training createEmptyTraining(){
      return Training.builder()
                .duration( LocalTime.of(0,0))
                .build();
    }
    private ExerciseInTraining createEmptyExerciseInTraining(){
        return ExerciseInTraining.builder()
                .repetitions(0)
                .weight(0.0)
                .seriesNumber(0)
                .training(createEmptyTraining())
                .build();
    }
}
