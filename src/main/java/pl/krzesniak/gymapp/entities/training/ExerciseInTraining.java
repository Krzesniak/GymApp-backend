package pl.krzesniak.gymapp.entities.training;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "USER_EXERCISES_IN_TRAINING")
@Builder
public class ExerciseInTraining extends AbstractDefaultEntity {

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Exercise exercise;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Training training;

    private Integer seriesNumber;

    private Double weight;

    private Integer repetitions;

    private LocalTime duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExerciseInTraining that = (ExerciseInTraining) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

