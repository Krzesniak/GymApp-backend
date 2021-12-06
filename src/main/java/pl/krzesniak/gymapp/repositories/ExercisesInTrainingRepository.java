package pl.krzesniak.gymapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krzesniak.gymapp.entities.ExerciseInTraining;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExercisesInTrainingRepository extends JpaRepository<ExerciseInTraining, UUID> {

    List<ExerciseInTraining> findByTrainingId(UUID trainingID);

    @Query("SELECT exercise FROM ExerciseInTraining exercise" +
            " JOIN Training training on exercise.training.id = training.id " +
            "  WHERE training.user.username = :username ")
    List<ExerciseInTraining> findByUsername(@Param("username") String username);

    @Query("SELECT exercise FROM ExerciseInTraining exercise" +
            " JOIN Training training on exercise.training.id = training.id" +
            " JOIN Exercise exerciseEntity on exercise.exercise.id = exerciseEntity.id " +
            "  WHERE training.user.username = :username AND exercise.exercise.id = :exerciseID ")
    List<ExerciseInTraining> findByUsernameAndExerciseId(@Param("username") String username,
                                                         @Param("exerciseID") UUID exerciseID);


    @Query("SELECT exercise FROM ExerciseInTraining exercise" +
            " JOIN Training training on exercise.training.id = training.id " +
            "  WHERE training.user.username = :username" +
            "   AND " +
            "  training.date BETWEEN :dateBefore and :dateTo")
    List<ExerciseInTraining> findByUsernameAndDate(@Param("username") String username, LocalDateTime dateBefore, LocalDateTime dateTo);
}
