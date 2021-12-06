package pl.krzesniak.gymapp.dto.records;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaximumRecordsDTO {
    private UUID trainingID;
    private Object value;
}
