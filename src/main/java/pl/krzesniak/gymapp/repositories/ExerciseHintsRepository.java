package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzesniak.gymapp.entities.training.ExerciseHints;

import java.util.UUID;

@Repository
public interface ExerciseHintsRepository extends JpaRepository<ExerciseHints, UUID> {
}
