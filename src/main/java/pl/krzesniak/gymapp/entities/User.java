package pl.krzesniak.gymapp.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.krzesniak.gymapp.entities.userdetails.UserInfo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends AbstractDefaultEntity {

    private String username;
    private String password;
    private String email;

    @Column(name = "ENABLED")
    private boolean isEnabled;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    private String urlImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name ="ROLE_ID")
    )
    private Collection<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
