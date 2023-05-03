package co.edu.umanizales.tads.controller;


import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
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
    @GetMapping(path = "/alternatekids")
    public ResponseEntity<ResponseDTO> alternateKids() throws ListSEException{
        listSEService.getKids().alternateKids();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercalado la lista",
                null), HttpStatus.OK);
    }

// Este es el controller del cuarto metodo
    @GetMapping(path = "/removekidbyage/{age}")
    public ResponseEntity<ResponseDTO> removeKidByAge(byte age) throws ListSEException{
        listSEService.getKids().removeKidByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercalado la lista",
                null), HttpStatus.OK);
    }

// Este es el controller del quinto metodo

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> getAverageAge() throws ListSEException{
        double kids = listSEService.getKids().getAverageAge();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha hecho el promedio de edad",
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
    @GetMapping(path = "winpositionkid/{id}/{win}")
    public ResponseEntity<ResponseDTO> winPositionKid(@PathVariable String id, @PathVariable int win) throws ListSEException{
        listSEService.getKids().winPositionKid(id, win);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño avanzó de posiciones", null), HttpStatus.OK);
    }

// Este es el controller del octavo metodo

    @GetMapping(path = "/kids/losepositionkid/{pos}")
    public ResponseEntity<ResponseDTO> losePositionKid(Kid kid, int pos2) throws ListSEException{
        listSEService.getKids().losePositionKid(kid, pos2);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño perdio el numero indicado de posiciones", null), HttpStatus.OK);
    }

// Este es el controller del noveno metodo

    @GetMapping(path = "/reportbyage/{age}")
    public ResponseEntity<ResponseDTO> getRangeByAge(byte minAge,byte maxAge) throws ListSEException{
        listSEService.getKids().getRangeByAge(minAge, maxAge);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño retrocedio las posiciones", null), HttpStatus.OK);
    }

// Este es el controller del decimo metodo

    @GetMapping(path = "addtostartnamechar/{letter}")
    public ResponseEntity<ResponseDTO> addToStartNameChar(char letter) throws ListSEException{
        listSEService.getKids().addToStartNameChar(letter);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño avanzó de posiciones", null), HttpStatus.OK);
    }
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListSEException{
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping(path="addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) throws ListSEException {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getName(),
                            kidDTO.getIdentification(),
                            kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el petacón",
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
