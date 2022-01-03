package pl.krzesniak.gymapp.repositories.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.krzesniak.gymapp.entities.diet.Meal;
import pl.krzesniak.gymapp.repositories.filters.MealFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public class MealSpecification implements Specification<Meal> {

    private final MealFilter mealFilter;


    @Override
    public Predicate toPredicate(Root<Meal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[4];
        if (isSearchStringValid()) predicates[0] = createPredicateForSearchString(root, criteriaQuery, criteriaBuilder);
        if (mealFilter.getMealType() != null) predicates[1] = createPredicateForMealType(root, criteriaBuilder);
        if (mealFilter.getMealDifficulty() != null)
            predicates[2] = createPredicateForMealDifficulty(root, criteriaBuilder);
        predicates[3] = createPredicateIsEnabled(root, criteriaQuery, criteriaBuilder);
        Predicate[] filterPredicates = Arrays.stream(predicates)
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);

        return criteriaBuilder.and(filterPredicates);
    }

    private Predicate createPredicateForMealDifficulty(Root<Meal> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("mealDifficulty"), mealFilter.getMealDifficulty());

    }

    private Predicate createPredicateForMealType(Root<Meal> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("type"), mealFilter.getMealType());
    }

    private Predicate createPredicateForSearchString(Root<Meal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("name")),
                "%" + mealFilter.getSearchString() + "%");
    }
    private Predicate createPredicateIsEnabled(Root<Meal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("enabled"), true);
    }

    private boolean isSearchStringValid() {
        return mealFilter.getSearchString() != null && !mealFilter.getSearchString().isBlank();
    }
}


