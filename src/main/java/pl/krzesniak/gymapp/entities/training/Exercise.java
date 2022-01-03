package pl.krzesniak.gymapp.entities.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Exercise extends AbstractDefaultEntity {

    private String name;
    private String urlMovie;
    private String urlImage;

    @Column(name = "DIFFICULTY")
    @Enumerated(EnumType.STRING)
    private ExerciseDifficulty exerciseDifficulty;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @OneToMany(mappedBy = "exercise")
    private Set<ExerciseHints> hints = new HashSet<>();

    @OneToMany(mappedBy = "exercise")
    private Set<ExerciseSteps> steps = new HashSet<>();

    private LocalDate createdDate;

    private String createdBy;

    private boolean enabled;

}
