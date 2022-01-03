package pl.krzesniak.gymapp.controllers.training;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.PieChartExerciseTypeDTO;
import pl.krzesniak.gymapp.dto.ProgressChartDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingsMadeAndToDoDTO;
import pl.krzesniak.gymapp.services.ExerciseStatisticsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise/statistics")
public class ExerciseStatisticsController {

    private final ExerciseStatisticsService exerciseStatisticsService;

    @ApiOperation(value = "Get the total amount of workouts performed for each month of the given year")
    @GetMapping("/trainings-number")
    public ResponseEntity<int[]> getNumberOfTrainingsByYear(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseStatisticsService.getNumberOfTrainingsByYear(name, localDate), HttpStatus.OK);
    }

    @GetMapping("/exercise-types")
    @ApiOperation(value = "Get information on the number of exercises performed for a given muscle part in a given month")
    public ResponseEntity<List<PieChartExerciseTypeDTO>> getNumberOfExerciseGroupedByBodyPart(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        return new ResponseEntity<>(exerciseStatisticsService.getNumberOfExerciseGroupedByBodyPart(name, localDate), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get information about the recorded results for the given exercise")
    public ResponseEntity<ProgressChartDTO> getExercisesStatisticsById(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseStatisticsService.getExerciseStatisticsById(id, name), HttpStatus.OK);
    }

    @GetMapping("/done")
    public ResponseEntity<TrainingsMadeAndToDoDTO> getTrainingsMadeAndToDo(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseStatisticsService.getTrainingsMadeAndToDo(name, date), HttpStatus.OK);

    }
}
