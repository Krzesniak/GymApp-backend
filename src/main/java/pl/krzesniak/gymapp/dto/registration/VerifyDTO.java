package pl.krzesniak.gymapp.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyDTO {
    private String token;
}
