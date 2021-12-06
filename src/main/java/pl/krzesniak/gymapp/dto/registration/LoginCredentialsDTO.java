package pl.krzesniak.gymapp.dto.registration;

import lombok.Data;

@Data
public class LoginCredentialsDTO {
    private String username;
    private String password;
    private String email;
}
