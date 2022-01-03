package pl.krzesniak.gymapp.entities.diet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;
import pl.krzesniak.gymapp.enums.MealDifficulty;
import pl.krzesniak.gymapp.enums.MealType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meal extends AbstractDefaultEntity {

    private String name;
    @Enumerated(STRING)
    private MealType type;
    private String urlImage;
    @Column(name ="DIFFICULTY")
    @Enumerated(STRING)
    private MealDifficulty mealDifficulty;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEAL_ID")
    private Set<RecipeSteps> recipeSteps;

    @OneToMany(mappedBy = "meal")
    private Set<MealIngredient> mealIngredients;

    @ManyToOne(fetch = LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEAL_NUTRIENT_ID", nullable = false)
    private MealNutrient mealNutrient;

    private LocalDate createdDate;

    private String createdBy;

    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meal meal = (Meal) o;

        return getId() != null && getId().equals(meal.getId());
    }

    @Override
    public int hashCode() {
        return 1438709215;
    }
}
