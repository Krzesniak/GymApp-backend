package pl.krzesniak.gymapp.repositories.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.krzesniak.gymapp.entities.training.Exercise;
import pl.krzesniak.gymapp.repositories.filters.ExerciseFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public class ExerciseSpecification implements Specification<Exercise> {
    private final ExerciseFilter exerciseFilter;

    @Override
    public Predicate toPredicate(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = new Predicate[4];
        if (isSearchStringValid()) predicates[0] = createPredicateForSearchString(root, criteriaQuery, criteriaBuilder);
        if (exerciseFilter.getExerciseType() != null)
            predicates[1] = createPredicateForExerciseType(root, criteriaBuilder);
        if (exerciseFilter.getExerciseDifficulty() != null)
            predicates[2] = createPredicateForExerciseDifficulty(root, criteriaBuilder);
        predicates[3] = createPredicateIsEnabled(root, criteriaQuery, criteriaBuilder);
        Predicate[] filterPredicates = Arrays.stream(predicates)
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);

        return criteriaBuilder.and(filterPredicates);
    }

    private Predicate createPredicateForExerciseDifficulty(Root<Exercise> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("exerciseDifficulty"), exerciseFilter.getExerciseDifficulty());

    }

    private Predicate createPredicateForExerciseType(Root<Exercise> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("exerciseType"), exerciseFilter.getExerciseType());
    }

    private Predicate createPredicateForSearchString(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("name")),
                "%" + exerciseFilter.getSearchString() + "%");
    }

    private Predicate createPredicateIsEnabled(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("enabled"), true);
    }

    private boolean isSearchStringValid() {
        return exerciseFilter.getSearchString() != null && !exerciseFilter.getSearchString().isBlank();
    }

}
