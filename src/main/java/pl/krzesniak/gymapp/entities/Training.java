package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.enums.TrainingType;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Training extends AbstractDefaultEntity {

    private LocalDateTime date;
    private LocalTime duration;
    private String name;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    private String urlImage;

    private String description;

    private String note;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<ExerciseInTraining> results;
}
