package pl.krzesniak.gymapp.mappers;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.krzesniak.gymapp.dto.exercises.TrainingRequestDTO;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.entities.training.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.training.Training;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = CentralConfig.class)
public abstract class TrainingMapperDecorator implements TrainingMapper {

    @Autowired
    @Qualifier("delegate")
    private TrainingMapper delegate;

    @Override
    public Training mapToUpdatedTraining(Training training, User user, List<Exercise> exercises, TrainingRequestDTO trainingDTO) {
        delegate.mapToUpdatedTraining(training, user, exercises, trainingDTO);
        training.setDuration(trainingDTO.getDuration());
        training.setUrlImage(trainingDTO.getTrainingType().toLowerCase());
        List<ExerciseInTraining> resultsList = makeExerciseInTraining(training, exercises, trainingDTO);
        training.setResults(resultsList);
        return training;
    }

    @Override
    public Training mapToCreatedTraining(TrainingRequestDTO trainingDTO, User user, List<Exercise> exercises) {
        Training training = delegate.mapToCreatedTraining(trainingDTO, user, exercises);
        training.setDate(LocalDateTime.of(trainingDTO.getDate(), trainingDTO.getHour()));
        training.setDuration(trainingDTO.getDuration());
        training.setUrlImage(trainingDTO.getTrainingType().toLowerCase());
        List<ExerciseInTraining> resultsList = makeExerciseInTraining(training, exercises, trainingDTO);
        training.setResults(resultsList);
        return training;
    }

    private List<ExerciseInTraining> makeExerciseInTraining(Training training, List<Exercise> exercises, TrainingRequestDTO trainingDTO) {
        return trainingDTO.getExerciseResults().stream()
                .map(resultDTO -> {
                    Exercise foundExercise = exercises.stream()
                            .filter(exercise -> exercise.getName().equals(resultDTO.getName()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundExerciseException(""));
                    List<ExerciseInTraining> results = new ArrayList<>();
                    for (int i = 0; i < resultDTO.getSeries().size(); i++) {
                        ExerciseInTraining result = new ExerciseInTraining();
                        result.setTraining(training);
                        result.setExercise(foundExercise);
                        result.setRepetitions(resultDTO.getSeries().get(i).getRepetitions());
                        result.setWeight(resultDTO.getSeries().get(i).getWeight());
                        result.setSeriesNumber(i+1);
                        results.add(result);
                    }
                    return results;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


}
