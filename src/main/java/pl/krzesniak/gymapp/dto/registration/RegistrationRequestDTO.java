package pl.krzesniak.gymapp.dto.registration;

import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private LoginCredentialsDTO loginCredentials;
    private PrivateInformationDTO privateInformation;
    private MeasurementDTO measurement;
}
