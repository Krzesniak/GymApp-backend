package pl.krzesniak.gymapp.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString

public class MealNutrient extends AbstractDefaultEntity {

    private Integer calories;
    private Integer protein;
    private Integer carbohydrate;
    private Integer fat;
    private Integer fiber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MealNutrient that = (MealNutrient) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 1336127595;
    }
}