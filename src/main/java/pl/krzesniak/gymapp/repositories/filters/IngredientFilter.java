package pl.krzesniak.gymapp.repositories.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientFilter {
    private String searchString;
}
