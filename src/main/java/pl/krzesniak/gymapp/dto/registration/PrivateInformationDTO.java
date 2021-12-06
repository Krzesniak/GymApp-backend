package pl.krzesniak.gymapp.dto.registration;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrivateInformationDTO {
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String urlImage;
    private LocalDate dateBirth;
}
