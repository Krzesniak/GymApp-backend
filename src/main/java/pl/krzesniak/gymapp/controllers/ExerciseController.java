package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.ExerciseDTO;
import pl.krzesniak.gymapp.dto.ExerciseFilterDTO;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseHeaderDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;
import pl.krzesniak.gymapp.services.ExerciseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseService.createExercise(exerciseDTO);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable UUID id) {
        return new ResponseEntity<>(exerciseService.getExerciseById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseById(@PathVariable UUID id) {
        exerciseService.deleteExerciseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ExerciseWithTotalPagesDTO>
    findAllExerciseHeaders(@RequestParam(required = false) Integer pageNumber,
                           @RequestParam(required = false) Integer pageSize,
                           @RequestParam(required = false) String searchString,
                           @RequestParam(required = false) ExerciseType exerciseType,
                           @RequestParam(required = false) ExerciseDifficulty exerciseDifficulty) {
        int finalPageNumber = pageNumber != null && pageNumber >= 0 ? pageNumber : 0;
        int finalPageSize = pageSize != null && pageSize >= 0 ? pageSize : 12;
        return new ResponseEntity<>(exerciseService.getExercisesHeader(finalPageNumber, finalPageSize,
                searchString, exerciseType, exerciseDifficulty), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseIdWithNameDTO>> findAllExercises() {
        return new ResponseEntity<>(exerciseService.getAllExercises(), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<ExerciseFilterDTO>> getExercisesWithFilters(
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) ExerciseType exerciseType,
            @RequestParam(required = false) ExerciseDifficulty exerciseDifficulty) {
        return new ResponseEntity<>(
                exerciseService.getFilteredExercises(searchString, exerciseType, exerciseDifficulty), HttpStatus.OK);
    }
}
