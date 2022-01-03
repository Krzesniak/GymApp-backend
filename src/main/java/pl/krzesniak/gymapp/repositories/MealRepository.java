package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.krzesniak.gymapp.entities.diet.Meal;

import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID>, JpaSpecificationExecutor<Meal> {
    List<Meal> findAllByEnabled(boolean b);
}
