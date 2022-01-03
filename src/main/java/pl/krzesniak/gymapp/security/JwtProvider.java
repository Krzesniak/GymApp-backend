package pl.krzesniak.gymapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.krzesniak.gymapp.entities.user.RefreshToken;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.repositories.UserRepository;
import pl.krzesniak.gymapp.services.RefreshTokenService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.token.expiration}")
    private int tokenExpirationAfterDays;

    @Value("${jwt.token.key}")
    private String secretKey;

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public String generateToken(Authentication authentication){

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(2000000000)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }


    public String generateTokenByUsername(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("User with username: " + username + " does not exist!"));
        List<SimpleGrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", roles)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(200000)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    public UUID generateRefreshToken(){
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken();
        return refreshToken.getToken();
    }

    public Jws<Claims> validateToken(String token) throws JwtException {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
    }

}
