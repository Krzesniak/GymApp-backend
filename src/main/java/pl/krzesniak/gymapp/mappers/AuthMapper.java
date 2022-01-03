package pl.krzesniak.gymapp.mappers;

import org.mapstruct.*;
import pl.krzesniak.gymapp.dto.registration.MeasurementDTO;
import pl.krzesniak.gymapp.dto.registration.PrivateInformationDTO;
import pl.krzesniak.gymapp.dto.registration.RegistrationRequestDTO;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.entities.user.UserMeasurement;
import pl.krzesniak.gymapp.entities.user.UserInfo;

@Mapper(config = CentralConfig.class)
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInfo.id", ignore = true)
    @Mapping(source = "loginCredentials.username", target = "username")
    @Mapping(source = "loginCredentials.password", target = "password")
    @Mapping(source = "loginCredentials.email", target = "email")
    @Mapping(source = "privateInformation.urlImage", target = "urlImage")
    @Mapping(source = "privateInformation.name", target = "userInfo.name")
    @Mapping(source = "privateInformation.dateBirth", target = "userInfo.dateBirth")
    @Mapping(source = "privateInformation.country", target = "userInfo.address.country")
    @Mapping(source = "privateInformation.street", target = "userInfo.address.street")
    @Mapping(source = "privateInformation.city", target = "userInfo.address.city")
    @Mapping(target = "enabled", defaultValue = "true", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User mapToUserEntity(RegistrationRequestDTO registrationRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(source = "measurement.height", target = "height")
    @Mapping(source = "measurement.weight", target = "weight")
    @Mapping(source = "measurement.neck", target = "neck")
    @Mapping(source = "measurement.arm", target = "arm")
    @Mapping(source = "measurement.wrist", target = "wrist")
    @Mapping(source = "measurement.chest", target = "chest")
    @Mapping(source = "measurement.waist", target = "waist")
    @Mapping(source = "measurement.thigh", target = "thigh")
    @Mapping(source = "measurement.forehand", target = "forehand")
    @Mapping(source = "measurement.calf", target = "calf")
    UserMeasurement mapToUserMeasurement(RegistrationRequestDTO registrationRequestDTO);

    MeasurementDTO mapToUserMeasurement(UserMeasurement userMeasurement);

    @Mapping(source = "userInfo.address.city", target = "city")
    @Mapping(source = "userInfo.address.street", target = "street")
    @Mapping(source = "userInfo.address.country", target = "country")
    PrivateInformationDTO mapToPrivateInformationDTO(UserInfo userInfo, String urlImage);
}
