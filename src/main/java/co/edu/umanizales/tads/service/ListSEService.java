package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {

    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();

    }

    public void invert() throws ListSEException {
        kids.invert();
    }


}
