package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.krzesniak.gymapp.entities.Meal;

import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID>, JpaSpecificationExecutor<Meal> {
}
