package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzesniak.gymapp.entities.training.ExerciseSteps;

import java.util.UUID;

@Repository
public interface ExerciseStepsRepository extends JpaRepository<ExerciseSteps, UUID> {
}
