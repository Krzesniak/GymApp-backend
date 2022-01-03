package pl.krzesniak.gymapp.record;

import io.jsonwebtoken.lang.Assert;
import liquibase.pro.packaged.E;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.krzesniak.gymapp.dto.records.MaximumRecordsDTO;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.entities.training.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.training.Training;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RecordMaximumFinderTest {

    private RecordMaximumFinder recordMaximumFinder = new RecordMaximumFinder();
    private List<ExerciseInTraining> exerciseInTrainingList;

    @BeforeEach
    void init() {
        List<Training> trainings = Stream.iterate(0, i -> i + 1)
                .limit(3)
                .map(it -> {
                    Training training = new Training();
                    training.setDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(it + 1, 0)));
                    training.setDuration(LocalTime.of(it, 0));
                    return training;
                })
                .collect(Collectors.toList());
        List<Exercise> exercises = Stream.iterate(0, i -> i + 1)
                .limit(5)
                .map(it -> {
                    Exercise exercise = new Exercise();
                    exercise.setName("exerciseName" + it);
                    return exercise;
                })
                .collect(Collectors.toList());
        ExerciseInTraining exerciseInTraining = new ExerciseInTraining(
                exercises.get(0), trainings.get(0), 1, 40.0, 40, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining2 = new ExerciseInTraining(
                exercises.get(1), trainings.get(0), 1, 50.0, 2, LocalTime.of(0, 15)
        );
        ExerciseInTraining exerciseInTraining3 = new ExerciseInTraining(
                exercises.get(2), trainings.get(0), 1, 20.0, 5, LocalTime.of(0, 5)
        );
        ExerciseInTraining exerciseInTraining4 = new ExerciseInTraining(
                exercises.get(3), trainings.get(0), 1, 40.0, 40, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining5 = new ExerciseInTraining(
                exercises.get(4), trainings.get(0), 1, 40.0, 40, LocalTime.of(1, 10)
        );
        ExerciseInTraining exerciseInTraining6 = new ExerciseInTraining(
                exercises.get(2), trainings.get(1), 1, 40.0, 40, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining7 = new ExerciseInTraining(
                exercises.get(3), trainings.get(2), 1, 40.0, 40, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining8 = new ExerciseInTraining(
                exercises.get(4), trainings.get(2), 1, 40.0, 40, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining9 = new ExerciseInTraining(
                exercises.get(4), trainings.get(2), 2, 45.0, 20, LocalTime.of(0, 10)
        );
        ExerciseInTraining exerciseInTraining10 = new ExerciseInTraining(
                exercises.get(4), trainings.get(2), 3, 40.0, 40, LocalTime.of(0, 10)
        );
        exerciseInTraining.setId(UUID.randomUUID());
        exerciseInTraining2.setId(UUID.randomUUID());
        exerciseInTraining3.setId(UUID.randomUUID());
        exerciseInTraining4.setId(UUID.randomUUID());
        exerciseInTraining5.setId(UUID.randomUUID());
        exerciseInTraining6.setId(UUID.randomUUID());
        exerciseInTraining7.setId(UUID.randomUUID());
        exerciseInTraining8.setId(UUID.randomUUID());
        exerciseInTraining9.setId(UUID.randomUUID());
        exerciseInTraining10.setId(UUID.randomUUID());

        trainings.get(0).setResults(List.of(exerciseInTraining, exerciseInTraining2, exerciseInTraining3, exerciseInTraining4, exerciseInTraining5));
        trainings.get(1).setResults(List.of(exerciseInTraining6));
        trainings.get(2).setResults(List.of(exerciseInTraining7, exerciseInTraining8, exerciseInTraining9, exerciseInTraining10));
        exerciseInTrainingList = new ArrayList<>();
        exerciseInTrainingList.add(exerciseInTraining);
        exerciseInTrainingList.add(exerciseInTraining2);
        exerciseInTrainingList.add(exerciseInTraining3);
        exerciseInTrainingList.add(exerciseInTraining4);
        exerciseInTrainingList.add(exerciseInTraining5);
        exerciseInTrainingList.add(exerciseInTraining6);
        exerciseInTrainingList.add(exerciseInTraining7);
        exerciseInTrainingList.add(exerciseInTraining8);
        exerciseInTrainingList.add(exerciseInTraining9);
        exerciseInTrainingList.add(exerciseInTraining10);
    }

    @Test
    public void shouldReturnMaximumNumberOfExercisesInTraining() {
        MaximumRecordsDTO maximumNumberOfExercisesInTraining =
                recordMaximumFinder.getMaximumNumberOfExercisesInTraining(exerciseInTrainingList);
        assertEquals(5, maximumNumberOfExercisesInTraining.getValue());
    }

    @Test
    public void shouldReturnMaximumSeriesNumberInTraining() {
        MaximumRecordsDTO maximumSeriesNumberInTraining =
                recordMaximumFinder.getMaximumSeriesNumberInTraining(exerciseInTrainingList);
        assertEquals(3, maximumSeriesNumberInTraining.getValue());
    }

    @Test
    public void shouldReturnMaximumDurationOfTraining() {
        MaximumRecordsDTO maximumSeriesNumberInTraining =
                recordMaximumFinder.getMaximumDurationOfTraining(exerciseInTrainingList);
        assertEquals(LocalTime.of(2, 0), maximumSeriesNumberInTraining.getValue());
    }

    @Test
    public void shouldReturnTheMostOftenDoExercise() {
        MaximumRecordsDTO theMostOftenExercise = recordMaximumFinder.
                getTheMostOftenExercise(exerciseInTrainingList);
        assertEquals("exerciseName4", theMostOftenExercise.getValue());
    }
}