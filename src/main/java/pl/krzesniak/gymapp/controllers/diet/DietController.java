package pl.krzesniak.gymapp.controllers.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.DietDayCreateRequest;
import pl.krzesniak.gymapp.dto.DietDayDTO;
import pl.krzesniak.gymapp.dto.DietSumIngredientsDTO;
import pl.krzesniak.gymapp.services.DietService;
import pl.krzesniak.gymapp.services.MealService;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diet")
public class DietController {

    private final DietService dietService;

    @GetMapping
    public ResponseEntity<DietDayDTO> getDietForUserAndByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(dietService.getDietForUserAndByDate(username, date), HttpStatus.OK);

    }

    @GetMapping("/ingredients")
    public ResponseEntity<DietSumIngredientsDTO> getDietIngredientsSumForUserAndByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(dietService.getDietIngredientsSumForUserAndByDate(username, date), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<DietDayDTO> createOrUpdateDietByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody DietDayCreateRequest dietDayCreateRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(dietService.createOrUpdateDietByDate(username, date, dietDayCreateRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDietById(@PathVariable("id") UUID  id){
        return new ResponseEntity<>(dietService.removeDietById(id), HttpStatus.NO_CONTENT);
    }
}
