package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.entities.user.RefreshToken;
import pl.krzesniak.gymapp.repositories.RefreshTokenRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID());
        refreshToken.setCreatedDate(LocalDate.now());
      //  return refreshToken;

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(UUID token) {
        refreshTokenRepository.findRefreshTokenByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(UUID token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
