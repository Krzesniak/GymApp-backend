package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.krzesniak.gymapp.entities.diet.Diet;
import pl.krzesniak.gymapp.entities.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DietRepository extends JpaRepository<Diet, UUID> {

    @Query("SELECT diet FROM Diet diet " +
            " JOIN User user ON diet.user.id = user.id " +
            "WHERE user.id = :username AND diet.dietDate = :date" )
    Optional<Diet> findByUsernameAndDate(@Param("username") UUID username, @Param("date") LocalDate date);


    @Query("SELECT diet FROM Diet diet " +
            " JOIN User user ON diet.user.id = user.id " +
            "WHERE user.id = :username AND diet.dietDate BETWEEN :startDate AND :endDate" )
    List<Diet> findByUsernameAndDate(@Param("username") UUID username,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    List<Diet> findByUser(User user);
}
