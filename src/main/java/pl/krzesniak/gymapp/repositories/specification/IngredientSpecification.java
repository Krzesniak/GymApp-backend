package pl.krzesniak.gymapp.repositories.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.entities.Ingredient;
import pl.krzesniak.gymapp.repositories.filters.IngredientFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public class IngredientSpecification implements Specification<Ingredient> {

    private IngredientFilter ingredientFilter;

    @Override
    public Predicate toPredicate(Root<Ingredient> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[1];
        if (isSearchStringValid()) predicates[0] = createPredicateForSearchString(root, criteriaQuery, criteriaBuilder);
        Predicate[] filterPredicates = Arrays.stream(predicates)
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);

        return criteriaBuilder.and(filterPredicates);
    }

    private Predicate createPredicateForSearchString(Root<Ingredient> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("name")),
                "%" + ingredientFilter.getSearchString() + "%");
    }

    private boolean isSearchStringValid() {
        return ingredientFilter.getSearchString() != null && !ingredientFilter.getSearchString().isBlank();
    }
}
