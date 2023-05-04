package co.edu.umanizales.tads.controller;


import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {


    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    //Este es el controller del primer metodo
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() throws ListSEException{
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha invertido la lista",
                null), HttpStatus.OK);

    }
// Este es el controller del segundo metodo
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() throws ListSEException {
        listSEService.getKids().orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha ordenado la lista con los niños al comienzo",null), HttpStatus.OK);
    }

// Este es el controller del tercer metodo

    @GetMapping(path = "/alternate")
    public ResponseEntity<ResponseDTO> alternateKids() throws ListSEException {
        listSEService.getKids().alternateKids();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha alternado la lista con niño y niña",
                null), HttpStatus.OK);
    }

// Este es el controller del cuarto metodo

@GetMapping(path = "/deletebyage/{age}")
public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) throws ListSEException{
    listSEService.getKids().deleteByAge(age);
    return new ResponseEntity<>(new ResponseDTO(200,
            "Niños por la edad dada eliminados", null), HttpStatus.OK);

}

// Este es el controller del quinto metodo
@GetMapping(path="/averageage")
public ResponseEntity<ResponseDTO> averageAge() throws ListSEException{

    float averageAge = listSEService.getKids().averageByAge();
    return new ResponseEntity<>(new ResponseDTO(
            200,"El promedio de la edad es aproximadamente: " + averageAge,
            null), HttpStatus.OK);
}


// Este es el controller del sexto metodo
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() throws ListSEException{
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

// Este es el controller del septimo metodo
@GetMapping(path = "/winposition/{id}/{numposition}")
public ResponseEntity<ResponseDTO> winPosition(@PathVariable String id,
                                               @PathVariable int numposition) throws ListSEException{

    listSEService.getKids().winPosition(id,numposition,listSEService.getKids());
    return new ResponseEntity<>(new ResponseDTO(
            200,"El niño se ha movido con éxito",
            null), HttpStatus.OK);
}
// Este es el controller del octavo metodo
@GetMapping(path = "/loseposition/{id}/{numposition}")
public ResponseEntity<ResponseDTO> losePosition(@PathVariable String id,
                                                @PathVariable int numposition) throws ListSEException{

    listSEService.getKids().losePosition(id,numposition,listSEService.getKids());
    return new ResponseEntity<>(new ResponseDTO(
            200,"El niño se ha movido con éxito",
            null), HttpStatus.OK);
}

// Este es el controller del noveno metodo
/*
   @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> getRangeByAge() throws ListSEException{

        List<RangeDTO> kidsRangeList = new ArrayList<>();
        for (Ranges i : RangeService.){
            int quantity = listSEService.getKids().getRangeByAge(i.getFrom(), i.getTo());
            kidsRangeList.add(new RangeDTO(i,quantity));
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"el rango de los niños es: "+kidsRangeList,
                null), HttpStatus.OK);
                }

 */

// Este es el controller del decimo metodo

    @GetMapping(path = "/sendkidend/{letter}")
    public ResponseEntity<ResponseDTO> sendKidFinalByLetter(@PathVariable char letter) throws ListSEException{
        if (listSEService.getKids() != null) {
            listSEService.getKids().sendKidToTheEndByLetter(letter);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "La lista se ha organizado",null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409,
                    "No existen niños, por lo tanto no se puede realizar la acción",
                    null), HttpStatus.BAD_REQUEST);
        }
    }





    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListSEException{
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el petacón",
                null), HttpStatus.OK);

    }


    @GetMapping(path = "/kidsbydepto")
    public ResponseEntity<ResponseDTO> getKidsByDeptoCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList1= new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByDeptoCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList1,
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids()
                .getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }
}
