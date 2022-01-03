package pl.krzesniak.gymapp.entities.user;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RefreshToken extends AbstractDefaultEntity {

    private LocalDate createdDate;
    private UUID token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RefreshToken that = (RefreshToken) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
