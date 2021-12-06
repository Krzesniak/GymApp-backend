package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzesniak.gymapp.dto.registration.MeasurementDTO;
import pl.krzesniak.gymapp.services.MeasurementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @GetMapping("/measurements")
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(measurementService.getAllMeasurements(name), HttpStatus.OK);

    }
}
