package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.DashboardSummaryDTO;
import pl.krzesniak.gymapp.entities.diet.Diet;
import pl.krzesniak.gymapp.entities.training.Training;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.entities.user.UserMeasurement;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.repositories.DietRepository;
import pl.krzesniak.gymapp.repositories.TrainingRepository;
import pl.krzesniak.gymapp.repositories.UserMeasurementRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final TrainingRepository trainingRepository;
    private final DietRepository dietRepository;
    private final UserMeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public DashboardSummaryDTO getSummary(LocalDate date, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("username"));
        final var startDate = date.withDayOfMonth(1).atTime(0, 0);
        final var endDate = date.withDayOfMonth(date.lengthOfMonth()).atTime(0, 0);
        var trainings = trainingRepository.findByUser(user);
        Map<Boolean, List<Training>> partitionedByDate = trainings.stream()
                .collect(partitioningBy(
                        training -> training.getDate().toLocalDate().isBefore(date)));
        LocalDate incomingTrainingDate = partitionedByDate.get(false).stream()
                .map(training -> training.getDate().toLocalDate())
                .min(LocalDate::compareTo)
                .orElse(null);
        LocalDate latestTrainingDate = partitionedByDate.get(true)
                .stream()
                .map(training -> training.getDate().toLocalDate())
                .max(LocalDate::compareTo)
                .orElse(null);
        var foundDiet = dietRepository.findByUser(user);
        Map<Boolean, List<Diet>> diets = foundDiet.stream()
                .collect(partitioningBy(diet -> diet.getDietDate().isBefore(date)));
        LocalDate incomingDietDate = diets.get(false)
                .stream()
                .map(Diet::getDietDate)
                .min(LocalDate::compareTo)
                .orElse(null);
        LocalDate latestDietDate = diets.get(true)
                .stream()
                .map(Diet::getDietDate)
                .max(LocalDate::compareTo)
                .orElse(null);
        List<UserMeasurement> measurements = measurementRepository.findByUsername(username);
        LocalDate latestMeasurement = measurements.stream()
                .map(UserMeasurement::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
        return new DashboardSummaryDTO(incomingTrainingDate, latestTrainingDate,
                incomingDietDate, latestDietDate, latestMeasurement);
    }
}
