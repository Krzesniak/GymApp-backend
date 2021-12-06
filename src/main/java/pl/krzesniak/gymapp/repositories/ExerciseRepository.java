package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.krzesniak.gymapp.entities.Exercise;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID>, JpaSpecificationExecutor<Exercise> {
    List<Exercise> findAllByNameIn(List<String> exercisesName);
}
