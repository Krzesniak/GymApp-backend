package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.PieChartExerciseTypeDTO;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.ProgressChartDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.enums.ExerciseType;
import pl.krzesniak.gymapp.services.ExerciseStatisticsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise/statistics")
public class ExerciseStatisticsController {

    private final ExerciseStatisticsService exerciseStatisticsService;

    @GetMapping("/trainings-number")
    public ResponseEntity<int[]> getNumberOfTrainingsByYear(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseStatisticsService.getNumberOfTrainingsByYear(name, localDate), HttpStatus.OK);
    }

    @GetMapping("/exercise-types")
    public ResponseEntity<List<PieChartExerciseTypeDTO>> getNumberOfExerciseGroupedByBodyPart(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        return new ResponseEntity<>(exerciseStatisticsService.getNumberOfExerciseGroupedByBodyPart(name, localDate), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<ProgressChartDTO> getExercisesStatisticsById(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseStatisticsService.getExerciseStatisticsById(id, name), HttpStatus.OK);
    }
}
