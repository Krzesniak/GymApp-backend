package pl.krzesniak.gymapp.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.EmailDTO;
import pl.krzesniak.gymapp.dto.UrlImageAndMovieDTO;
import pl.krzesniak.gymapp.dto.registration.PrivateInformationDTO;
import pl.krzesniak.gymapp.dto.registration.RegistrationRequestDTO;
import pl.krzesniak.gymapp.dto.registration.VerifyDTO;
import pl.krzesniak.gymapp.dto.UrlImageDTO;
import pl.krzesniak.gymapp.entities.user.Role;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.entities.user.UserMeasurement;
import pl.krzesniak.gymapp.entities.user.VerificationToken;
import pl.krzesniak.gymapp.exceptions.NotFoundExerciseException;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.mappers.AuthMapper;
import pl.krzesniak.gymapp.repositories.RefreshTokenRepository;
import pl.krzesniak.gymapp.repositories.UserMeasurementRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;
import pl.krzesniak.gymapp.repositories.VerificationTokenRepository;
import pl.krzesniak.gymapp.services.mail.MailService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMeasurementRepository userMeasurementRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    private static String URL = "http://localhost:4200/";
    private static String REGISTRATION_ENDPOINT = "?verify=";

    @Value("${path.image.url.avatar}")
    private String avatarURL;

    public UrlImageDTO uploadAvatar(MultipartFile file) {
        String imageName = UUID.randomUUID() + file.getOriginalFilename();
        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=krzesniakowo;AccountKey=25lw//dy4LGB6PuGf8Ft6PqWwCcKycGJT94ns6FQMeAJ+vvSswesJUsQtn2gn/otjKWtcAa4VyZk6Z+4Y2Eb4w==;EndpointSuffix=core.windows.net")
                .containerName("avatars")
                .buildClient();


        BlobClient blob = container.getBlobClient(imageName);

        try {
            blob.upload(file.getInputStream(), file.getSize(), true);
            return new UrlImageDTO("https://krzesniakowo.blob.core.windows.net/avatars/" + imageName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public User registerNewUser(RegistrationRequestDTO registrationRequestDTO) {

        User user = authMapper.mapToUserEntity(registrationRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(IllegalArgumentException::new);
        user.setRoles(List.of(roleUser));
        UserMeasurement userMeasurement = authMapper.mapToUserMeasurement(registrationRequestDTO);
        userMeasurement.setUser(user);
        userMeasurement.setDate(LocalDate.now());
        userRepository.save(user);
        userMeasurementRepository.save(userMeasurement);
        UUID token = generateVerificationToken(user);
        mailService.sendMail(new EmailDTO("Rejestracja nowego użytkownika", user.getEmail(), "Link do aktywacji:" +
                URL + REGISTRATION_ENDPOINT + token ));
        return user;
    }

    public UUID generateVerificationToken(User user) {
        UUID token = UUID.randomUUID();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public VerifyDTO verifyAccount(UUID token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found!"));
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return new VerifyDTO(user.getUsername() + " was successfully verified!");
    }

    public PrivateInformationDTO getUserInfo(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundUserException("User not found with name: " + name));
        return authMapper.mapToPrivateInformationDTO(user.getUserInfo(), user.getUrlImage());

    }

    @Transactional
    public void logoutUser(UUID token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public UrlImageDTO getAvatarForUser(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundExerciseException(name));
        return new UrlImageDTO(user.getUrlImage());
    }
}
