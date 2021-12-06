package pl.krzesniak.gymapp.dto.registration;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeasurementDTO {
    private double height;
    private double weight;
    private double neck;
    private double chest;
    private double arm;
    private double forehand;
    private double waist;
    private double thigh;
    private double calf;
    private double wrist;
    private LocalDate date;
}
