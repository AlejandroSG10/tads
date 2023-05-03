package co.edu.umanizales.tads.controller.dto;


import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.List;

@Data

public class RangeByAgeDTO {
private List<RangeDTO> ranges;

public RangeByAgeDTO(){
    this.ranges = new ArrayList<>();
    this.ranges.add(new RangeDTO());

}
}
