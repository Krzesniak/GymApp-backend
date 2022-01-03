package pl.krzesniak.gymapp.entities.user;

import lombok.Data;
import pl.krzesniak.gymapp.entities.AbstractDefaultEntity;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address{

    private String country;

    private String street;

    private String city;


}