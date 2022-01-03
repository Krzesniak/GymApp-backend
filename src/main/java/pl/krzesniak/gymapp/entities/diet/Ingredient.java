package pl.krzesniak.gymapp.entities.diet;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ingredient extends AbstractDefaultEntity {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
