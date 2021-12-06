package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;
import pl.krzesniak.gymapp.services.MealService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<MealWithTotalPagesDTO> getAllDietHeader(@RequestParam(required = false) Integer pageNumber,
                                                                  @RequestParam(required = false) Integer pageSize,
                                                                  @RequestParam(required = false) String searchString,
                                                                  @RequestParam(required = false) MealType mealType,
                                                                  @RequestParam(required = false) MealDifficulty mealDifficulty) {
        int finalPageNumber = pageNumber != null && pageNumber >= 0 ? pageNumber : 0;
        int finalPageSize = pageSize != null && pageSize >= 0 ? pageSize : 12;
        return new ResponseEntity<>(mealService.getAllDietHeadersByPage(finalPageNumber, finalPageSize,
                searchString,mealType,mealDifficulty), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDetailsDTO> getMealDetailsById(@PathVariable UUID id) {
        return new ResponseEntity<>(mealService.getMealDetailsById(id), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<MealHeaderDTO>> getAllMeals(
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) MealType mealType,
            @RequestParam(required = false) MealDifficulty mealDifficulty) {
        return new ResponseEntity<>(mealService.getAllMeals(searchString, mealType, mealDifficulty), HttpStatus.OK);
    }
}
