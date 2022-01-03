package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dietUtil.DietIngredientCalculator;
import pl.krzesniak.gymapp.dto.DietDayCreateRequest;
import pl.krzesniak.gymapp.dto.DietDayDTO;
import pl.krzesniak.gymapp.dto.DietSumIngredientsDTO;
import pl.krzesniak.gymapp.entities.diet.Diet;
import pl.krzesniak.gymapp.entities.diet.Meal;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.mappers.MealMapper;
import pl.krzesniak.gymapp.repositories.DietRepository;
import pl.krzesniak.gymapp.repositories.MealRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DietService {

    private final DietRepository dietRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    public DietDayDTO getDietForUserAndByDate(String username, LocalDate date) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Not found user with username: " + username));
        return dietRepository.findByUsernameAndDate(user.getId(), date)
                .map(mealMapper::mapToDietDayDTO)
                .orElseThrow(() -> new NotFoundUserException(""));// TODO DIET Excep

    }

    public DietDayDTO createOrUpdateDietByDate(String username, LocalDate date, DietDayCreateRequest dietDayCreateRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Not found user with username: " + username));
        Set<UUID> mealsID = new HashSet<>();
        mealsID.add(dietDayCreateRequest.getMainCourse());
        mealsID.add(dietDayCreateRequest.getDinner());
        mealsID.add(dietDayCreateRequest.getSnacks());
        mealsID.add(dietDayCreateRequest.getBreakfast());
        mealsID.add(dietDayCreateRequest.getSecondBreakfast());
        List<UUID> filteredMealsID = mealsID.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Meal> meals = mealRepository.findAllById(filteredMealsID);
        Diet diet = dietRepository.findByUsernameAndDate(user.getId(), date)
                .orElse(new Diet(date, user));
        diet.setBreakfast(getMealById(meals, dietDayCreateRequest.getBreakfast()));
        diet.setDinner(getMealById(meals, dietDayCreateRequest.getDinner()));
        diet.setSecondBreakfast(getMealById(meals, dietDayCreateRequest.getSecondBreakfast()));
        diet.setMainCourse(getMealById(meals, dietDayCreateRequest.getMainCourse()));
        diet.setSnacks(getMealById(meals, dietDayCreateRequest.getSnacks()));
        Diet savedDiet = dietRepository.save(diet);
        return mealMapper.mapToDietDayDTO(savedDiet);

    }
    private Meal getMealById(List<Meal> meals, UUID id){
       return meals.stream()
                .filter(meal -> meal.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public DietSumIngredientsDTO getDietIngredientsSumForUserAndByDate(String username, LocalDate date) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Not found user with username: " + username));
        Diet diet = dietRepository.findByUsernameAndDate(user.getId(), date)
                .orElseThrow(() -> new NotFoundUserException(""));//TODO XD
        DietIngredientCalculator ingredientCalculator = new DietIngredientCalculator(diet);
        return ingredientCalculator.getSumOfIngredients();
    }

    public Void removeDietById(UUID id) {
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(""));//TODO XD
        dietRepository.delete(diet);
        return null;
    }
}
