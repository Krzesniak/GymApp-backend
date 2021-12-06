package pl.krzesniak.gymapp.dto;

import lombok.Builder;
import lombok.Data;
import pl.krzesniak.gymapp.dto.records.MaximumRecordsDTO;
import pl.krzesniak.gymapp.record.RecordMaximumFinder;

import java.time.LocalTime;

@Data
@Builder
public class MonthRecordExerciseDTO {

    private MaximumRecordsDTO exerciseName;
    private MaximumRecordsDTO exercisesCounter;
    private MaximumRecordsDTO longestTrainingTime;
    private MaximumRecordsDTO seriesCounter;

}
