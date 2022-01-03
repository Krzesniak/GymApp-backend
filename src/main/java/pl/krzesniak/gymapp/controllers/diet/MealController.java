package pl.krzesniak.gymapp.controllers.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.meals.MealsNotVerifiedDTO;
import pl.krzesniak.gymapp.dto.meals.CreateMealRequestDTO;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;
import pl.krzesniak.gymapp.dto.UrlImageDTO;
import pl.krzesniak.gymapp.entities.diet.Meal;
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

    @PostMapping("/image")
    public ResponseEntity<UrlImageDTO> uploadMealImage(@RequestBody @RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(mealService.uploadMealImage(file), HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity<Meal> addNewMeal(@RequestBody CreateMealRequestDTO createMealRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(mealService.createMeal(createMealRequestDTO, name), HttpStatus.CREATED);
    }
    @GetMapping("/not-verified")
    public ResponseEntity<List<MealsNotVerifiedDTO>> getNotVerifiedMeals(){
        return new ResponseEntity<>(mealService.getNotVerifiedMeals(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDetailsDTO> enableMeal(@PathVariable UUID id){
        return new ResponseEntity<>(mealService.enableMeal(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id){
        mealService.deleteMeal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
