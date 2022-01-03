package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krzesniak.gymapp.entities.training.Training;
import pl.krzesniak.gymapp.entities.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    List<Training> findByUser(User user);


    @Query("SELECT t FROM Training t " +
            "WHERE t.user.username =:username AND " +
            "t.date between :startDate and :endDate")
    List<Training> findAllByUserAndDateBetween( @Param("username") String username,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);
}
