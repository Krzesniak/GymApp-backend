package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefreshTokenRequest {

    private UUID refreshToken;
    private String username;
}
