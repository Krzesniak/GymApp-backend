package pl.krzesniak.gymapp.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.AuthenticationResponseDTO;
import pl.krzesniak.gymapp.dto.RefreshTokenRequest;
import pl.krzesniak.gymapp.dto.registration.PrivateInformationDTO;
import pl.krzesniak.gymapp.dto.registration.RegistrationRequestDTO;
import pl.krzesniak.gymapp.dto.registration.VerifyDTO;
import pl.krzesniak.gymapp.security.JwtProvider;
import pl.krzesniak.gymapp.services.AuthService;
import pl.krzesniak.gymapp.services.RefreshTokenService;
import pl.krzesniak.gymapp.dto.UrlImageDTO;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @PostMapping("/refresh/token")
    public AuthenticationResponseDTO refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String accessToken = "Bearer " + jwtProvider.generateTokenByUsername(refreshTokenRequest.getUsername());
        UUID refreshToken = refreshTokenService.generateRefreshToken().getToken();
        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }

    @PostMapping("/avatar")
    public ResponseEntity<UrlImageDTO> uploadAvatar(@RequestBody @RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(authService.uploadAvatar(file), HttpStatus.CREATED);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> registerNewUser(@RequestBody RegistrationRequestDTO registrationRequestDTO){
        return new ResponseEntity<>(authService.registerNewUser(registrationRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<VerifyDTO> verifyAccount(@PathVariable UUID token){
        return new ResponseEntity<>(authService.verifyAccount(token), HttpStatus.OK);
    }
    @DeleteMapping("/logout/{token}")
    public ResponseEntity<Void> logout(@PathVariable UUID token){
        authService.logoutUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user-info")
    public ResponseEntity<PrivateInformationDTO> getUserInfoDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(authService.getUserInfo(name), HttpStatus.OK);

    }
    @GetMapping("/avatar")
    public ResponseEntity<UrlImageDTO> getImage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(authService.getAvatarForUser(name), HttpStatus.OK);
    }

}
