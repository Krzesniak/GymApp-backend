package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.TrainingDTO;
import pl.krzesniak.gymapp.dto.TrainingDetailsDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseSeriesRequestDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingRequestDTO;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.entities.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.Training;
import pl.krzesniak.gymapp.entities.User;
import pl.krzesniak.gymapp.exceptions.NotFoundTrainingException;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.mappers.TrainingMapper;
import pl.krzesniak.gymapp.repositories.ExerciseRepository;
import pl.krzesniak.gymapp.repositories.ExercisesInTrainingRepository;
import pl.krzesniak.gymapp.repositories.TrainingRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ExercisesInTrainingRepository exercisesInTrainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    public List<TrainingDTO> getAllTrainingsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Username with id: " + username + " not found!"));
        return trainingMapper.mapToTrainingDTO(trainingRepository.findByUser(user));
    }

    public TrainingDetailsDTO getTrainingDetailsById(UUID trainingID) {
        List<ExerciseInTraining> trainingExercises = exercisesInTrainingRepository.findByTrainingId(trainingID);
        Training training = null;
        if(!trainingExercises.isEmpty()){
            training = trainingExercises.get(0).getTraining();
        }
        return trainingMapper.mapToTrainingDetailsDTO(training, trainingExercises);
    }

    public TrainingDetailsDTO createTraining(TrainingRequestDTO trainingDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Create training, not found user with username: "  + username));
        List<String> exercisesName = trainingDTO.getExerciseResults().stream()
                .map(ExerciseSeriesRequestDTO::getName)
                .collect(Collectors.toList());
        List<Exercise> exercises = exerciseRepository.findAllByNameIn(exercisesName);
        Training training = trainingMapper.mapToCreatedTraining(trainingDTO, user, exercises);
        trainingRepository.save(training);
        return trainingMapper.mapToTrainingDetailsDTO(training, training.getResults());
    }
    @Transactional
    public TrainingDetailsDTO updateTraining(String username, TrainingRequestDTO trainingDTO, UUID trainingID){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Update training, not found user with username: "  + username));
        Training training = trainingRepository.findById(trainingID)
                .orElseThrow(() -> new NotFoundTrainingException("Training with id: " + trainingDTO + " does not exist! "));
        List<String> exercisesName = trainingDTO.getExerciseResults().stream()
                .map(ExerciseSeriesRequestDTO::getName)
                .collect(Collectors.toList());
        exercisesInTrainingRepository.deleteAll(training.getResults());
        training.setResults(null);
        List<Exercise> exercises = exerciseRepository.findAllByNameIn(exercisesName);
        trainingMapper.mapToUpdatedTraining(training, user, exercises, trainingDTO);
        trainingRepository.save(training);
        return null;
    }
}
