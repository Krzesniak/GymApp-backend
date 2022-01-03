package pl.krzesniak.gymapp.entities.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;
import pl.krzesniak.gymapp.entities.training.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.user.User;
import pl.krzesniak.gymapp.enums.TrainingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Training extends AbstractDefaultEntity {

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<ExerciseInTraining> results;

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


}
