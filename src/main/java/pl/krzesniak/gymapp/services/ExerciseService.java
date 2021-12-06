package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.ExerciseDTO;
import pl.krzesniak.gymapp.dto.ExerciseFilterDTO;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseHeaderDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;
import pl.krzesniak.gymapp.mappers.ExerciseMapper;
import pl.krzesniak.gymapp.repositories.ExerciseRepository;
import pl.krzesniak.gymapp.repositories.filters.ExerciseFilter;
import pl.krzesniak.gymapp.repositories.specification.ExerciseSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public Exercise createExercise(ExerciseDTO exerciseDTO) {
        log.info("Creating an exercise ");
        return exerciseRepository.save(exerciseMapper.mapToExerciseEntity(exerciseDTO));
    }

    public ExerciseDTO getExerciseById(UUID id) {
        log.info("Getting an exercise by ID:  {}", id);
        // TODO ADD with id into log
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundExerciseException("Exercise with id: not found!"));
        return exerciseMapper.mapToExerciseDTO(exercise);
    }

    public void deleteExerciseById(UUID id) {
        log.info("Delete an exercise with ID: {}", id);
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundExerciseException("Exercise with id: not found!"));
        exerciseRepository.delete(exercise);

    }

    public ExerciseWithTotalPagesDTO getExercisesHeader(int pageNumber, int pageSize, String searchString, ExerciseType exerciseType, ExerciseDifficulty exerciseDifficulty) {
        ExerciseFilter exerciseFilter = new ExerciseFilter(searchString, exerciseDifficulty, exerciseType);
        var exercisesPage  = exerciseRepository.findAll(new ExerciseSpecification(exerciseFilter), PageRequest.of(pageNumber, pageSize));
        var totalPages = exercisesPage.getTotalPages();
        List<ExerciseHeaderDTO> exercises = exercisesPage.stream()
                .map(exerciseMapper::mapToExerciseHeader)
                .collect(Collectors.toList());
        return exerciseMapper.mapToExerciseHeaderWithPageNumberDTO(exercises, totalPages);
    }

    public List<ExerciseIdWithNameDTO> getAllExercises() {
        return exerciseRepository.findAll()
                .stream()
                .map(exerciseMapper::mapToExerciseIdWithNameDTO)
                .collect(Collectors.toList());
    }

    public List<ExerciseFilterDTO> getFilteredExercises(String searchString, ExerciseType exerciseType, ExerciseDifficulty exerciseDifficulty) {
        ExerciseFilter exerciseFilter = new ExerciseFilter(searchString, exerciseDifficulty, exerciseType);
        return exerciseRepository.findAll(new ExerciseSpecification(exerciseFilter))
                .stream()
                .map(exerciseMapper::mapToExeciseFilterDTO)
                .collect(Collectors.toList());
    }

}
