package pl.krzesniak.gymapp.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ResultDTO {

    private Double weight;
    private int repetitions;
    private int seriesNumber;
    private LocalTime duration;
}
