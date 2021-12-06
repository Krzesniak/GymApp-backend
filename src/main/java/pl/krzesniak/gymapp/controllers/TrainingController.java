package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.TrainingDTO;
import pl.krzesniak.gymapp.dto.TrainingDetailsDTO;
import pl.krzesniak.gymapp.dto.exercises.TrainingRequestDTO;
import pl.krzesniak.gymapp.services.TrainingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDTO>> getAllTrainingsForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        //  String username = userDetails.getUsername();
       return new ResponseEntity<>( trainingService.getAllTrainingsForUser(name), HttpStatus.OK);
    }
    @GetMapping("/trainings/{training-id}")
    public ResponseEntity<TrainingDetailsDTO> getTrainingDetails(@PathVariable("training-id")UUID trainingID) {
       return new ResponseEntity<>(trainingService.getTrainingDetailsById(trainingID), HttpStatus.OK);
    }
    @PostMapping("/trainings")
    public ResponseEntity<TrainingDetailsDTO> createTraining(@RequestBody TrainingRequestDTO trainingDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(trainingService.createTraining(trainingDTO, username), HttpStatus.CREATED);
    }
    @PutMapping("/trainings/{training-id}")
    public ResponseEntity<TrainingDetailsDTO> update(@PathVariable("training-id")UUID trainingID, @RequestBody TrainingRequestDTO trainingDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(trainingService.updateTraining(username, trainingDTO, trainingID), HttpStatus.CREATED);
    }
}