package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.meals.IngredientFilterDTO;
import pl.krzesniak.gymapp.services.IngredientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("/filters")
    public ResponseEntity<List<IngredientFilterDTO>> getExercisesWithFilters(
            @RequestParam(required = false) String searchString ){
        return new ResponseEntity<>(
                ingredientService.getFilteredIngredients(searchString), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientFilterDTO> getIngredientById(@PathVariable UUID id){
        return new ResponseEntity<>(ingredientService.getIngredientById(id), HttpStatus.OK);
    }
}
