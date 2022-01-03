package pl.krzesniak.gymapp.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.*;
import pl.krzesniak.gymapp.dto.exercises.CreateExerciseDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseHeaderDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseWithTotalPagesDTO;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.entities.training.ExerciseHints;
import pl.krzesniak.gymapp.entities.training.ExerciseSteps;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;
import pl.krzesniak.gymapp.mappers.ExerciseMapper;
import pl.krzesniak.gymapp.repositories.ExerciseHintsRepository;
import pl.krzesniak.gymapp.repositories.ExerciseRepository;
import pl.krzesniak.gymapp.repositories.ExerciseStepsRepository;
import pl.krzesniak.gymapp.repositories.filters.ExerciseFilter;
import pl.krzesniak.gymapp.repositories.specification.ExerciseSpecification;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseStepsRepository exerciseStepsRepository;
    private final ExerciseHintsRepository exerciseHintsRepository;
    private final ExerciseMapper exerciseMapper;

    @Transactional
    public Exercise createExercise(CreateExerciseDTO exerciseDTO, String name) {
        log.info("Creating an exercise ");
        Exercise exercise = exerciseMapper.mapToExerciseEntity(exerciseDTO, name, LocalDate.now());
        Set<ExerciseSteps> steps = exercise.getSteps();
        Set<ExerciseHints> hints = exercise.getHints();
        exercise.setHints(null);
        exercise.setSteps(null);
        exerciseRepository.save(exercise);
        exerciseStepsRepository.saveAll(steps);
        exerciseHintsRepository.saveAll(hints);
        return exercise;
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
        Set<ExerciseSteps> steps = exercise.getSteps();
        Set<ExerciseHints> hints = exercise.getHints();
        exerciseHintsRepository.deleteAll(hints);
        exerciseStepsRepository.deleteAll(steps);
        exerciseRepository.delete(exercise);
    }

    public ExerciseWithTotalPagesDTO getExercisesHeader(int pageNumber, int pageSize, String searchString, ExerciseType exerciseType, ExerciseDifficulty exerciseDifficulty) {
        ExerciseFilter exerciseFilter = new ExerciseFilter(searchString, exerciseDifficulty, exerciseType);
        var exercisesPage = exerciseRepository.findAll(new ExerciseSpecification(exerciseFilter), PageRequest.of(pageNumber, pageSize));
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

    public UrlImageAndMovieDTO uploadImageAndMovie(MultipartFile movie, MultipartFile image) {
        String imageName = UUID.randomUUID() + image.getOriginalFilename();
        String movieName = UUID.randomUUID() + movie.getOriginalFilename();
        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=krzesniakowo;AccountKey=25lw//dy4LGB6PuGf8Ft6PqWwCcKycGJT94ns6FQMeAJ+vvSswesJUsQtn2gn/otjKWtcAa4VyZk6Z+4Y2Eb4w==;EndpointSuffix=core.windows.net")
                .containerName("exercises")
                .buildClient();
        BlobContainerClient container2 = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=krzesniakowo;AccountKey=25lw//dy4LGB6PuGf8Ft6PqWwCcKycGJT94ns6FQMeAJ+vvSswesJUsQtn2gn/otjKWtcAa4VyZk6Z+4Y2Eb4w==;EndpointSuffix=core.windows.net")
                .containerName("exercises-movies")
                .buildClient();

        BlobClient blob = container.getBlobClient(imageName);
        BlobClient blob2 = container2.getBlobClient(movieName);

        try {
            blob.upload(image.getInputStream(), image.getSize(), true);
            blob2.upload(movie.getInputStream(), movie.getSize(), true);
            return new UrlImageAndMovieDTO("https://krzesniakowo.blob.core.windows.net/exercises/" + imageName,
                    "https://krzesniakowo.blob.core.windows.net/exercises-movies/" + movieName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ExerciseNotVerifiedDTO> getNotVerifiedExercises() {
        List<Exercise> exercises = exerciseRepository.findAllByEnabled(false);
        return exercises
                .stream()
                .map(exerciseMapper::mapToExerciseNotVerifiedDTO)
                .collect(Collectors.toList());
    }

    public ExerciseDTO enableExercise(UUID id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundExerciseException(id.toString()));
        exercise.setEnabled(true);
        exerciseRepository.save(exercise);
        return exerciseMapper.mapToExerciseDTO(exercise);
    }
}
