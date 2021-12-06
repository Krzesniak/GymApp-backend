package pl.krzesniak.gymapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzesniak.gymapp.entities.RefreshToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findRefreshTokenByToken(UUID token);

    void deleteByToken(UUID token);
}
