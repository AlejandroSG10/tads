package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String petIdentification;
    private String namePet;
    private byte agePet;
    private String petType;
    private String breed;
    private char petGender;
    private Location location;
    private Boolean bathedPet;

}

