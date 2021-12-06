package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.meals.IngredientFilterDTO;
import pl.krzesniak.gymapp.entities.Ingredient;
import pl.krzesniak.gymapp.mappers.IngredientMapper;
import pl.krzesniak.gymapp.repositories.IngredientRepository;
import pl.krzesniak.gymapp.repositories.filters.ExerciseFilter;
import pl.krzesniak.gymapp.repositories.filters.IngredientFilter;
import pl.krzesniak.gymapp.repositories.specification.ExerciseSpecification;
import pl.krzesniak.gymapp.repositories.specification.IngredientSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientFilterDTO> getFilteredIngredients(String searchString) {
        IngredientFilter ingredientFilter = new IngredientFilter(searchString);
        return ingredientRepository.findAll(new IngredientSpecification(ingredientFilter))
                .stream()
                .map(ingredientMapper::mapToIngredientFilterDTO)
                .collect(Collectors.toList());
    }

    public IngredientFilterDTO getIngredientById(UUID id) {
        return ingredientMapper.mapToIngredientFilterDTO(ingredientRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }
}
