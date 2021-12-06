package pl.krzesniak.gymapp.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.enums.MeasureUnit;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MealIngredient {

    @Embeddable
    @Data
    public static class Id implements Serializable {
        @Column(name = "MEAL_ID")
        protected UUID mealID;
        @Column(name = "INGREDIENT_ID")
        protected UUID ingredientID;
    }
    @EmbeddedId
    protected Id id = new Id();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false)
    private Ingredient ingredient;

    private Integer preciseAmount;

    @Enumerated(STRING)
    private MeasureUnit measureUnit;

    private String amountOverall;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MealIngredient that = (MealIngredient) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
