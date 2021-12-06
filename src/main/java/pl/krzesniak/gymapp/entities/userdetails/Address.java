package pl.krzesniak.gymapp.entities.userdetails;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    private String country;

    private String street;

    private String city;


}