package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzesniak.gymapp.entities.diet.MealIngredient;

import java.util.UUID;

public interface MealIngredientRepository extends JpaRepository<MealIngredient, UUID> {
}
