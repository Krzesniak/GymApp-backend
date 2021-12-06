package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMeasurement extends AbstractDefaultEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private double weight;
    private double height;

    private double neck;
    private double arm;
    private double forehand;
    private double wrist;
    private double chest;
    private double waist;
    private double thigh;
    private double calf;

    private LocalDate date;
}
