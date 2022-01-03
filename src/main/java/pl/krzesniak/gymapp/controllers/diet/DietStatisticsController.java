package pl.krzesniak.gymapp.controllers.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.krzesniak.gymapp.dto.TrainingMeasurementChartDTO;
import pl.krzesniak.gymapp.services.DietStatisticsService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("diets/statistics")
public class DietStatisticsController {

    private final DietStatisticsService dietStatisticsService;

    @GetMapping("/measurement")
    public ResponseEntity<TrainingMeasurementChartDTO> getTrainingMeasure(@RequestParam String measurement) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(dietStatisticsService.getTrainingMeasureForUserByProperty(name, measurement), HttpStatus.OK);
    }

    @GetMapping("/nutrients")
    public ResponseEntity<TrainingMeasurementChartDTO> getTrainingIngredients(@RequestParam String nutrient,
                                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(dietStatisticsService.getTrainingNutrientForUserByProperty(name, nutrient, date), HttpStatus.OK);
    }
}
