package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzesniak.gymapp.entities.Training;
import pl.krzesniak.gymapp.entities.User;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    List<Training> findByUser(User user);
}
