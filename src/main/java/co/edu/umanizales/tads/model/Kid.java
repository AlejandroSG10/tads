package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kid {

    private String identification;
    private String name;
    private byte age;
    private char gender;
    private Location location;

    public Kid (String identification, String name, int sum){
    }

    public int getPosition(){
        return 0;
    }
}
