package pl.krzesniak.gymapp.services;

import liquibase.pro.packaged.T;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.PieChartExerciseTypeDTO;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.ProgressChartDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingsMadeAndToDoDTO;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.entities.training.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.training.Training;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;
import pl.krzesniak.gymapp.repositories.ExerciseRepository;
import pl.krzesniak.gymapp.repositories.ExercisesInTrainingRepository;
import pl.krzesniak.gymapp.repositories.TrainingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ExerciseStatisticsService {

    private final ExercisesInTrainingRepository exercisesInTrainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrainingRepository trainingRepository;

    public int[] getNumberOfTrainingsByYear(String username, LocalDateTime date) {
        LocalDateTime firstDayOfYear = date.toLocalDate().with(firstDayOfYear()).atTime(0, 0);
        LocalDateTime lastDayOfYear = date.toLocalDate().with(lastDayOfYear()).atTime(23, 59);
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
        var start = date.toLocalDate().withDayOfMonth(1).atTime(0, 0);
        var end = date.toLocalDate().withDayOfMonth(date.toLocalDate().lengthOfMonth()).atTime(23, 59);
        List<ExerciseInTraining> exercises = exercisesInTrainingRepository.findByUsernameAndDate(name, start, end);
        var map = exercises.stream()
                .filter(exercise -> exercise.getSeriesNumber() == 1)
                .collect(groupingBy(exercise -> exercise.getExercise().getExerciseType(), counting()));
        return map.entrySet().stream()
                .map(element -> new PieChartExerciseTypeDTO(translateBodyPartIntoPolish(element.getKey().toString()),
                        Math.toIntExact(element.getValue())))
                .collect(toList());
    }

    public ProgressChartDTO getExerciseStatisticsById(UUID exerciseID, String name) {
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new NotFoundExerciseException(exerciseID.toString()));
        List<ExerciseInTraining> exercises = exercisesInTrainingRepository.findByUsernameAndExerciseId(name, exerciseID);
        Map<Training, Optional<ExerciseInTraining>> trainingToMaxWeight = exercises.stream()
                .collect(groupingBy(ExerciseInTraining::getTraining,
                        maxBy(Comparator.comparingDouble(ExerciseInTraining::getWeight))));
        var points = trainingToMaxWeight.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> new PointDTO(entry.getKey().getDate().atZone(ZoneId.of("Europe/Warsaw")).toInstant().toEpochMilli(),
                        entry.getValue().get().getWeight()))
                .collect(toList());
        return new ProgressChartDTO(points, exercise.getName());
    }

    private String translateBodyPartIntoPolish(String englishVersion) {
        switch (englishVersion) {
            case "LEGS":
                return "NOGI";
            case "CHEST":
                return "KLATKA PIERSIOWA";
            case "BICEPS":
                return "BICEPS";
            case "TRICEPS":
                return "TRICEPS";
            case "SHOULDERS":
                return "BARKI";
            case "BACK":
                return "PLECY";
            case "ABS":
                return "BRZUCH";
        }
        return "";
    }

    public TrainingsMadeAndToDoDTO getTrainingsMadeAndToDo(String name, LocalDate date) {
        final var startDate = date.withDayOfMonth(1).atTime(0, 0);
        final var endDate = date.withDayOfMonth(date.lengthOfMonth()).atTime(0, 0);
        List<Training> trainings = trainingRepository.findAllByUserAndDateBetween(name, startDate, endDate);
        Map<Boolean, List<Training>> partitionedByDate = trainings.stream()
                .collect(partitioningBy(
                        training -> training.getDate().toLocalDate().isBefore(LocalDate.now())));
        return new TrainingsMadeAndToDoDTO(
                partitionedByDate.get(Boolean.TRUE).size(),
                partitionedByDate.get(Boolean.FALSE).size());
    }
}
