package pl.krzesniak.gymapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.krzesniak.gymapp.dto.meals.IngredientFilterDTO;
import pl.krzesniak.gymapp.entities.Ingredient;
import pl.krzesniak.gymapp.repositories.filters.IngredientFilter;

@Mapper(config = CentralConfig.class)
public interface IngredientMapper {

    @Mapping(source = "id", target = "ingredientID")
    @Mapping(source = "name", target = "ingredientName")
    IngredientFilterDTO mapToIngredientFilterDTO(Ingredient ingredient);
}
