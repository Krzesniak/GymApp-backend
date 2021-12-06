package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.registration.MeasurementDTO;
import pl.krzesniak.gymapp.entities.User;
import pl.krzesniak.gymapp.entities.UserMeasurement;
import pl.krzesniak.gymapp.exceptions.NotFoundUserException;
import pl.krzesniak.gymapp.mappers.AuthMapper;
import pl.krzesniak.gymapp.repositories.UserMeasurementRepository;
import pl.krzesniak.gymapp.repositories.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final UserMeasurementRepository userMeasurementRepository;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    public List<MeasurementDTO> getAllMeasurements(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundUserException("name + " + name));
        return userMeasurementRepository.findByUsername(user.getUsername())
                .stream()
                .sorted(Comparator.comparing(UserMeasurement::getDate).reversed())
                .map(authMapper::mapToUserMeasurement)
                .collect(Collectors.toList());

    }
}
