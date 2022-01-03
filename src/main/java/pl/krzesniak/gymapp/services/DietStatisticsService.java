package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dietUtil.DietIngredientCalculator;
import pl.krzesniak.gymapp.dto.PointDTO;
import pl.krzesniak.gymapp.dto.TrainingMeasurementChartDTO;
import pl.krzesniak.gymapp.entities.diet.Diet;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.entities.user.UserMeasurement;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.repositories.DietRepository;
import pl.krzesniak.gymapp.repositories.UserMeasurementRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietStatisticsService {

    private final UserMeasurementRepository userMeasurementRepository;
    private final DietRepository dietRepository;
    private final UserRepository userRepository;


    public TrainingMeasurementChartDTO getTrainingMeasureForUserByProperty(String name, String measurement) {
        userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundUserException(" name" + name));
        List<UserMeasurement> results = userMeasurementRepository.findByUsername(name);
        List<PointDTO> points = results.stream()
                .filter(userMeasurement -> Objects.nonNull(getProperty(measurement).apply(userMeasurement)))
                .map(element -> new PointDTO(
                        element.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                        getProperty(measurement).apply(element)))
                .collect(Collectors.toList());
        return new TrainingMeasurementChartDTO(points, translateMeasurementIntoPolish(measurement));
    }

    private String translateMeasurementIntoPolish(String measurement) {
        switch (measurement){
            case "weight": return "waga";
            case "height": return "wzrost";
            case "arm" : return "ramię";
            case "neck" : return "kark";
            case "forehand": return "przedramię";
            case "wrist": return "nadgarstek";
            case "chest": return "klatka piersiowa";
            case "waist": return "talia";
            case "thigh": return "udo";
            case "calf": return  "łydka";
            default: return "waga";
        }
    }

    private Function<UserMeasurement, Double> getProperty(String measurement) {
        switch (measurement.toLowerCase()){
            case "weight": return UserMeasurement::getWeight;
            case "height": return UserMeasurement::getHeight;
            case "arm" : return UserMeasurement::getArm;
            case "neck" : return UserMeasurement::getNeck;
            case "forehand": return UserMeasurement::getForehand;
            case "wrist": return UserMeasurement::getWrist;
            case "chest": return UserMeasurement::getChest;
            case "waist": return UserMeasurement::getWaist;
            case "thigh": return UserMeasurement::getThigh;
            case "calf": return  UserMeasurement::getCalf;
            default: return UserMeasurement::getWeight;
        }
    }

    public TrainingMeasurementChartDTO getTrainingNutrientForUserByProperty(String name, String nutrient, LocalDate date) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundUserException(" name" + name));
        final var startDate = date.withDayOfMonth(1);
        final var endDate = date.withDayOfMonth(date.lengthOfMonth());
        List<Diet> diets = dietRepository.findByUsernameAndDate(user.getId(), startDate, endDate);
        List<PointDTO> points = diets.stream()
                .map(diet -> new PointDTO(diet.getDietDate().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
                        getNutrient(nutrient, diet)))
                .collect(Collectors.toList());
        return new TrainingMeasurementChartDTO(points,translateNutrientIntoPolsh(nutrient));
    }

    private String translateNutrientIntoPolsh(String nutrient) {
        switch (nutrient.toLowerCase()){
            case "calories": return  "kalorie";
            case "carbohydrates": return  "węglowodany";
            case "proteins": return  "białko";
            case "fiber": return  "błonnik";
            case "fat": return  "tłuszcze";
            default: return "kalorie";
        }
    }

    private double getNutrient(String nutrient, Diet diet) {

        DietIngredientCalculator ingredientCalculator = new DietIngredientCalculator(diet);

        switch (nutrient.toLowerCase()){
            case "calories": return  ingredientCalculator.getSumOfCalories();
            case "carbohydrates": return  ingredientCalculator.getSumOfCarbohydrates();
            case "proteins": return  ingredientCalculator.getSumOfProteins();
            case "fiber": return  ingredientCalculator.getSumOfFiber();
            case "fat": return  ingredientCalculator.getSumOfFat();
            default: return Integer.valueOf(ingredientCalculator.getSumOfCalories()).doubleValue();
        }
    }
}
