package pl.krzesniak.gymapp.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;
import pl.krzesniak.gymapp.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken extends AbstractDefaultEntity {

    private UUID token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
