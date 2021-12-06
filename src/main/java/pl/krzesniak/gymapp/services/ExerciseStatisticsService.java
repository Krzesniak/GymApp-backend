package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.PieChartExerciseTypeDTO;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.ProgressChartDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.entities.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.Training;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;
import pl.krzesniak.gymapp.repositories.ExerciseRepository;
import pl.krzesniak.gymapp.repositories.ExercisesInTrainingRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ExerciseStatisticsService {

    private final ExercisesInTrainingRepository exercisesInTrainingRepository;
    private final ExerciseRepository exerciseRepository;

    public int[] getNumberOfTrainingsByYear(String username, LocalDateTime date) {
        LocalDateTime firstDayOfYear = date.toLocalDate().with(firstDayOfYear()).atTime(0,0);
        LocalDateTime lastDayOfYear = date.toLocalDate().with(lastDayOfYear()).atTime(23,59);
        List<ExerciseInTraining> trainings = exercisesInTrainingRepository.findByUsernameAndDate(username, firstDayOfYear, lastDayOfYear);
        var monthToCounter = trainings.stream()
                .map(ExerciseInTraining::getTraining)
                .distinct()
                .collect(groupingBy(element -> element.getDate().getMonth(), counting()));
        int[] series = new int[12];
        monthToCounter.forEach((key, value) -> series[key.getValue() - 1] = Math.toIntExact(value));
        return series;

    }

    public List<PieChartExerciseTypeDTO> getNumberOfExerciseGroupedByBodyPart(String name, LocalDateTime date) {
        var start = date.toLocalDate().withDayOfMonth(1).atTime(0,0);
        var end = date.toLocalDate().withDayOfMonth(date.toLocalDate().lengthOfMonth()).atTime(23,59);
        List<ExerciseInTraining> exercises = exercisesInTrainingRepository.findByUsernameAndDate(name, start, end);
        var map = exercises.stream()
                .filter(exercise -> exercise.getSeriesNumber() == 1)
                .collect(groupingBy(exercise -> exercise.getExercise().getExerciseType(), counting()));
        return map.entrySet().stream()
                .map(element -> new PieChartExerciseTypeDTO(element.getKey().toString(), Math.toIntExact(element.getValue())))
                .collect(toList());
    }

    public ProgressChartDTO getExerciseStatisticsById(UUID exerciseID, String name) {
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new NotFoundExerciseException(exerciseID.toString()));
        List<ExerciseInTraining> exercises = exercisesInTrainingRepository.findByUsernameAndExerciseId(name, exerciseID);
        Map<Training, Optional<ExerciseInTraining>> trainingToMaxWeight = exercises.stream()
                .collect(groupingBy(ExerciseInTraining::getTraining,
                        maxBy(Comparator.comparingDouble(ExerciseInTraining::getWeight))));
        var points =  trainingToMaxWeight.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> new PointDTO(entry.getKey().getDate().atZone(ZoneId.of("Europe/Warsaw")).toInstant().toEpochMilli(),
                        entry.getValue().get().getWeight()))
                .collect(toList());
        return new ProgressChartDTO(points, exercise.getName());
    }
}
