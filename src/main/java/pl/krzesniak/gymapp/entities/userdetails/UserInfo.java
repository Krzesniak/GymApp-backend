package pl.krzesniak.gymapp.entities.userdetails;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends AbstractDefaultEntity {

    private String name;

    private String surname;

    @Embedded
    private Address address;

    private LocalDate dateBirth;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;

        return id != null && id.equals(userInfo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}