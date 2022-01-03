package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class DashboardSummaryDTO {

    private LocalDate incomingTrainingDate;
    private LocalDate lastTrainingDate;
    private LocalDate incomingDietDate;
    private LocalDate lastDietDate;
    private LocalDate lastMeasurement;
}
