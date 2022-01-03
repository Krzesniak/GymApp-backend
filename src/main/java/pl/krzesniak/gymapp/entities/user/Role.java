package pl.krzesniak.gymapp.entities.user;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role extends AbstractDefaultEntity {

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;

        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
