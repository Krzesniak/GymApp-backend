package pl.krzesniak.gymapp.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.MealWithTotalPagesDTO;
import pl.krzesniak.gymapp.dto.UrlImageDTO;
import pl.krzesniak.gymapp.dto.meals.*;
import pl.krzesniak.gymapp.entities.diet.Ingredient;
import pl.krzesniak.gymapp.entities.diet.Meal;
import pl.krzesniak.gymapp.entities.diet.MealIngredient;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;
import pl.krzesniak.gymapp.mappers.MealMapper;
import pl.krzesniak.gymapp.repositories.IngredientRepository;
import pl.krzesniak.gymapp.repositories.MealIngredientRepository;
import pl.krzesniak.gymapp.repositories.MealRepository;
import pl.krzesniak.gymapp.repositories.filters.MealFilter;
import pl.krzesniak.gymapp.repositories.specification.MealSpecification;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final IngredientRepository ingredientRepository;
    private final MealIngredientRepository mealIngredientRepository;

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

    public UrlImageDTO uploadMealImage(MultipartFile file) {
        String imageName = UUID.randomUUID() + file.getOriginalFilename();
        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=krzesniakowo;AccountKey=25lw//dy4LGB6PuGf8Ft6PqWwCcKycGJT94ns6FQMeAJ+vvSswesJUsQtn2gn/otjKWtcAa4VyZk6Z+4Y2Eb4w==;EndpointSuffix=core.windows.net")
                .containerName("food")
                .buildClient();


        BlobClient blob = container.getBlobClient(imageName);

        try {
            blob.upload(file.getInputStream(), file.getSize(), true);
            return new UrlImageDTO("https://krzesniakowo.blob.core.windows.net/food/" + imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Meal createMeal(CreateMealRequestDTO createMealRequestDTO, String name) {
        List<String> ingredientNames = createMealRequestDTO.getMealIngredients().stream()
                .map(CreateIngredientDetailsDTO::getName)
                .collect(Collectors.toList());
        List<Ingredient> ingredients = ingredientRepository.findByNameIn(ingredientNames);
        Meal meal =  mealMapper.mapToMealEntity(createMealRequestDTO, ingredients, name);
        Set<MealIngredient> mealIngredients = meal.getMealIngredients();
        meal.setMealIngredients(null);
        mealRepository.save(meal);
        mealIngredients = mealIngredients.stream()
                .map(mealIngredient -> {
                    mealIngredient.setMeal(meal);
                    MealIngredient.Id id = mealIngredient.getId();
                    id.setMealID(meal.getId());
                    return mealIngredient;
                })
                .collect(Collectors.toSet());
        mealIngredientRepository.saveAll(mealIngredients);
        return meal;
    }

    public MealDetailsDTO enableMeal(UUID id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        meal.setEnabled(true);
        mealRepository.save(meal);
       return mealMapper.mapToMealDetails(meal);
    }

    public Meal deleteMeal(UUID id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Set<MealIngredient> mealIngredients = meal.getMealIngredients();
        meal.setMealIngredients(null);
        mealIngredientRepository.deleteAll(mealIngredients);
        mealRepository.delete(meal);
        return meal;
    }

    public List<MealsNotVerifiedDTO> getNotVerifiedMeals() {
     return mealRepository.findAllByEnabled(false)
                .stream()
                .map(mealMapper::mapToNotVerifiedMeal)
                .collect(Collectors.toList());
    }
}
