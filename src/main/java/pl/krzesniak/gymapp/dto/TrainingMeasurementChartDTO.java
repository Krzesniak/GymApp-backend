package pl.krzesniak.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainingMeasurementChartDTO {
    List<PointDTO> data;
    private String measurementName;
}