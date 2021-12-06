package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String accessToken;
    private UUID refreshToken;
}
