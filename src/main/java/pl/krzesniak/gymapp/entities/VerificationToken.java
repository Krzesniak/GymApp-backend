package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
