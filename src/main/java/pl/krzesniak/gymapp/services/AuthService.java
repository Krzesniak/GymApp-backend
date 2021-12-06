package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.krzesniak.gymapp.dto.EmailDTO;
import pl.krzesniak.gymapp.dto.registration.PrivateInformationDTO;
import pl.krzesniak.gymapp.dto.registration.RegistrationRequestDTO;
import pl.krzesniak.gymapp.dto.registration.VerifyDTO;
import pl.krzesniak.gymapp.dto.urlImageDTO;
import pl.krzesniak.gymapp.entities.Role;
import pl.krzesniak.gymapp.entities.User;
import pl.krzesniak.gymapp.entities.UserMeasurement;
import pl.krzesniak.gymapp.entities.VerificationToken;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.mappers.AuthMapper;
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



    @Value("${path.image.url.avatar}")
    private String avatarURL;

    public urlImageDTO uploadAvatar(MultipartFile file) {
        String imageName = UUID.randomUUID() + file.getOriginalFilename();
        Path path = Paths.get(avatarURL + imageName);
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            log.info("Problem with file!");
            return new urlImageDTO();
        }
        return new urlImageDTO(imageName);
    }

    @Transactional
    public Object registerNewUser(RegistrationRequestDTO registrationRequestDTO) {

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
                " http://localhost:8080/accountVerification/" + token ));
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
}
