package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.krzesniak.gymapp.entities.Exercise;
import pl.krzesniak.gymapp.entities.Ingredient;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, JpaSpecificationExecutor<Ingredient>
{
}
