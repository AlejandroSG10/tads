package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ReportDTO {
    private int quantity;
    private ArrayList<Location> location;
    private char gender;
    private int age;
}
