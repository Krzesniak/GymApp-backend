package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krzesniak.gymapp.entities.user.UserMeasurement;

import java.util.List;
import java.util.UUID;

public interface UserMeasurementRepository extends JpaRepository<UserMeasurement, UUID> {

    @Query("SELECT userMeasurement from  UserMeasurement  userMeasurement " +
            " JOIN User user ON user.id = userMeasurement.user.id " +
            "WHERE user.username = :username")
    List<UserMeasurement> findByUsername(@Param("username") String user);
}
