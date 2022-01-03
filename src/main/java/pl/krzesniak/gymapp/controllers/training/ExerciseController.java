package pl.krzesniak.gymapp.controllers.training;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.*;
import pl.krzesniak.gymapp.dto.exercises.CreateExerciseDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.training.Exercise;
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

    @PostMapping("/files")
    public ResponseEntity<UrlImageAndMovieDTO> uploadImageAndMovie(@RequestBody @RequestParam(value = "movie") MultipartFile movie,
                                                                   @RequestBody @RequestParam(value = "image") MultipartFile image) {
        return new ResponseEntity<>(exerciseService.uploadImageAndMovie(movie, image), HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody CreateExerciseDTO exerciseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(exerciseService.createExercise(exerciseDTO, name), HttpStatus.CREATED);
    }

    @GetMapping("/not-verified")
    public ResponseEntity<List<ExerciseNotVerifiedDTO>> getNotVerifiedExercises(){
        return new ResponseEntity<>(exerciseService.getNotVerifiedExercises(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> enableExercise(@PathVariable UUID id) {
        return new ResponseEntity<>(exerciseService.enableExercise(id), HttpStatus.CREATED);
    }

}
