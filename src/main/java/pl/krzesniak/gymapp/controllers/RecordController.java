package pl.krzesniak.gymapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.krzesniak.gymapp.dto.MonthRecordExerciseDTO;
import pl.krzesniak.gymapp.dto.RecordExercisesDTO;
import pl.krzesniak.gymapp.services.RecordService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records/exercises")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
   // @AuthenticationPrincipal UserDetails userDetails,
    public ResponseEntity<List<RecordExercisesDTO>> getExercisesRecordByUsername(
            @RequestParam(required = false) String sort){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String sortBy = sort != null ? sort : "weight";
        return new ResponseEntity<>(recordService.getExercisesRecordByUsername(name,sortBy), HttpStatus.OK);
    }
    @GetMapping("/monthly")
    public ResponseEntity<MonthRecordExerciseDTO> getMonthRecordExercise(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return new ResponseEntity<>(recordService.getMonthExerciseRecord(name, date), HttpStatus.OK);

    }
}
