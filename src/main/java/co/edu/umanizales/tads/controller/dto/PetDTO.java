package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PetDTO {
    @NotBlank(message = "Este campo no puede ir vacío")
    private String petIdentification;

    @Size(max = 15, message = "el nombre debe ser máximo de 15 caracteres")
    @NotBlank(message = "Este campo no puede ir vacío")
    private String namePet;
    @Min(value = 1)
    @Max(value = 18)
    private byte agePet;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String petType;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String breed;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String codeLocation;
    @NotBlank(message = "Este campo no puede ir vacio")
    private char petGender;


}
