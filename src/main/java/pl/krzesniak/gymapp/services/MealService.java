package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.dto.meals.MealDetailsDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;
import pl.krzesniak.gymapp.entities.Meal;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;
import pl.krzesniak.gymapp.mappers.MealMapper;
import pl.krzesniak.gymapp.repositories.MealRepository;
import pl.krzesniak.gymapp.repositories.filters.MealFilter;
import pl.krzesniak.gymapp.repositories.specification.MealSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    public MealWithTotalPagesDTO getAllDietHeadersByPage(int finalPageNumber, int pageSize, String searchString, MealType mealType, MealDifficulty mealDifficulty) {
        MealFilter mealFilter = new MealFilter(searchString, mealDifficulty, mealType);
        Page<Meal> mealPage = mealRepository.findAll(new MealSpecification(mealFilter), PageRequest.of(finalPageNumber, pageSize));
        var totalPages = mealPage.getTotalPages();

        var meals = mealPage
                .stream()
                .collect(Collectors.toList());
        return mealMapper.mapToMealWithTotalPagesDTO(meals, totalPages);
    }

    public MealDetailsDTO getMealDetailsById(UUID id) {
        Meal meal = mealRepository.findById(id).orElseThrow((RuntimeException::new));
        return mealMapper.mapToMealDetails(meal);
    }

    public List<MealHeaderDTO> getAllMeals(String searchString, MealType mealType, MealDifficulty mealDifficulty) {
        MealFilter mealFilter = new MealFilter(searchString, mealDifficulty, mealType);
        return mealRepository.findAll(new MealSpecification(mealFilter))
                .stream()
                .map(mealMapper::mapToDietHeader)
                .collect(Collectors.toList());
    }
}
